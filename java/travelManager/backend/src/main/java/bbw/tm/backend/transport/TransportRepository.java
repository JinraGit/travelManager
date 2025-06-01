package bbw.tm.backend.transport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Integer> {

    // Alle Transportmittel, die mit einem bestimmten Trip verknüpft sind, abrufen
    List<Transport> findAllByTripId(Integer tripId);
}