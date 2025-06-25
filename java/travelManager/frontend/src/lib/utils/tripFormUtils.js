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

export function handleTripFormChange(e, setForm) {
    const { name, value } = e.target;

    if (name.startsWith("transport.")) {
        const key = name.split(".")[1];
        setForm(prev => ({
            ...prev,
            transport: { ...prev.transport, [key]: value }
        }));
    } else if (name.startsWith("hotel.address.")) {
        const key = name.split(".")[2];
        setForm(prev => ({
            ...prev,
            hotel: {
                ...prev.hotel,
                address: {
                    ...prev.hotel.address,
                    [key]: value
                }
            }
        }));
    } else if (name.startsWith("hotel.")) {
        const key = name.split(".")[1];
        setForm(prev => ({
            ...prev,
            hotel: { ...prev.hotel, [key]: value }
        }));
    } else {
        setForm(prev => ({ ...prev, [name]: value }));
    }
}
