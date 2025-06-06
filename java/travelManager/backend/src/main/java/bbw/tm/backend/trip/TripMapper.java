package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.transport.Transport;
import bbw.tm.backend.transport.TransportMapper;
import bbw.tm.backend.transport.TransportRequestDTO;

import java.util.stream.Collectors;

public class TripMapper {

    public static Trip toTrip(TripRequestDTO requestDTO, Account account) {
        Trip trip = new Trip();
        trip.setUser(account.getUsername());
        trip.setTripType(requestDTO.getTripType());
        trip.setStartDate(requestDTO.getStartDate());
        trip.setEndDate(requestDTO.getEndDate());
        trip.setAccount(account);

        // Transporte hinzufügen
        if (requestDTO.getTransports() != null) {
            trip.setTransports(requestDTO.getTransports().stream()
                .map(dto -> TransportMapper.toTransport(dto, trip))
                .collect(Collectors.toList()));
        }

        return trip;
    }

    public static TripResponseDTO toResponseDTO(Trip trip) {
        return new TripResponseDTO(
            trip.getId(),
            trip.getUser(),
            trip.getTripType(),
            trip.getStartDate(),
            trip.getEndDate(),
            trip.getTransports().stream().map(TransportMapper::toResponseDTO).toList()
        );
    }
}