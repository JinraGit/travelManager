package bbw.tm.backend.address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // Von AddressCreateDTO zu Address (z. B. bei Address-Erstellung)
    public Address fromCreateDTO(AddressCreateDTO dto) {
        Address address = new Address();
        address.setStreet(dto.street());
        address.setHouseNumber(dto.houseNumber());
        address.setCity(dto.city());
        address.setZipCode(dto.zipCode());
        return address;
    }

    // Von Address zu AddressDTO (z. B. bei Response)
    public AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getCity(),
                address.getZipCode()
        );
    }

    // Optional: Von AddressDTO zu Address (falls Updates benötigt werden, z. B. bei PUT):
    public Address fromDTO(AddressDTO dto) {
        Address address = new Address();
        address.setStreet(dto.street());
        address.setHouseNumber(dto.houseNumber());
        address.setCity(dto.city());
        address.setZipCode(dto.zipCode());
        return address;
    }
}