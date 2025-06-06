package bbw.tm.backend.trip;

import bbw.tm.backend.enums.TripType;
import bbw.tm.backend.hotel.HotelDTO;
import bbw.tm.backend.transport.TransportResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TripResponseDTO {

    private Integer id;
    private String user;
    private TripType tripType;
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Transporte hinzufügen
    private List<TransportResponseDTO> transports;

    // Hotels hinzufügen
    private List<HotelDTO> hotels;

}