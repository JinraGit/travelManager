package bbw.tm.backend.hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByNameAndCheckInDate(String name, LocalDate checkInDate);
}