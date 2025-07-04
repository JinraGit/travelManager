import { useEffect, useState } from "react";
import { fetchMeetings } from "@/lib/meetings/meetings.js";
import { fetchAllTrips } from "@/lib/trips/trips.js";
import { useCurrentUser } from "@/lib/session.js";
import { Link } from "react-router-dom";
import { formatDateEU } from "@/lib/utils/dateUtils.js";



export default function MeetingOverviewRoute() {
    const user = useCurrentUser();
    const [trips, setTrips] = useState([]);
    const [selectedTripId, setSelectedTripId] = useState("ALL");
    const [meetings, setMeetings] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadTrips() {
            try {
                const loadedTrips = await fetchAllTrips();
                setTrips(loadedTrips);
            } catch (err) {
                setError("Fehler beim Laden der Trips: " + (err.message || ""));
            }
        }
        loadTrips();
    }, []);

    useEffect(() => {
        async function loadMeetings() {
            if (!user?.id) return;

            try {
                let allMeetings = [];

                if (selectedTripId === "ALL") {
                    for (let trip of trips) {
                        const m = await fetchMeetings(trip.id, user.id);
                        allMeetings.push(...m);
                    }
                } else {
                    const m = await fetchMeetings(Number(selectedTripId), user.id);
                    allMeetings = m;
                }

                setMeetings(allMeetings);
            } catch (err) {
                setError("Fehler beim Laden der Meetings: " + (err.message || ""));
            }
        }

        if (trips.length > 0) loadMeetings();
    }, [selectedTripId, trips, user]);

    const handleTripChange = (e) => {
        setSelectedTripId(e.target.value);
    };

    return (
        <div className="container mt-4">
            <h2>Meetings</h2>

            <div className="mb-3">
                <label className="form-label">Nach Trip filtern:</label>
                <select
                    className="form-select"
                    style={{ color: "black" }}
                    value={selectedTripId}
                    onChange={handleTripChange}
                >
                    <option value="ALL">Alle Trips</option>
                    {trips.map((trip) => (
                        <option
                            key={trip.id}
                            value={trip.id}
                            style={{ color: "black" }}
                        >
                            {trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"} vom {formatDateEU(trip.startDate)} bis {formatDateEU(trip.endDate)}
                        </option>
                    ))}
                </select>
            </div>

            {error && <div className="alert alert-danger mt-4">{error}</div>}

            <table className="table table-striped mt-3">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Datum</th>
                    <th>Aktion</th>
                </tr>
                </thead>
                <tbody>
                {meetings.length === 0 ? (
                    <tr>
                        <td colSpan="3">Keine Meetings gefunden.</td>
                    </tr>
                ) : (
                    meetings.map(meeting => (
                        <tr key={meeting.id}>
                            <td>{meeting.name}</td>
                            <td>{formatDateEU(meeting.date)}</td>
                            <td>
                                <Link to={`/meetings/${meeting.id}`} className="btn btn-outline-primary btn-sm">
                                    Details
                                </Link>
                            </td>
                        </tr>
                    ))
                )}
                </tbody>
            </table>
        </div>
    );
}
