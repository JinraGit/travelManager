export function getTransportLabel(type) {
    switch (type) {
        case "CAR":
            return "Auto";
        case "TRAIN":
            return "Bahn";
        case "AIRPLANE":
            return "Flugzeug";
        case "BUS":
            return "Bus";
        default:
            return type;
    }
}
