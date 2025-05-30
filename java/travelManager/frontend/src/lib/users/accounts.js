import { getJWTToken } from "@/lib/session.js";

const URL = import.meta.env.VITE_BACKEND_URL;

export async function fetchAccountById(id) {
    const token = getJWTToken();
    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    const response = await fetch(`${URL}/accounts/${id}`, {
        method: "GET",
        headers,
    });

    if (!response.ok) {
        throw new Error(`Fehler beim Abrufen des Accounts: ${response.status}`);
    }

    const account = await response.json();

    return account ;
}
