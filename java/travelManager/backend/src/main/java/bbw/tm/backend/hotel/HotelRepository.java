package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("SELECT h FROM Hotel h WHERE h.name = :name AND " +
           "h.address.street = :street AND h.address.city = :city AND h.address.zipCode = :zipCode")
    Optional<Hotel> findByNameAndAddress(@Param("name") String name,
                                         @Param("street") String street,
                                         @Param("city") String city,
                                         @Param("zipCode") String zipCode);
}