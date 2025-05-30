import {atom, useAtom} from "jotai";
import {useEffect} from "react";

const STORAGE_KEY = "session";
const SESSION_EVENT = "session-changed";

export function getSession() {
    const session = localStorage.getItem(STORAGE_KEY);
    return session ? JSON.parse(session) : null;
}

export function saveSession(session) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
    window.dispatchEvent(new CustomEvent(SESSION_EVENT, {detail: session}));
}

export function removeSession() {
    localStorage.removeItem(STORAGE_KEY);
    window.dispatchEvent(new CustomEvent(SESSION_EVENT, {detail: null}));
}

const sessionAtom = atom(getSession());

export function useSession() {
    const [session, setSession] = useAtom(sessionAtom)

    useEffect(() => {
        const savedSession = localStorage.getItem(STORAGE_KEY)
        if (savedSession) {
            try {
                const sessionValues = JSON.parse(savedSession)
                setSession(sessionValues)
            } catch (e) {
                console.error(e)
            }
        }

        const handleStorageChange = (event) => {
            if (event.key === STORAGE_KEY) {
                try {
                    if (event.newValue) {
                        setSession(JSON.parse(event.newValue))
                    } else {
                        setSession(null)
                    }
                } catch (e) {
                    console.error(e)
                }
            }
        }

        const handleCustomSessionChange = (event) => {
            setSession(event.detail)
        }

        window.addEventListener("storage", handleStorageChange)
        window.addEventListener(SESSION_EVENT, handleCustomSessionChange)

        return () => {
            window.removeEventListener("storage", handleStorageChange)
            window.removeEventListener(SESSION_EVENT, handleCustomSessionChange)
        }
    }, [])

    return session
}

export function getJWTToken() {
    const session = getSession();
    return session?.token || null;
}

export function useCurrentUser() {
    const session = useSession()
    const test = session?.account
    return test
}


export function decodeJWT(token) {
    if (!token) throw new Error("Token nicht vorhanden");

    try {
        const payload = token.split(".")[1];
        const decoded = atob(payload);
        return JSON.parse(decoded);
    } catch (e) {
        console.error("Fehler beim Dekodieren des Tokens:", e);
        return null;
    }
}

export function getUserIdFromToken() {
    const token = getJWTToken();
    if (!token) return null;

    const decoded = decodeJWT(token);
    return decoded?.sub ? parseInt(decoded.sub) : null;
}

export function getUserRoles() {
    const token = getJWTToken();
    if (!token) return [];

    const decoded = decodeJWT(token);
    return decoded?.roles || [];
}

