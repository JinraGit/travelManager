package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @Operation(summary = "Erstellt einen neuen Trip für den aktuellen Account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trip erfolgreich erstellt",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TripResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Ungültiger Eingabeparameter", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(
            @Valid @RequestBody TripRequestDTO requestDTO,
            @AuthenticationPrincipal Account account) {
        TripResponseDTO createdTrip = tripService.createTrip(requestDTO, account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
    }

    @Operation(summary = "Liefert eine Liste aller Trips des aktuellen Accounts.")
    @GetMapping
    public ResponseEntity<List<TripResponseDTO>> getAllTrips(@AuthenticationPrincipal Account account) {
        List<TripResponseDTO> trips = tripService.getAllTripsForAccount(account);
        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "Liefert einen Trip anhand der ID, wenn er zum aktuellen Account gehört.")
    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDTO> getTripById(
            @PathVariable Integer id,
            @AuthenticationPrincipal Account account) {
        TripResponseDTO trip = tripService.getTripByIdForAccount(id, account);
        return ResponseEntity.ok(trip);
    }

    @Operation(summary = "Aktualisiert einen vorhandenen Trip teilweise, wenn er zum aktuellen Account gehört.")
    @PatchMapping("/{id}")
    public ResponseEntity<TripResponseDTO> updateTrip(
            @PathVariable Integer id,
            @Valid @RequestBody TripRequestDTO requestDTO,
            @AuthenticationPrincipal Account account) {
        TripResponseDTO updatedTrip = tripService.updateTripForAccount(id, requestDTO, account);
        return ResponseEntity.ok(updatedTrip);
    }

    @Operation(summary = "Löscht einen Trip, wenn er zum aktuellen Account gehört.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(
            @PathVariable Integer id,
            @AuthenticationPrincipal Account account) {
        tripService.deleteTripForAccount(id, account);
        return ResponseEntity.noContent().build();
    }
}