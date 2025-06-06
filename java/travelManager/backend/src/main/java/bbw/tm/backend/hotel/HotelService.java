package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.address.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.name());
        hotel.setCheckInDate(hotelDTO.checkInDate());
        hotel.setCheckOutDate(hotelDTO.checkOutDate());
        hotel.setPrice(hotelDTO.price());

        Address address = new Address();
        address.setStreet(hotelDTO.address().street());
        address.setHouseNumber(hotelDTO.address().houseNumber());
        address.setCity(hotelDTO.address().city());
        address.setZipCode(hotelDTO.address().zipCode());
        hotel.setAddress(address);

        Hotel savedHotel = hotelRepository.save(hotel);
        return mapToDTO(savedHotel);
    }

    private HotelDTO mapToDTO(Hotel hotel) {
        Address address = hotel.getAddress();
        AddressDTO addressDTO = new AddressDTO(
            address.getId(),
            address.getStreet(),
            address.getHouseNumber(),
            address.getCity(),
            address.getZipCode()
        );

        return new HotelDTO(
            hotel.getId(),
            hotel.getName(),
            addressDTO,
            hotel.getCheckInDate(),
            hotel.getCheckOutDate(),
            hotel.getPrice()
        );
    }
}