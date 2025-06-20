import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getJWTToken } from "@/lib/session";

export default function CreateTripRoute() {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        tripType: "",
        startDate: "",
        endDate: ""
    });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        const token = getJWTToken();
        if (!token) {
            setError("Nicht eingeloggt.");
            return;
        }

        try {
            const response = await fetch("/trips", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    ...form,
                    transports: [],
                    hotels: []
                })
            });

            if (!response.ok) throw new Error("Fehler beim Erstellen");
            navigate("/trips/all");
        } catch (err) {
            console.error(err);
            setError("Erstellen fehlgeschlagen.");
        }
    };

    return (
        <div className="container py-5">
            <div className="p-4 border rounded bg-light shadow-sm">
                <h2 className="mb-4">Neuen Trip erstellen</h2>

                {error && <p className="text-danger">{error}</p>}

                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label className="form-label">Trip Typ</label>
                        <select
                            name="tripType"
                            value={form.tripType}
                            onChange={handleChange}
                            className="form-select"
                            required
                        >
                            <option value="">Bitte wählen</option>
                            <option value="PRIVATE">Privat</option>
                            <option value="BUSINESS">Geschäftlich</option>
                            <option value="EDUCATION">Bildung</option>
                        </select>
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Startdatum</label>
                        <input
                            type="date"
                            name="startDate"
                            value={form.startDate}
                            onChange={handleChange}
                            className="form-control"
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Enddatum</label>
                        <input
                            type="date"
                            name="endDate"
                            value={form.endDate}
                            onChange={handleChange}
                            className="form-control"
                            required
                        />
                    </div>

                    <button type="submit" className="btn btn-primary">Trip erstellen</button>
                </form>
            </div>
        </div>
    );
}
