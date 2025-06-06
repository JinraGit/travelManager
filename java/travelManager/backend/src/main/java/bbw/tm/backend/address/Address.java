package bbw.tm.backend.address;

import bbw.tm.backend.common.BaseEntity;
import bbw.tm.backend.hotel.Hotel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseEntity {

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String houseNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @OneToOne
    private Hotel hotel;
}
