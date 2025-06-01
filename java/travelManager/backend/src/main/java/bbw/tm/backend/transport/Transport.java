package bbw.tm.backend.transport;

import bbw.tm.backend.enums.TransportType;
import bbw.tm.backend.trip.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportType type; // Enum für Art des Transportmittels

    @Column(nullable = false)
    private LocalDate date; // Datum der Reise

    @Column(nullable = false)
    private Double price; // Preis des Transportmittels
    
    @Column
    private LocalTime departureTime; // Abreisezeit (optional)

    @Column
    private LocalTime arrivalTime; // Ankunftszeit (optional)

    // Spezifische Felder für Transportmittel:
    @Column
    private String licensePlate; // Kennzeichen (für Auto)

    @Column
    private String airline; // Fluggesellschaft (für Flugzeug)

    @Column
    private String trainNumber; // Zugnummer (für Zug)

    @Column
    private String busNumber; // Busnummer (für Bus)

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip; // Beziehung zu Trip
}