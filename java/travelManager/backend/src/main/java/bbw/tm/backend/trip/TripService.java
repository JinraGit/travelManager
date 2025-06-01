package bbw.tm.backend.trip;

import bbw.tm.backend.FailedValidationException;
import bbw.tm.backend.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    /**
     * Holt alle Trips des authentifizierten Accounts.
     *
     * @param account Der Account, dessen Trips abgerufen werden sollen.
     * @return Eine Liste der Trips als ResponseDTOs.
     */
    public List<TripResponseDTO> getAllTripsForAccount(Account account) {
        return tripRepository.findAllByAccountId(account.getId()).stream()
                .map(TripMapper::toResponseDTO)
                .toList();
    }

    /**
     * Holt einen bestimmten Trip anhand der ID, wenn er dem Account gehört.
     *
     * @param id      Die ID des Trips.
     * @param account Der Account, dessen Trips überprüft werden.
     * @return Der gesuchte Trip als ResponseDTO.
     */
    public TripResponseDTO getTripByIdForAccount(Integer id, Account account) {
        return tripRepository.findByIdAndAccountId(id, account.getId())
                .map(TripMapper::toResponseDTO)
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Trip wurde nicht gefunden oder gehört nicht zu Ihrem Account"))
                ));
    }

    /**
     * Erstellt einen neuen Trip für einen bestimmten Account.
     *
     * @param requestDTO Die Daten des neuen Trips.
     * @param account    Der Account, für den der Trip erstellt werden soll.
     * @return Der erstellte Trip als ResponseDTO.
     */
    public TripResponseDTO createTrip(TripRequestDTO requestDTO, Account account) {
        Trip trip = TripMapper.toTrip(requestDTO, account);
        Trip savedTrip = tripRepository.save(trip);
        return TripMapper.toResponseDTO(savedTrip);
    }

    /**
     * Aktualisiert einen vorhandenen Trip, wenn er zum Account gehört.
     *
     * @param id         Die ID des zu aktualisierenden Trips.
     * @param requestDTO Die neuen Daten des Trips.
     * @param account    Der Account, dessen Trip aktualisiert werden soll.
     * @return Der aktualisierte Trip als ResponseDTO.
     */
    public TripResponseDTO updateTripForAccount(Integer id, TripRequestDTO requestDTO, Account account) {
        Trip trip = tripRepository.findByIdAndAccountId(id, account.getId())
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Trip wurde nicht gefunden oder gehört nicht zu Ihrem Account"))
                ));

        // Partielle Aktualisierung der Felder
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
     * Löscht einen bestimmten Trip, wenn er zum Account gehört.
     *
     * @param id      Die ID des Trips.
     * @param account Der Account, dessen Trip gelöscht werden soll.
     */
    public void deleteTripForAccount(Integer id, Account account) {
        Trip trip = tripRepository.findByIdAndAccountId(id, account.getId())
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Trip wurde nicht gefunden oder gehört nicht zu Ihrem Account"))
                ));
        tripRepository.delete(trip);
    }
}