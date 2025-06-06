package bbw.tm.backend.transport;

import bbw.tm.backend.enums.TransportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TransportResponseDTO {

    private Integer id; // Transport-ID
    private TransportType type; // Transportmitteltyp
    private LocalDate date; // Datum
    private Double price; // Preis

    private String departureHour; // Abreise-Stunde
    private String departureMinute; // Abreise-Minute
    private String arrivalHour; // Ankunfts-Stunde
    private String arrivalMinute; // Ankunfts-Minute

    private String licensePlate; // Kennzeichen
    private String airline; // Fluggesellschaft
    private String trainNumber; // Zugnummer
    private String busNumber; // Busnummer


}