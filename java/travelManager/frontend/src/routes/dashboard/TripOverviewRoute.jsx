import { useEffect, useState } from 'react';
import { getSession } from '@/lib/session';


export default function TripOverviewRoute() {
    const [trips, setTrips] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = getSession()?.token;

        if (!token) return;

        fetch('/trips', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Accept': 'application/json'
            }
        })
            .then(async (res) => {
                if (!res.ok) throw new Error('Fehler beim Laden der Trips');
                return res.json();
            })
            .then(setTrips)
            .catch(console.error)
            .finally(() => setLoading(false));
    }, []);

    return (
        <div className="container py-5">
            <div className="p-4 border rounded bg-light shadow-sm">
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h2 className="text-success m-0">Meine Trips</h2>
                    <a href="/trips/new" className="btn btn-primary">+ Neuer Trip</a>
                </div>

                {loading ? (
                    <p>Lade Trips...</p>
                ) : trips.length === 0 ? (
                    <p className="text-muted">Noch keine Trips erfasst.</p>
                ) : (
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Typ</th>
                            <th>Start</th>
                            <th>Ende</th>
                            <th>Hotels</th>
                            <th>Transporte</th>
                        </tr>
                        </thead>
                        <tbody>
                        {trips.map((trip) => (
                            <tr key={trip.id}>
                                <td>{trip.tripType}</td>
                                <td>{trip.startDate}</td>
                                <td>{trip.endDate}</td>
                                <td>{trip.hotels?.length ?? 0}</td>
                                <td>{trip.transports?.length ?? 0}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}
