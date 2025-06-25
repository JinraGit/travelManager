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
    const [meetings, setMeetings] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadMeetings() {
            try {
                const trips = await fetchAllTrips(); // Hole alle Trips für den eingeloggten User
                const allMeetings = [];

                for (let trip of trips) {
                    const m = await fetchMeetings(trip.id, user.id);
                    allMeetings.push(...m);
                }

                setMeetings(allMeetings);
            } catch (err) {
                setError("Fehler beim Laden der Meetings: " + (err.message || ""));
            }
        }

        if (user?.id) loadMeetings();
    }, [user]);

    if (error) return <div className="alert alert-danger mt-4">{error}</div>;

    return (
        <div className="container mt-4">
            <h2>Meetings</h2>
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
                                <Link to={`/meetings/${meeting.id}`} className="btn btn-outline-primary text-primary btn-sm">
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
