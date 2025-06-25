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
    const [filter, setFilter] = useState("ALL");
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

    const filteredTrips = trips.filter(trip => {
        if (filter === "ALL") return true;
        return trip.tripType === filter;
    });

    return (
        <div className="container mt-4">
            <h2>Meine Reisen</h2>
            {error && <div className="alert alert-danger">{error}</div>}

            <div className="mb-3">
                <label className="form-label">Reise-Typ filtern:</label>
                <select
                    className="form-select"
                    value={filter}
                    onChange={(e) => setFilter(e.target.value)}
                >
                    <option value="ALL">Alle Reisen</option>
                    <option value="PRIVATE">Private Reisen</option>
                    <option value="BUSINESS">Geschäftsreisen</option>
                </select>
            </div>

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
                {filteredTrips.length === 0 ? (
                    <tr>
                        <td colSpan="4">Keine Reisen gefunden.</td>
                    </tr>
                ) : (
                    filteredTrips.map((trip) => (
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
                    ))
                )}
                </tbody>
            </table>
        </div>
    );
}
