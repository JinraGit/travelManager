import {useEffect, useState} from "react";

export default function WelcomeRoute() {
    const [username, setUsername] = useState("");

    useEffect(() => {
        const session = JSON.parse(localStorage.getItem("session"));
        const name = session?.account?.username || "Benutzer";
        setUsername(name);
    }, []);

    return (
        <div className="container py-5 text-center">
            <div className="p-4 border rounded bg-light shadow-sm">
                <img
                    src="/assets/tm_logo.png"
                    alt="LEO Logo"
                    style={{maxWidth: "180px"}}
                    className="mb-4"
                />
                <h1 className="text-success">Willkommen zurück, {username}!</h1>
                <p className="text-muted fs-5 mt-3">
                    Schön, dass du wieder da bist. Nutze das Menü links, um auf deine Funktionen zuzugreifen.
                </p>
            </div>
        </div>
    );
}
