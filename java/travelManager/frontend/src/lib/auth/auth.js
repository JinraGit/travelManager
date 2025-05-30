import {saveSession} from "@/lib/session";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export async function signIn({email, password}) {
    const response = await fetch(`${BASE_URL}/accounts/signin`, {
        method: "POST", headers: {
            "content-type": "application/json",
        }, body: JSON.stringify({email, password}),
    });

    if (!response.ok) {
        await response.text();
        throw new Error("Login fehlgeschlagen");
    }

    const session = await response.json();
    saveSession(session);
    return session;
}

export async function signUp({email, username, password}) {
    const response = await fetch(`${BASE_URL}/accounts/signup`, {
        method: "POST", headers: {
            "content-type": "application/json",
        }, body: JSON.stringify({
            email, username, password, role: "USER"
        }),
    });

    if (!response.ok) {
        const errorText = await response.text();
        console.error("Registrierung fehlgeschlagen:", errorText);
        throw new Error("Registrierung fehlgeschlagen");
    }

    return await response.json();
}

