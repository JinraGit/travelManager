import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createTrip } from "@/lib/trips/trips.js";
import { defaultHotel } from "@/lib/constants/defaultHotel.js";
import { defaultTransport } from "@/lib/constants/defaultTransport.js";

export default function CreateTripRoute() {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        tripType: "PRIVATE",
        startDate: "",
        endDate: "",
        transport: { ...defaultTransport },
        hotel: { ...defaultHotel }
    });
    const [error, setError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name.startsWith("transport.")) {
            const key = name.split(".")[1];
            setForm((prev) => ({
                ...prev,
                transport: { ...prev.transport, [key]: value }
            }));
        } else if (name.startsWith("hotel.address.")) {
            const key = name.split(".")[2];
            setForm((prev) => ({
                ...prev,
                hotel: {
                    ...prev.hotel,
                    address: {
                        ...prev.hotel.address,
                        [key]: value
                    }
                }
            }));
        } else if (name.startsWith("hotel.")) {
            const key = name.split(".")[1];
            setForm((prev) => ({
                ...prev,
                hotel: { ...prev.hotel, [key]: value }
            }));
        } else {
            setForm((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        let [depHour = "", depMin = ""] = form.transport.departureTime?.split(":") || [];
        let [arrHour = "", arrMin = ""] = form.transport.arrivalTime?.split(":") || [];

        const transport = {
            type: form.transport.type,
            date: form.transport.date,
            departureHour: depHour,
            departureMinute: depMin,
            arrivalHour: arrHour,
            arrivalMinute: arrMin,
            licensePlate: form.transport.type === "CAR" ? form.transport.licensePlate : null,
            airline: form.transport.type === "AIRPLANE" ? form.transport.airline : null,
            trainNumber: form.transport.type === "TRAIN" ? form.transport.trainNumber : null,
            busNumber: form.transport.type === "BUS" ? form.transport.busNumber : null
        };

        const hotel = {
            name: form.hotel.name,
            address: {
                street: form.hotel.address.street,
                houseNumber: form.hotel.address.houseNumber,
                city: form.hotel.address.city,
                zipCode: form.hotel.address.zipCode
            },
            checkInDate: form.hotel.checkInDate,
            checkOutDate: form.hotel.checkOutDate
        };

        const payload = {
            tripType: form.tripType,
            startDate: form.startDate,
            endDate: form.endDate,
            transports: [transport],
            hotels: [hotel] // Optional, aber wird im richtigen Format gesendet
        };

        try {
            await createTrip(payload);
            navigate("/trips/all");
        } catch (err) {
            setError("Erstellen fehlgeschlagen: " + (err.message || "Unbekannter Fehler"));
        }
    };

    return (
        <div className="container mt-4">
            <h2>Reise erstellen</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Reisetyp</label>
                    <select name="tripType" className="form-control" value={form.tripType} onChange={handleChange}>
                        <option value="PRIVATE">Privat</option>
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
                    <label>Details</label>
                    {form.transport.type === "CAR" && (
                        <input type="text" name="transport.licensePlate" className="form-control" placeholder="Kennzeichen" value={form.transport.licensePlate} onChange={handleChange} />
                    )}
                    {form.transport.type === "TRAIN" && (
                        <input type="text" name="transport.trainNumber" className="form-control" placeholder="Zugnummer" value={form.transport.trainNumber} onChange={handleChange} />
                    )}
                    {form.transport.type === "AIRPLANE" && (
                        <input type="text" name="transport.airline" className="form-control" placeholder="Fluggesellschaft" value={form.transport.airline} onChange={handleChange} />
                    )}
                    {form.transport.type === "BUS" && (
                        <input type="text" name="transport.busNumber" className="form-control" placeholder="Busnummer" value={form.transport.busNumber} onChange={handleChange} />
                    )}
                </div>

                <h4>Hotel (optional)</h4>
                <div className="mb-3">
                    <label>Name</label>
                    <input type="text" name="hotel.name" className="form-control" value={form.hotel.name} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Adresse</label>
                    <input type="text" name="hotel.address.street" className="form-control mb-1" placeholder="Strasse" value={form.hotel.address.street} onChange={handleChange} />
                    <input type="text" name="hotel.address.houseNumber" className="form-control mb-1" placeholder="Hausnummer" value={form.hotel.address.houseNumber} onChange={handleChange} />
                    <input type="text" name="hotel.address.city" className="form-control mb-1" placeholder="Ort" value={form.hotel.address.city} onChange={handleChange} />
                    <input type="text" name="hotel.address.zipCode" className="form-control mb-1" placeholder="PLZ" value={form.hotel.address.zipCode} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Check-In</label>
                    <input type="date" name="hotel.checkInDate" className="form-control" value={form.hotel.checkInDate} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label>Check-Out</label>
                    <input type="date" name="hotel.checkOutDate" className="form-control" value={form.hotel.checkOutDate} onChange={handleChange} />
                </div>

                <button type="submit" className="btn btn-primary">Reise erstellen</button>
                {error && <div className="alert alert-danger mt-3">{error}</div>}
            </form>
        </div>
    );
}
