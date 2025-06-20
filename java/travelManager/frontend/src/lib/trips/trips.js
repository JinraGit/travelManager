import { getJWTToken } from "@/lib/session";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

async function fetchWithAuth(url, options = {}) {
    const token = getJWTToken();
    if (!token) {
        throw new Error("Keine Authentifizierung vorhanden");
    }

    const headers = {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
        ...options.headers
    };

    const response = await fetch(`${BASE_URL}${url}`, {
        ...options,
        headers
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Fehler bei der Anfrage");
    }

    return response.json();
}

export async function createTrip(tripData) {
    return fetchWithAuth("/trips", {
        method: "POST",
        body: JSON.stringify(tripData)
    });
}

export async function getAllTrips() {
    return fetchWithAuth("/trips");
}

export async function getTripById(id) {
    return fetchWithAuth(`/trips/${id}`);
}

export async function updateTrip(id, tripData) {
    return fetchWithAuth(`/trips/${id}`, {
        method: "PATCH",
        body: JSON.stringify(tripData)
    });
}

export async function deleteTrip(id) {
    await fetchWithAuth(`/trips/${id}`, {
        method: "DELETE"
    });

    return true;
}
