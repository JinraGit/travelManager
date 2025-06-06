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

    public void validate() {
        switch (type) {
            case CAR -> {
                if (licensePlate == null || licensePlate.isBlank()) {
                    throw new IllegalArgumentException("License plate is required for CAR transport type.");
                }
            }
            case AIRPLANE -> {
                if (airline == null || airline.isBlank()) {
                    throw new IllegalArgumentException("Airline is required for AIRPLANE transport type.");
                }
            }
            case TRAIN -> {
                if (trainNumber == null || trainNumber.isBlank()) {
                    throw new IllegalArgumentException("Train number is required for TRAIN transport type.");
                }
            }
            case BUS -> {
                if (busNumber == null || busNumber.isBlank()) {
                    throw new IllegalArgumentException("Bus number is required for BUS transport type.");
                }
            }
            default -> throw new IllegalArgumentException("Invalid transport type specified.");
        }
    }
}