import { useEffect, useState } from "react";
import { fetchMeetings } from "@/lib/meetings/meetings.js";
import { fetchAllTrips } from "@/lib/trips/trips.js";
import { useCurrentUser } from "@/lib/session.js";
import { Link } from "react-router-dom";

function formatDateEU(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString("de-CH", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    });
}

export default function MeetingOverviewRoute() {
    const user = useCurrentUser();
    const [trips, setTrips] = useState([]);
    const [allMeetings, setAllMeetings] = useState([]);
    const [selectedTripId, setSelectedTripId] = useState("ALL");
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadData() {
            try {
                const loadedTrips = await fetchAllTrips();
                setTrips(loadedTrips);

                const meetings = [];
                for (let trip of loadedTrips) {
                    const m = await fetchMeetings(trip.id, user.id);
                    meetings.push(...m);
                }
                setAllMeetings(meetings);
            } catch (err) {
                setError("Fehler beim Laden der Meetings: " + (err.message || ""));
            }
        }

        if (user?.id) loadData();
    }, [user]);

    const filteredMeetings =
        selectedTripId === "ALL"
            ? allMeetings
            : allMeetings.filter(m => m.tripId === parseInt(selectedTripId));

    return (
        <div className="container mt-4">
            <h2>Meetings</h2>

            <div className="mb-3">
                <label className="form-label">Nach Trip filtern:</label>
                <select
                    className="form-select"
                    style={{ color: "black" }}
                    value={selectedTripId}
                    onChange={e => setSelectedTripId(e.target.value)}
                >
                    <option value="ALL">Alle Trips</option>
                    {trips.map(trip => (
                        <option key={trip.id} value={trip.id} style={{ color: "black !important;" }}>
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
                {filteredMeetings.length === 0 ? (
                    <tr>
                        <td colSpan="3">Keine Meetings gefunden.</td>
                    </tr>
                ) : (
                    filteredMeetings.map(meeting => (
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
