package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // Suche über Name und Adresse
    Optional<Hotel> findByNameAndAddress(String name, Address address);
}