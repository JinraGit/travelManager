package bbw.tm.backend.trip;

import bbw.tm.backend.FailedValidationException;
import bbw.tm.backend.account.Account;
import bbw.tm.backend.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelService hotelService;

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
     * Holt einen Trip anhand der ID und authentifiziertem Account direkt als Entity.
     * Diese Methode wird für andere Services benötigt, z. B. für Transport.
     *
     * @param id      Die ID des Trips.
     * @param account Der Account, dessen Trips überprüft werden.
     * @return Der gesuchte Trip als Entity.
     */
    public Trip getTripByIdAndAccount(Integer id, Account account) {
        return tripRepository.findByIdAndAccountId(id, account.getId())
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Trip wurde nicht gefunden oder gehört nicht zu Ihrem Account"))
                ));
    }

    /**
     * Erstellt einen neuen Trip für einen bestimmten Account, einschließlich eines Hotels,
     * falls es im Request angegeben ist.
     *
     * @param requestDTO Die Daten des neuen Trips.
     * @param account    Der Account, für den der Trip erstellt werden soll.
     * @return Der erstellte Trip als ResponseDTO.
     */
    public TripResponseDTO createTrip(TripRequestDTO requestDTO, Account account) {
        Trip trip = TripMapper.toTrip(requestDTO, account);

        // Hotels erstellen oder vorhandene Hotels verwenden
        if (requestDTO.getHotels() != null && !requestDTO.getHotels().isEmpty()) {
            List<Hotel> hotels = requestDTO.getHotels().stream()
                    .map(this::findOrCreateHotel) // Hotel finden oder erstellen
                    .toList();
            trip.setHotels(hotels);


            // Sicherstellen, dass der Trip im Hotel gesetzt wird (bidirektionale Beziehung)
            for (Hotel hotel : hotels) {
                if (!hotel.getTrips().contains(trip)) {
                    hotel.getTrips().add(trip);
                }
            }
        }

        // Trip speichern (inklusive verknüpfter Hotels)
        Trip savedTrip = tripRepository.save(trip);
        return TripMapper.toResponseDTO(savedTrip);
    }




    /**
     * Aktualisiert einen vorhandenen Trip für einen Account, einschließlich eines Hotels.
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

        // Hotels aktualisieren oder hinzufügen
        if (requestDTO.getHotels() != null && !requestDTO.getHotels().isEmpty()) {
            List<Hotel> hotels = requestDTO.getHotels().stream()
                    .map(this::findOrCreateHotel)
                    .toList();
            for (Hotel hotel : hotels) {
                hotel.getTrips().add(trip); // Trip im Hotel setzen
            }
            trip.setHotels(hotels);
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

    /**
     * Hilfsmethode, um ein Hotel zu finden oder bei Bedarf zu erstellen.
     *
     */
    private Hotel findOrCreateHotel(HotelCreateDTO hotelCreateDTO) {
        // Prüfe, ob das Hotel bereits existiert
        Optional<Hotel> existingHotel = hotelRepository.findByNameAndCheckInDate(
                hotelCreateDTO.name(),
                hotelCreateDTO.checkInDate()
        );

        if (existingHotel.isPresent()) {
            // Clonen, um Konflikte in Trip-Zuordnungen zu verhindern
            Hotel original = existingHotel.get();
            Hotel clonedHotel = new Hotel();

            clonedHotel.setName(original.getName());
            clonedHotel.setCheckInDate(original.getCheckInDate());
            clonedHotel.setCheckOutDate(original.getCheckOutDate());
            clonedHotel.setAddress(original.getAddress()); // Falls Address-Informationen mitkopiert werden sollen

            return clonedHotel; // Neuer Datensatz wird erstellt
        }

        // Neues Hotel erstellen, wenn keins gefunden wurde
        return hotelMapper.fromCreateDTO(hotelCreateDTO);
    }
}