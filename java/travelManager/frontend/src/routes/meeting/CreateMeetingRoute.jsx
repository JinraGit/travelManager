import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { createMeeting } from "@/lib/meetings/meetings.js";
import { getJWTToken, decodeJWT } from "@/lib/session.js";
import { fetchAllTrips } from "@/lib/trips/trips.js";
import { formatDateEU } from "@/lib/utils/dateUtils.js";


export default function CreateMeetingRoute() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: "",
        date: "",
        startMeeting: "",
        endMeeting: "",
        notes: "",
        tripId: ""
    });

    const [trips, setTrips] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadTrips() {
            try {
                const allTrips = await fetchAllTrips();
                setTrips(allTrips);
                if (allTrips.length > 0) {
                    setForm((prev) => ({ ...prev, tripId: allTrips[0].id }));
                }
            } catch (err) {
                setError("Fehler beim Laden deiner Reisen: " + err.message);
            }
        }

        loadTrips();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const token = getJWTToken();
            const decoded = decodeJWT(token);
            const accountId = parseInt(decoded.sub);

            const payload = {
                name: form.name,
                date: form.date,
                startMeeting: form.startMeeting,
                endMeeting: form.endMeeting,
                notes: form.notes,
                tripId: parseInt(form.tripId)
            };

            await createMeeting(accountId, payload);
            navigate("/meetings/all");
        } catch (err) {
            setError("Meeting konnte nicht erstellt werden: " + (err.message || ""));
        }
    };

    return (
        <div className="container mt-4">
            <h2>Meeting erstellen</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Zu welcher Reise?</label>
                    <select name="tripId" className="form-control" value={form.tripId} onChange={handleChange} required>
                        {trips.map((trip) => (
                            <option key={trip.id} value={trip.id}>
                                {trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"} vom {formatDateEU(trip.startDate)} bis {formatDateEU(trip.endDate)}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="mb-3">
                    <label>Name des Meetings</label>
                    <input type="text" name="name" className="form-control" value={form.name} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label>Datum</label>
                    <input type="date" name="date" className="form-control" value={form.date} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label>Startzeit</label>
                    <input type="time" name="startMeeting" className="form-control" value={form.startMeeting} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label>Endzeit</label>
                    <input type="time" name="endMeeting" className="form-control" value={form.endMeeting} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                    <label>Notizen</label>
                    <textarea name="notes" className="form-control" rows="3" value={form.notes} onChange={handleChange}></textarea>
                </div>

                <button type="submit" className="btn btn-primary">Meeting erstellen</button>
                {error && <div className="alert alert-danger mt-3">{error}</div>}
            </form>
        </div>
    );
}
