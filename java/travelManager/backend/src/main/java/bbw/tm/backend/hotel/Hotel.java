package bbw.tm.backend.hotel;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.common.BaseEntity;
import bbw.tm.backend.trip.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    @Column(nullable = false)
    private Double price;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "hotel")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    // Setter für Address-Objekt, das gleich die Bidirektionalität aufrechterhält
    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setHotel(this);
        }
    }
}