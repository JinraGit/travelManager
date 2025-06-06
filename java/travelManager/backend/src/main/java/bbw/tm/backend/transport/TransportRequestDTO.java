package bbw.tm.backend.transport;

import bbw.tm.backend.enums.TransportType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "([01][0-9]|2[0-3])", message = "Hour must be between 00 and 23.")
    @Schema(description = "Hour of departure (00-23)", example = "22")
    private String departureHour;

    @Pattern(regexp = "[0-5][0-9]", message = "Minute must be between 00 and 59.")
    @Schema(description = "Minute of departure (00-59)", example = "41")
    private String departureMinute;

    @Pattern(regexp = "([01][0-9]|2[0-3])", message = "Hour must be between 00 and 23.")
    @Schema(description = "Hour of arrival (00-23)", example = "14")
    private String arrivalHour;

    @Pattern(regexp = "[0-5][0-9]", message = "Minute must be between 00 and 59.")
    @Schema(description = "Minute of arrival (00-59)", example = "04")
    private String arrivalMinute;



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
        if (date == null) {
            throw new IllegalArgumentException("Date is required.");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price is required.");
        }


    }

    // Entferne oder ignoriere Getter für LocalTime
    @JsonIgnore
    public LocalTime getDepartureTimeAsLocalTime() {
        if (departureHour != null && departureMinute != null) {
            return LocalTime.of(Integer.parseInt(departureHour), Integer.parseInt(departureMinute));
        }
        return null;
    }

    @JsonIgnore
    public LocalTime getArrivalTimeAsLocalTime() {
        if (arrivalHour != null && arrivalMinute != null) {
            return LocalTime.of(Integer.parseInt(arrivalHour), Integer.parseInt(arrivalMinute));
        }
        return null;
    }


}