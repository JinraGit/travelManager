package bbw.tm.backend.hotel;



import bbw.tm.backend.address.AddressCreateDTO;

import java.time.LocalDate;
import java.util.List;

public record HotelCreateDTO(
    String name,
    AddressCreateDTO address,
    LocalDate checkInDate,
    LocalDate checkOutDate
){}