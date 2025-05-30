export function validateRegistration(registration) {
    const errors = {
        email: "",
        username: "",
        password: "",
    };

    let isValid = true;

    if (registration.email.trim().length === 0 || !registration.email.includes("@")) {
        errors.email = "Die E-Mail darf nicht leer sein und muss eine gültige E-Mail-Adresse sein.";
        isValid = false;
    }

    if (registration.username.trim().length === 0) {
        errors.username = "Der Benutzername darf nicht leer sein.";
        isValid = false;
    }

    if (registration.password.trim().length === 0 || registration.password.length < 8) {
        errors.password = "Das Passwort darf nicht leer sein und muss mindestens 8 Zeichen lang sein.";
        isValid = false;
    }

    return {errors, isValid};
}
