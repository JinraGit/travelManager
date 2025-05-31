package bbw.tm.backend.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    List<Trip> findAllByAccountId(Integer accountId);

    Optional<Trip> findByIdAndAccountId(Integer id, Integer accountId); // Neu
}