export function mapTripToForm(trip, defaultHotel) {
    const transport = trip.transports?.[0] || {};
    const hotel = trip.hotels?.[0] || { ...defaultHotel };

    return {
        tripType: trip.tripType,
        startDate: trip.startDate,
        endDate: trip.endDate,
        transport: {
            type: transport.type || "CAR",
            date: transport.date || "",
            departureTime: `${transport.departureHour || ""}:${transport.departureMinute || ""}`,
            arrivalTime: `${transport.arrivalHour || ""}:${transport.arrivalMinute || ""}`,
            licensePlate: transport.licensePlate || "",
            airline: transport.airline || "",
            trainNumber: transport.trainNumber || "",
            busNumber: transport.busNumber || ""
        },
        hotel: {
            name: hotel.name || "",
            address: hotel.address || defaultHotel.address,
            checkInDate: hotel.checkInDate || "",
            checkOutDate: hotel.checkOutDate || ""
        }
    };
}