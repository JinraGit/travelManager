package bbw.tm.backend.meeting;

import bbw.tm.backend.address.Address;
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
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String startMeeting;

    @Column(nullable = false)
    private String endMeeting;

    @Column
    private String notes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Address location;


    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}