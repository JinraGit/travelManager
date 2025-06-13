package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;
import bbw.tm.backend.hotel.HotelMapper;
import bbw.tm.backend.transport.TransportMapper;


import java.util.stream.Collectors;

public class TripMapper {

    private static final HotelMapper hotelMapper = new HotelMapper();

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
                trip.getTransports().stream()
                        .map(TransportMapper::toResponseDTO)
                        .collect(Collectors.toList()),
                trip.getHotels().stream()
                        .map(hotelMapper::toDTO) // Hotels mappen
                        .toList()
        );

    }
}