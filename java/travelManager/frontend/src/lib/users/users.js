import {getJWTToken} from "@/lib/session.js"

const URL = import.meta.env.VITE_BACKEND_URL;

export async function fetchUsers() {
    const response = await fetch(`${URL}/accounts`, {
        headers: {
            'authorization': `Bearer ${getJWTToken()}`,
        },
    });

    if (!response.ok) {
        throw new Error(`HTTP Error: status ${response.status}`);
    }

    return await response.json();
}

export async function deleteUser(id) {
    const token = getJWTToken();
    const response = await fetch(`${URL}/accounts/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    if (response.status === 404) {
        throw new Error("Benutzer nicht gefunden (404)");
    }

    if (!response.ok) {
        throw new Error(`Fehler beim Löschen: ${response.status}`);
    }

    return true;
}