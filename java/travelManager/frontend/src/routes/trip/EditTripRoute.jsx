import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchTripById, updateTrip } from "@/lib/trips/trips.js";
import { defaultHotel } from "@/lib/constants/defaultHotel.js";
import {mapTripToForm, handleTripFormChange, buildTripPayload} from "@/lib/utils/tripFormUtils.js";
import TripForm from "@/components/trips/TripForm.jsx";

export default function EditTripRoute() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [form, setForm] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadTrip() {
            try {
                const trip = await fetchTripById(id);
                setForm(mapTripToForm(trip, defaultHotel));
            } catch (err) {
                setError("Fehler beim Laden der Reisedaten: " + err.message);
            }
        }

        loadTrip();
    }, [id]);

    const handleChange = (e) => handleTripFormChange(e, setForm);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        const payload = buildTripPayload(form);


        try {
            await updateTrip(id, payload);
            navigate("/trips/all");
        } catch (err) {
            setError("Update fehlgeschlagen: " + err.message);
        }
    };

    if (error) return <div className="alert alert-danger mt-4">{error}</div>;
    if (!form) return <div className="mt-4">Lade Reisedaten ...</div>;

    return (
        <div className="container mt-4">
            <h2>Reise bearbeiten</h2>
            <TripForm form={form} onChange={handleChange} onSubmit={handleSubmit} />
        </div>
    );
}