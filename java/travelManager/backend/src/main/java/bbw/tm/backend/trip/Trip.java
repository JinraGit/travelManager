package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.common.BaseEntity;
import bbw.tm.backend.enums.TripType;
import bbw.tm.backend.hotel.Hotel;
import bbw.tm.backend.meeting.Meeting;
import bbw.tm.backend.transport.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Trip extends BaseEntity {

    @Column(nullable = false)
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripType tripType;


    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transport> transports = new ArrayList<>();

    @ManyToMany(mappedBy = "trips", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Hotel> hotels = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> meetings;
}