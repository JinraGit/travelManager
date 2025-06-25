import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchTripById } from "@/lib//trips/trips.js";

export default function DetailTripRoute() {
    const { id } = useParams();
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

    if (error) return <div className="alert alert-danger mt-4">{error}</div>;
    if (!trip) return <div className="mt-4">Lade Reise ...</div>;

    const transport = trip.transports?.[0];
    const hotel = trip.hotels?.[0];

    return (
        <div className="container mt-4">
            <h2>Reisedetails</h2>
            <hr />

            <h4>Allgemein</h4>
            <p><strong>Reisetyp:</strong> {trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"}</p>
            <p><strong>Start:</strong> {trip.startDate}</p>
            <p><strong>Ende:</strong> {trip.endDate}</p>

            {transport && (
                <>
                    <h4 className="mt-4">Transport</h4>
                    <p><strong>Typ:</strong> {transport.type}</p>
                    <p><strong>Datum:</strong> {transport.date}</p>
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
                </>
            )}

            {hotel && (
                <>
                    <h4 className="mt-4">Hotel</h4>
                    <p><strong>Name:</strong> {hotel.name}</p>
                    <p><strong>Adresse:</strong> {hotel.address.street} {hotel.address.houseNumber}, {hotel.address.zipCode} {hotel.address.city}</p>
                    <p><strong>Check-In:</strong> {hotel.checkInDate}</p>
                    <p><strong>Check-Out:</strong> {hotel.checkOutDate}</p>
                </>
            )}
        </div>
    );
}
