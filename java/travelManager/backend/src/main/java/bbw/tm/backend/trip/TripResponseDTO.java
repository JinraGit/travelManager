package bbw.tm.backend.trip;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripResponseDTO {

    private Integer id;
    private String user;
    private String tripType;
    private LocalDate startDate;
    private LocalDate endDate;
}