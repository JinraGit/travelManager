package bbw.tm.backend.transport;

import bbw.tm.backend.enums.TransportType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TransportResponseDTO {

    private Integer id; // Transport-ID
    private TransportType type; // Transportmitteltyp
    private LocalDate date; // Datum
    private Double price; // Preis
    private LocalTime departureTime; // Abreisezeit
    private LocalTime arrivalTime; // Ankunftszeit
    private String licensePlate; // Kennzeichen
    private String airline; // Fluggesellschaft
    private String trainNumber; // Zugnummer
    private String busNumber; // Busnummer
}