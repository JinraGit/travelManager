export function buildHotelPayload(form) {
    return {
        name: form.hotel.name,
        address: form.hotel.address,
        checkInDate: form.hotel.checkInDate,
        checkOutDate: form.hotel.checkOutDate
    };
}
