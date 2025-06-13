package bbw.tm.backend.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.street = :street " +
           "AND a.houseNumber = :houseNumber " +
           "AND a.city = :city " +
           "AND a.zipCode = :zipCode")
    Optional<Address> findByAddress(
            @Param("street") String street,
            @Param("houseNumber") String houseNumber,
            @Param("city") String city,
            @Param("zipCode") String zipCode);
}