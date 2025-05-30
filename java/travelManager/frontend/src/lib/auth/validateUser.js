export function validateUser(user) {
    const errors = {};
    if (!user.email?.includes("@")) errors.email = "E-Mail ist ungültig";
    if (!user.password || user.password.length < 8) errors.password = "Mindestens 8 Zeichen erforderlich";
    return {isValid: Object.keys(errors).length === 0, errors};
}
