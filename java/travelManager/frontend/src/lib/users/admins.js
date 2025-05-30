import {getJWTToken} from "@/lib/session";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export async function createAdmin({username, email, password}) {
    const token = getJWTToken();
    const body = {
        username,
        email,
        password,
        role: "ADMIN",
        enabled: true
    };

    const response = await fetch(`${BASE_URL}/accounts/admin/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(body)
    });

    if (response.status === 409) {
        throw new Error("User already exists (409)");
    }

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}
