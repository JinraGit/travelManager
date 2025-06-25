import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchMeetingById, deleteMeeting } from "@/lib/meetings/meetings.js";
import { useCurrentUser } from "@/lib/session.js";
import { formatDateEU } from "@/lib/utils/dateUtils.js";



export default function DetailMeetingRoute() {
    const { id } = useParams();
    const user = useCurrentUser();
    const navigate = useNavigate();

    const [meeting, setMeeting] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadMeeting() {
            try {
                const data = await fetchMeetingById(id, user.id);
                setMeeting(data);
            } catch (err) {
                setError("Fehler beim Laden des Meetings: " + (err.message || ""));
            }
        }
        if (user?.id) loadMeeting();
    }, [id, user]);

    const handleDelete = async () => {
        if (!confirm("Möchtest du dieses Meeting wirklich löschen?")) return;
        try {
            await deleteMeeting(id);
            navigate("/meetings/all");
        } catch (err) {
            setError("Löschen fehlgeschlagen: " + (err.message || ""));
        }
    };

    if (error) return <div className="alert alert-danger mt-4">{error}</div>;
    if (!meeting) return <div className="mt-4">Lade Meeting ...</div>;

    return (
        <div className="container mt-4">
            <h2 style={{ color: "#003366" }}>Meeting-Details</h2>
            <hr />

            <div className="p-3 bg-light rounded shadow-sm">
                <p><strong>Name:</strong> {meeting.name}</p>
                <p><strong>Datum:</strong> {formatDateEU(meeting.date)}</p>
                <p><strong>Beginn:</strong> {meeting.startMeeting}</p>
                <p><strong>Ende:</strong> {meeting.endMeeting}</p>
                <p><strong>Notizen:</strong> {meeting.notes || "—"}</p>
            </div>

            <div className="mt-4 d-flex gap-2">
                <button
                    className="btn"
                    style={{ backgroundColor: "#e7f0fb", color: "#003366" }}
                    onClick={() => navigate(`/meetings/edit/${meeting.id}`)}
                >
                    Bearbeiten
                </button>
                <button className="btn btn-danger" onClick={handleDelete}>
                    Löschen
                </button>
            </div>
        </div>
    );
}
