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

// Meetings für einen bestimmten Trip abrufen
export async function getMeetings(tripId, accountId) {
    return fetchWithAuth(`/meetings/list/${tripId}?accountId=${accountId}`);
}

// Ein neues Meeting erstellen
export async function createMeeting(meetingData, accountId) {
    return fetchWithAuth(`/meetings/create?accountId=${accountId}`, {
        method: "POST",
        body: JSON.stringify(meetingData)
    });
}

// Ein bestehendes Meeting aktualisieren
export async function updateMeeting(meetingId, meetingData, accountId) {
    return fetchWithAuth(`/meetings/edit/${meetingId}?accountId=${accountId}`, {
        method: "PUT",
        body: JSON.stringify(meetingData)
    });
}

// Ein Meeting löschen
export async function deleteMeeting(meetingId) {
    await fetchWithAuth(`/meetings/delete/${meetingId}`, {
        method: "DELETE"
    });

    return true;
}