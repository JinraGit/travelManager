const BASE_URL = import.meta.env.VITE_BACKEND_URL;
import { getJWTToken } from "@/lib/session.js";

export async function fetchAllTrips() {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/trips`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Laden der Reisen");
    return await response.json();
}

export async function fetchTripById(id) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/trips/${id}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Laden der Reise");
    return await response.json();
}

export async function createTrip(data) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/trips`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Fehler beim Erstellen der Reise");
    return await response.json();
}

export async function updateTrip(id, data) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/trips/${id}`, {
        method: "PATCH",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Fehler beim Aktualisieren der Reise");
    return await response.json();
}

export async function deleteTrip(id) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/trips/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Löschen der Reise");
}
