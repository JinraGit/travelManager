package bbw.tm.backend.transport;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.trip.Trip;
import bbw.tm.backend.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;
    private final TripService tripService;

    // Erstellen eines Transportmittels
    public TransportResponseDTO createTransport(Integer tripId, TransportRequestDTO requestDTO, Account account) {
        // Überprüfung der Transportdaten
        requestDTO.validate();

        Trip trip = tripService.getTripByIdAndAccount(tripId, account);
        Transport transport = TransportMapper.toTransport(requestDTO, trip);
        Transport savedTransport = transportRepository.save(transport);
        return TransportMapper.toResponseDTO(savedTransport);
    }

    // Alle Transportmittel für einen Trip abrufen
    public List<TransportResponseDTO> getAllTransportsForTrip(Integer tripId, Account account) {
        Trip trip = tripService.getTripByIdAndAccount(tripId, account);
        return trip.getTransports().stream()
                .map(TransportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}