// src/routes/meeting/EditMeetingRoute.jsx
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchMeetingById, updateMeeting } from "@/lib/meetings/meetings.js";
import { useCurrentUser } from "@/lib/session.js";

export default function EditMeetingRoute() {
    const { meetingId } = useParams();
    const [meetingData, setMeetingData] = useState(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const user = useCurrentUser();
    const navigate = useNavigate();

    useEffect(() => {
        async function load() {
            try {
                const data = await fetchMeetingById(meetingId, user.id);
                setMeetingData(data);
            } catch (err) {
                setError("Fehler beim Laden des Meetings: " + (err.message || ""));
            } finally {
                setLoading(false);
            }
        }
        if (user?.id) load();
    }, [meetingId, user]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateMeeting(meetingId, user.id, meetingData);
            navigate(`/meetings/${meetingId}`);
        } catch (err) {
            setError("Fehler beim Speichern: " + (err.message || ""));
        }
    };

    if (loading) return <div className="mt-4">Lade Meeting ...</div>;
    if (error) return <div className="alert alert-danger mt-4">{error}</div>;

    return (
        <div className="container mt-4">
            <h2 style={{ color: "#003366" }}>Meeting bearbeiten</h2>
            <hr />

            <form onSubmit={handleSubmit} className="bg-light p-4 rounded shadow-sm">
                <div className="mb-3">
                    <label className="form-label">Name</label>
                    <input
                        type="text"
                        className="form-control"
                        value={meetingData.name}
                        onChange={(e) => setMeetingData({ ...meetingData, name: e.target.value })}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Datum</label>
                    <input
                        type="date"
                        className="form-control"
                        value={meetingData.date}
                        onChange={(e) => setMeetingData({ ...meetingData, date: e.target.value })}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Beginn</label>
                    <input
                        type="time"
                        className="form-control"
                        value={meetingData.startMeeting}
                        onChange={(e) => setMeetingData({ ...meetingData, startMeeting: e.target.value })}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Ende</label>
                    <input
                        type="time"
                        className="form-control"
                        value={meetingData.endMeeting}
                        onChange={(e) => setMeetingData({ ...meetingData, endMeeting: e.target.value })}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Notizen</label>
                    <textarea
                        className="form-control"
                        value={meetingData.notes || ""}
                        onChange={(e) => setMeetingData({ ...meetingData, notes: e.target.value })}
                        rows={3}
                    />
                </div>

                <button type="submit" className="btn btn-primary">Speichern</button>
                <button type="button" className="btn btn-secondary ms-2" onClick={() => navigate(-1)}>
                    Abbrechen
                </button>
            </form>
        </div>
    );
}
