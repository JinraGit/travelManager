package bbw.tm.backend.trip;

import bbw.tm.backend.enums.TripType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripRequestDTO {

    @NotNull
    private TripType tripType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}