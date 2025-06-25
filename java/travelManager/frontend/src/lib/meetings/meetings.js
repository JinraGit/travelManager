const BASE_URL = import.meta.env.VITE_BACKEND_URL;
import { getJWTToken } from "@/lib/session.js";

export async function fetchMeetings(tripId, accountId) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/meetings/list/${tripId}?accountId=${accountId}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Laden der Meetings");
    return await response.json();
}

export async function fetchMeetingById(meetingId, accountId) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/meetings/${meetingId}?accountId=${accountId}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Laden des Meetings");
    return await response.json();
}

export async function createMeeting(accountId, data) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/meetings/create?accountId=${accountId}`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Fehler beim Erstellen des Meetings");
    return await response.json();
}

export async function updateMeeting(meetingId, accountId, data) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/meetings/edit/${meetingId}?accountId=${accountId}`, {
        method: "PUT",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Fehler beim Aktualisieren des Meetings");
    return await response.json();
}

export async function deleteMeeting(meetingId) {
    const token = getJWTToken();

    const response = await fetch(`${BASE_URL}/meetings/delete/${meetingId}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        },
        credentials: "include"
    });

    if (!response.ok) throw new Error("Fehler beim Löschen des Meetings");
}
