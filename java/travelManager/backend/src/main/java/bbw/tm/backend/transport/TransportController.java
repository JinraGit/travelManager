package bbw.tm.backend.transport;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transports")
@RequiredArgsConstructor
public class TransportController {

    private final TransportService transportService;
    private final TripService tripService;

    // Hinzufügen eines Transportmittels zu einem Trip
    @PostMapping("/{tripId}")
    public ResponseEntity<TransportResponseDTO> addTransportToTrip(
            @PathVariable Integer tripId,
            @Valid @RequestBody TransportRequestDTO requestDTO,
            @AuthenticationPrincipal Account account) {

        TransportResponseDTO transport = transportService.createTransport(tripId, requestDTO, account);
        return ResponseEntity.status(HttpStatus.CREATED).body(transport);
    }

    // Abrufen aller Transportmittel eines Trips
    @GetMapping("/{tripId}")
    public ResponseEntity<List<TransportResponseDTO>> getAllTransportsForTrip(
            @PathVariable Integer tripId,
            @AuthenticationPrincipal Account account) {

        List<TransportResponseDTO> transports = transportService.getAllTransportsForTrip(tripId, account);
        return ResponseEntity.ok(transports);
    }
}