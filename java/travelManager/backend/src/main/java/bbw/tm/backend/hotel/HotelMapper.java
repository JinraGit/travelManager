package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.address.AddressDTO;
import bbw.tm.backend.address.AddressCreateDTO;
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

    public Hotel fromCreateDTO(HotelCreateDTO hotelCreateDTO) {
        Hotel hotel = new Hotel();

        hotel.setName(hotelCreateDTO.name());
        hotel.setCheckInDate(hotelCreateDTO.checkInDate());
        hotel.setCheckOutDate(hotelCreateDTO.checkOutDate());
        hotel.setPrice(hotelCreateDTO.price());

        if (hotelCreateDTO.address() != null) {
            Address address = new Address();
            address.setStreet(hotelCreateDTO.address().street());
            address.setHouseNumber(hotelCreateDTO.address().houseNumber());
            address.setCity(hotelCreateDTO.address().city());
            address.setZipCode(hotelCreateDTO.address().zipCode());
            hotel.setAddress(address);
        }

        return hotel;
    }
    public void updateFromDTO(Hotel hotel, HotelDTO hotelDTO) {
        // Aktualisiere nur die Felder, die im HotelDTO gesetzt sind
        if (hotelDTO.name() != null) {
            hotel.setName(hotelDTO.name());
        }
        if (hotelDTO.checkInDate() != null) {
            hotel.setCheckInDate(hotelDTO.checkInDate());
        }
        if (hotelDTO.checkOutDate() != null) {
            hotel.setCheckOutDate(hotelDTO.checkOutDate());
        }
        if (hotelDTO.price() != null) {
            hotel.setPrice(hotelDTO.price());
        }

        if (hotelDTO.address() != null) {
            Address address = hotel.getAddress();
            if (address == null) {
                address = new Address();
                hotel.setAddress(address);
            }

            if (hotelDTO.address().street() != null) {
                address.setStreet(hotelDTO.address().street());
            }
            if (hotelDTO.address().houseNumber() != null) {
                address.setHouseNumber(hotelDTO.address().houseNumber());
            }
            if (hotelDTO.address().city() != null) {
                address.setCity(hotelDTO.address().city());
            }
            if (hotelDTO.address().zipCode() != null) {
                address.setZipCode(hotelDTO.address().zipCode());
            }
        }
    }
    public Hotel toEntity(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDTO.id()); // Setze die ID, falls sie vorhanden ist
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