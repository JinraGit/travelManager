package bbw.tm.backend.trip;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripRequestDTO {

    @NotNull
    private String user;

    @NotNull
    private String tripType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}