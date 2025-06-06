package bbw.tm.backend.trip;

import bbw.tm.backend.enums.TripType;
import bbw.tm.backend.transport.TransportRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TripRequestDTO {

    @NotNull
    private TripType tripType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Valid
    private List<TransportRequestDTO> transports;

}