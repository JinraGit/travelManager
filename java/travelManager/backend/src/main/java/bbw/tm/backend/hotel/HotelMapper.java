package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.address.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelDTO toDTO(Hotel hotel) {
        Address address = hotel.getAddress();
        AddressDTO addressDTO = null;

        if (address != null) {
            addressDTO = new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getCity(),
                address.getZipCode()
            );
        }

        return new HotelDTO(
            hotel.getId(),
            hotel.getName(),
            addressDTO,
            hotel.getCheckInDate(),
            hotel.getCheckOutDate(),
            hotel.getPrice()
        );
    }

    public Hotel fromDTO(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.name());
        hotel.setCheckInDate(hotelDTO.checkInDate());
        hotel.setCheckOutDate(hotelDTO.checkOutDate());
        hotel.setPrice(hotelDTO.price());

        if (hotelDTO.address() != null) {
            Address address = new Address();
            address.setStreet(hotelDTO.address().street());
            address.setHouseNumber(hotelDTO.address().houseNumber());
            address.setCity(hotelDTO.address().city());
            address.setZipCode(hotelDTO.address().zipCode());
            hotel.setAddress(address);
        }

        return hotel;
    }
}