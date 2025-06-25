import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createTrip } from "@/lib/trips/trips.js";
import { defaultHotel } from "@/lib/constants/defaultHotel.js";
import { defaultTransport } from "@/lib/constants/defaultTransport.js";
import {buildTripPayload, handleTripFormChange} from "@/lib/utils/tripFormUtils.js";
import TripForm from "@/components/trips/TripForm.jsx";

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

    const handleChange = (e) => handleTripFormChange(e, setForm);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        const payload = buildTripPayload(form);

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
            <TripForm form={form} onChange={handleChange} onSubmit={handleSubmit} />
            {error && <div className="alert alert-danger mt-3">{error}</div>}
        </div>
    );
}
