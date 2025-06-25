export function formatDateEU(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString("de-CH", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    });
}