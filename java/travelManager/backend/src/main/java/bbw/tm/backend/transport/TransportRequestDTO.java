package bbw.tm.backend.transport;

import bbw.tm.backend.enums.TransportType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TransportRequestDTO {

    @NotNull
    private TransportType type; // Transportmitteltyp

    @NotNull
    private LocalDate date; // Datum

    @NotNull
    private Double price; // Preis

    private LocalTime departureTime; // Abreisezeit (optional)

    private LocalTime arrivalTime; // Ankunftszeit (optional)

    private String licensePlate; // Kennzeichen (für Auto)

    private String airline; // Fluggesellschaft (für Flugzeug)

    private String trainNumber; // Zugnummer (für Zug)

    private String busNumber; // Busnummer (für Bus)
}