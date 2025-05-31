package bbw.tm.backend.trip;

import bbw.tm.backend.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripResponseDTO {

    private Integer id;
    private String user;
    private TripType tripType;
    private LocalDate startDate;
    private LocalDate endDate;
}