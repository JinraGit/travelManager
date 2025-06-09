package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.common.BaseEntity;
import bbw.tm.backend.trip.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Hotel extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hotel")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "trip_hotel", // Name der Zwischentabelle
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trip> trips = new ArrayList<>();

    // Setter für Address-Objekt, das gleich die Bidirektionalität aufrechterhält
    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setHotel(this);
        }
    }
}