import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getJWTToken } from "@/lib/session.js"; // <<< Pfad ggf. anpassen!

const defaultTransport = {
    type: "CAR",
    date: "",
    departureTime: "",
    arrivalTime: "",
    licensePlate: "",
    airline: "",
    trainNumber: "",
    busNumber: ""
};

export default function CreateTripRoute() {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        tripType: "VACATION",
        startDate: "",
        endDate: "",
        transport: { ...defaultTransport }
    });
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "transport.type") {
            setForm((prev) => ({
                ...prev,
                transport: { ...defaultTransport, type: value }
            }));
        } else if (name.startsWith("transport.")) {
            const key = name.split(".")[1];
            setForm((prev) => ({
                ...prev,
                transport: { ...prev.transport, [key]: value }
            }));
        } else {
            setForm((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        // Zeit splitten
        let depHour = "", depMin = "", arrHour = "", arrMin = "";
        if (form.transport.departureTime) [depHour, depMin] = form.transport.departureTime.split(":");
        if (form.transport.arrivalTime) [arrHour, arrMin] = form.transport.arrivalTime.split(":");

        const transportPayload = {
            type: form.transport.type,
            date: form.transport.date,
            departureHour: depHour,
            departureMinute: depMin,
            arrivalHour: arrHour,
            arrivalMinute: arrMin
        };

        if (form.transport.type === "CAR") {
            transportPayload.licensePlate = form.transport.licensePlate;
        } else if (form.transport.type === "AIRPLANE") {
            transportPayload.airline = form.transport.airline;
        } else if (form.transport.type === "TRAIN") {
            transportPayload.trainNumber = form.transport.trainNumber;
        } else if (form.transport.type === "BUS") {
            transportPayload.busNumber = form.transport.busNumber;
        }

        const payload = {
            tripType: form.tripType,
            startDate: form.startDate,
            endDate: form.endDate,
            transports: [transportPayload],
            hotels: []
        };

        try {
            const res = await fetch("http://localhost:8080/trips", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + getJWTToken()
                },
                body: JSON.stringify(payload)
            });

            if (!res.ok) {
                const errText = await res.text();
                throw new Error(errText || "Fehler beim Erstellen");
            }
            navigate("/dashboard/trips");
        } catch (err) {
            setError("Erstellen fehlgeschlagen: " + (err.message || ""));
        }
    };

    return (
        <div className="container mt-4">
            <h2>Reise erstellen</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Reisetyp</label>
                    <select name="tripType" className="form-control" value={form.tripType} onChange={handleChange}>
                        <option value="VACATION">Ferien</option>
                        <option value="BUSINESS">Geschäftlich</option>
                    </select>
                </div>
                <div className="mb-3">
                    <label>Startdatum</label>
                    <input type="date" name="startDate" className="form-control" value={form.startDate} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Enddatum</label>
                    <input type="date" name="endDate" className="form-control" value={form.endDate} onChange={handleChange} />
                </div>
                <h4>Transport</h4>
                <div className="mb-3">
                    <label>Typ</label>
                    <select name="transport.type" className="form-control" value={form.transport.type} onChange={handleChange}>
                        <option value="CAR">Auto</option>
                        <option value="TRAIN">Zug</option>
                        <option value="AIRPLANE">Flugzeug</option>
                        <option value="BUS">Bus</option>
                    </select>
                </div>
                <div className="mb-3">
                    <label>Datum</label>
                    <input type="date" name="transport.date" className="form-control" value={form.transport.date} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Abfahrt (HH:MM)</label>
                    <input type="time" name="transport.departureTime" className="form-control" value={form.transport.departureTime} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Ankunft (HH:MM)</label>
                    <input type="time" name="transport.arrivalTime" className="form-control" value={form.transport.arrivalTime} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Transportnummer</label>
                    {form.transport.type === "CAR" && (
                        <input type="text" name="transport.licensePlate" placeholder="Autokennzeichen" className="form-control" value={form.transport.licensePlate} onChange={handleChange} />
                    )}
                    {form.transport.type === "TRAIN" && (
                        <input type="text" name="transport.trainNumber" placeholder="Zugnummer" className="form-control" value={form.transport.trainNumber} onChange={handleChange} />
                    )}
                    {form.transport.type === "AIRPLANE" && (
                        <input type="text" name="transport.airline" placeholder="Fluggesellschaft" className="form-control" value={form.transport.airline} onChange={handleChange} />
                    )}
                    {form.transport.type === "BUS" && (
                        <input type="text" name="transport.busNumber" placeholder="Busnummer" className="form-control" value={form.transport.busNumber} onChange={handleChange} />
                    )}
                </div>
                <button type="submit" className="btn btn-primary">Erstellen</button>
                {error && <div className="mt-3 alert alert-danger">{error}</div>}
            </form>
        </div>
    );
}
