package bbw.tm.backend.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    // Gibt alle Meetings zurück, die zu einem Trip und einer Account-ID gehören
    List<Meeting> findAllByTripIdAndTripAccountId(Integer tripId, Integer accountId);

    // Prüft, ob ein Meeting mit dieser Trip-ID und Account-ID existiert
    boolean existsByIdAndTripAccountId(Integer meetingId, Integer accountId);
}