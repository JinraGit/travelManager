package bbw.tm.backend.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    // Gibt alle Trips zurück, die mit der Account-ID verknüpft sind
    List<Trip> findAllByAccountId(Integer accountId);

    // Gibt einen einzelnen Trip zurück, falls er mit der angegebenen Account-ID verknüpft ist
    Optional<Trip> findByIdAndAccountId(Integer id, Integer accountId);
}