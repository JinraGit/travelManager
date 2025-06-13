package bbw.tm.backend.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    List<Meeting> findAllByTripId(Integer tripId);

}
