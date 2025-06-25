import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchTripById, deleteTrip } from "@/lib/trips/trips.js";
import { formatDateEU } from "@/lib/utils/dateUtils.js";
import { getTransportLabel } from "@/lib/utils/transportUtils.js";

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
            <h2 className="text-primary">Reisedetails</h2>

            <div className="bg-light p-3 rounded mb-3">
                <h4 className="text-primary">Allgemein</h4>
                <p><strong>Reisetyp:</strong> {trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"}</p>
                <p><strong>Start:</strong> {formatDateEU(trip.startDate)}</p>
                <p><strong>Ende:</strong> {formatDateEU(trip.endDate)}</p>
            </div>

            {transport && (
                <div className="bg-light p-3 rounded mb-3">
                    <h4 className="text-primary">Transport</h4>
                    <p><strong>Transportmittel:</strong> {getTransportLabel(transport.type)}</p>
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
                <div className="bg-light p-3 rounded mb-3">
                    <h4 className="text-primary">Hotel</h4>
                    <p><strong>Name:</strong> {hotel.name}</p>
                    <p><strong>Adresse:</strong> {hotel.address.street} {hotel.address.houseNumber}, {hotel.address.zipCode} {hotel.address.city}</p>
                    <p><strong>Check-In:</strong> {formatDateEU(hotel.checkInDate)}</p>
                    <p><strong>Check-Out:</strong> {formatDateEU(hotel.checkOutDate)}</p>
                </div>
            )}

            <div className="d-flex gap-2 mt-4">
                <button className="btn btn-info text-white" onClick={() => navigate(`/trips/${id}/edit`)}>
                    Bearbeiten
                </button>
                <button className="btn btn-danger" onClick={handleDelete}>
                    Löschen
                </button>
            </div>
        </div>
    );
}
