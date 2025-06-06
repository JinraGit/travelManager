package bbw.tm.backend.hotel;

import bbw.tm.backend.address.AddressDTO;

import java.time.LocalDate;

public record HotelDTO(
    Integer id,
    String name,
    AddressDTO address,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    Double price
) {}