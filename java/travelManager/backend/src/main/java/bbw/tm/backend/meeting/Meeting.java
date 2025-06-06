package bbw.tm.backend.meeting;

import bbw.tm.backend.common.BaseEntity;
import bbw.tm.backend.trip.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Meeting extends BaseEntity {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private String location;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}