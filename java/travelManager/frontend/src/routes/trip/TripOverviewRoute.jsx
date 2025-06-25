import { useEffect, useState } from "react";
import { fetchAllTrips } from "@/lib/trips/trips.js";
import { Link } from "react-router-dom";

function formatDateEU(isoDate) {
    const date = new Date(isoDate);
    return date.toLocaleDateString("de-CH", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
    });
}

export default function TripOverviewRoute() {
    const [trips, setTrips] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        async function loadTrips() {
            try {
                const data = await fetchAllTrips();
                setTrips(data);
            } catch (err) {
                setError("Fehler beim Laden der Reisen: " + (err.message || ""));
            }
        }

        loadTrips();
    }, []);

    return (
        <div className="container mt-4">
            <h2>Meine Reisen</h2>
            {error && <div className="alert alert-danger">{error}</div>}

            <table className="table mt-3">
                <thead>
                <tr>
                    <th>Typ</th>
                    <th>Startdatum</th>
                    <th>Enddatum</th>
                    <th>Details</th>
                </tr>
                </thead>
                <tbody>
                {trips.map((trip) => (
                    <tr key={trip.id}>
                        <td>{trip.tripType === "BUSINESS" ? "Geschäftlich" : "Privat"}</td>
                        <td>{formatDateEU(trip.startDate)}</td>
                        <td>{formatDateEU(trip.endDate)}</td>
                        <td>
                            <Link to={`/trips/${trip.id}`} className="btn btn-outline-primary btn-sm">
                                Ansehen
                            </Link>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
