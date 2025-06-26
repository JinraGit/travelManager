export function getTransportLabel(type) {
    switch (type) {
        case "CAR":
            return "Auto";
        case "TRAIN":
            return "Zug";
        case "AIRPLANE":
            return "Flugzeug";
        case "BUS":
            return "Bus";
        default:
            return type;
    }
}
export function buildTransportPayload(form) {
    let [depHour = "", depMin = ""] = form.transport.departureTime?.split(":") || [];
    let [arrHour = "", arrMin = ""] = form.transport.arrivalTime?.split(":") || [];

    return {
        type: form.transport.type,
        date: form.transport.date,
        departureHour: depHour,
        departureMinute: depMin,
        arrivalHour: arrHour,
        arrivalMinute: arrMin,
        licensePlate: form.transport.type === "CAR" ? form.transport.licensePlate : null,
        airline: form.transport.type === "AIRPLANE" ? form.transport.airline : null,
        trainNumber: form.transport.type === "TRAIN" ? form.transport.trainNumber : null,
        busNumber: form.transport.type === "BUS" ? form.transport.busNumber : null
    };
}