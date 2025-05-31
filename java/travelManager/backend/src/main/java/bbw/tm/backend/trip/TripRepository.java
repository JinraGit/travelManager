package bbw.tm.backend.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query("SELECT t FROM Trip t WHERE t.account.id = :accountId")
    List<Trip> findAllByAccountId(@Param("accountId") Integer accountId);
}