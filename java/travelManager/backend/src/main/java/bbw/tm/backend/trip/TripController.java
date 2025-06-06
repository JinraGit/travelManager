package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @Operation(summary = "Erstellt einen neuen Trip für den Account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trip erfolgreich erstellt."),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabeparameter.")
    })
    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(
            @Valid @RequestBody TripRequestDTO requestDTO,
            @AuthenticationPrincipal Account account) {
        TripResponseDTO createdTrip = tripService.createTrip(requestDTO, account);
        return ResponseEntity.status(201).body(createdTrip);
    }

    @Operation(summary = "Liefert alle Trips des Accounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trips erfolgreich abgerufen.")
    })
    @GetMapping
    public ResponseEntity<List<TripResponseDTO>> getAllTrips(@AuthenticationPrincipal Account account) {
        List<TripResponseDTO> trips = tripService.getAllTripsForAccount(account);
        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "Liefert einen bestimmten Trip des Accounts anhand der ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip erfolgreich abgerufen."),
            @ApiResponse(responseCode = "404", description = "Trip wurde nicht gefunden.")
    })
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