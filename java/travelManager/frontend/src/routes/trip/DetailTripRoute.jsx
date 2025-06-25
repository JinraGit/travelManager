import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchTripById, deleteTrip } from "@/lib/trips/trips.js";

function formatDateEU(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString("de-CH", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
    });
}

export default function DetailTripRoute() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [trip, setTrip] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadTrip() {
            try {
                const data = await fetchTripById(id);
                setTrip(data);
            } catch (err) {
                setError("Fehler beim Laden der Reise: " + (err.message || ""));
            }
        }
        loadTrip();
    }, [id]);

    const handleDelete = async () => {
        if (!window.confirm("Möchtest du diese Reise wirklich löschen?")) return;

        try {
            await deleteTrip(id);
            navigate("/trips/all");
        } catch (err) {
            setError("Löschen fehlgeschlagen: " + (err.message || ""));
        }
    };

    if (error) return <div className="alert alert-danger mt-4">{error}</div>;
    if (!trip) return <div className="mt-4">Lade Reise ...</div>;

    const transport = trip.transports?.[0];
    const hotel = trip.hotels?.[0];

    return (
        <div className="container mt-4">
            <div className="bg-primary text-white p-3 rounded mb-3">
                <h2>Reisedetails</h2>
            </div>

            <div className="bg-primary text-white p-3 rounded mb-3">
                <h4>Allgemein</h4>
                <p><strong>Reisetyp:</strong> {trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"}</p>
                <p><strong>Start:</strong> {formatDateEU(trip.startDate)}</p>
                <p><strong>Ende:</strong> {formatDateEU(trip.endDate)}</p>
            </div>

            {transport && (
                <div className="bg-primary text-white p-3 rounded mb-3">
                    <h4>Transport</h4>
                    <p><strong>Typ:</strong> {transport.type}</p>
                    <p><strong>Datum:</strong> {formatDateEU(transport.date)}</p>
                    <p><strong>Abfahrt:</strong> {transport.departureHour}:{transport.departureMinute}</p>
                    <p><strong>Ankunft:</strong> {transport.arrivalHour}:{transport.arrivalMinute}</p>

                    {transport.type === "CAR" && (
                        <p><strong>Autokennzeichen:</strong> {transport.licensePlate || "—"}</p>
                    )}
                    {transport.type === "AIRPLANE" && (
                        <p><strong>Fluggesellschaft:</strong> {transport.airline || "—"}</p>
                    )}
                    {transport.type === "TRAIN" && (
                        <p><strong>Zugnummer:</strong> {transport.trainNumber || "—"}</p>
                    )}
                    {transport.type === "BUS" && (
                        <p><strong>Busnummer:</strong> {transport.busNumber || "—"}</p>
                    )}
                </div>
            )}

            {hotel && (
                <div className="bg-primary text-white p-3 rounded mb-3">
                    <h4>Hotel</h4>
                    <p><strong>Name:</strong> {hotel.name}</p>
                    <p><strong>Adresse:</strong> {hotel.address.street} {hotel.address.houseNumber}, {hotel.address.zipCode} {hotel.address.city}</p>
                    <p><strong>Check-In:</strong> {formatDateEU(hotel.checkInDate)}</p>
                    <p><strong>Check-Out:</strong> {formatDateEU(hotel.checkOutDate)}</p>
                </div>
            )}

            <div className="d-flex gap-2 mt-4">
                <button
                    className="btn btn-warning"
                    onClick={() => navigate(`/trips/${id}/edit`)}
                >
                    Bearbeiten
                </button>
                <button
                    className="btn btn-danger"
                    onClick={handleDelete}
                >
                    Löschen
                </button>
            </div>
        </div>
    );
}
