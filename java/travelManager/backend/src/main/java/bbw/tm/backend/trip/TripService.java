package bbw.tm.backend.trip;

import bbw.tm.backend.FailedValidationException;
import bbw.tm.backend.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    /**
     * Holt alle Reisen für einen bestimmten Account.
     *
     * @param account Der Account, dessen Trips abgerufen werden sollen
     * @return Liste der Trips in Form von ResponseDTOs
     */
    public List<TripResponseDTO> getAllTripsForAccount(Account account) {
        return tripRepository.findAllByAccountId(account.getId())
                .stream()
                .map(TripMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Holt einen bestimmten Trip anhand der ID, sofern er zum aktuellen Account gehört.
     *
     * @param id      Die ID des Trips
     * @param account Der Account, dessen Trips überprüft werden
     * @return Der gesuchte Trip als ResponseDTO
     */
    public TripResponseDTO getTripByIdForAccount(Integer id, Account account) {
        return tripRepository.findByIdAndAccountId(id, account.getId())
                .map(TripMapper::toResponseDTO)
                .orElseThrow(() -> createFailedValidationException("id", "Trip wurde nicht gefunden"));
    }

    /**
     * Erstellt einen neuen Trip für einen bestimmten Account.
     *
     * @param requestDTO Die Daten des neuen Trips (RequestDTO)
     * @param account    Der Account, für den der Trip erstellt werden soll
     * @return Der erstellte Trip in Form eines ResponseDTOs
     */
    public TripResponseDTO createTrip(TripRequestDTO requestDTO, Account account) {
        Trip trip = TripMapper.toTrip(requestDTO, account);
        Trip savedTrip = tripRepository.save(trip);
        return TripMapper.toResponseDTO(savedTrip);
    }

    /**
     * Aktualisiert einen vorhandenen Trip für einen bestimmten Account, basierend auf den übergebenen Daten.
     *
     * @param id         Die ID des zu aktualisierenden Trips
     * @param requestDTO Die neuen Daten des Trips (RequestDTO)
     * @param account    Der Account, für den der Trip aktualisiert werden soll
     * @return Der aktualisierte Trip als ResponseDTO
     */
    public TripResponseDTO updateTripForAccount(Integer id, TripRequestDTO requestDTO, Account account) {
        Trip trip = tripRepository.findByIdAndAccountId(id, account.getId())
                .orElseThrow(() -> createFailedValidationException("id", "Trip wurde nicht gefunden"));

        // Partielle Aktualisierung
        if (requestDTO.getUser() != null) {
            trip.setUser(requestDTO.getUser());
        }
        if (requestDTO.getTripType() != null) {
            trip.setTripType(requestDTO.getTripType());
        }
        if (requestDTO.getStartDate() != null) {
            trip.setStartDate(requestDTO.getStartDate());
        }
        if (requestDTO.getEndDate() != null) {
            trip.setEndDate(requestDTO.getEndDate());
        }

        Trip updatedTrip = tripRepository.save(trip);
        return TripMapper.toResponseDTO(updatedTrip);
    }

    /**
     * Löscht einen vorhandenen Trip eines Accounts, basierend auf der ID.
     *
     * @param id      Die ID des zu löschenden Trips
     * @param account Der Account, dessen Trip gelöscht werden soll
     */
    public void deleteTripForAccount(Integer id, Account account) {
        Trip trip = tripRepository.findByIdAndAccountId(id, account.getId())
                .orElseThrow(() -> createFailedValidationException("id", "Trip wurde nicht gefunden"));
        tripRepository.delete(trip);
    }

    /**
     * Hilfsmethode zur Erstellung einer FailedValidationException.
     *
     * @param field   Das Feld, das den Fehler verursacht hat
     * @param message Die Fehlermeldung
     * @return Eine Instanz von FailedValidationException
     */
    private FailedValidationException createFailedValidationException(String field, String message) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(field, List.of(message));
        return new FailedValidationException(errors);
    }
}