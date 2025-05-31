package bbw.tm.backend.trip;

import bbw.tm.backend.account.Account;

public class TripMapper {

    // Konvertiert von RequestDTO zu Trip-Entity
    public static Trip toTrip(TripRequestDTO requestDTO, Account account) {
        Trip trip = new Trip();
        trip.setUser(account.getUsername());
        trip.setTripType(requestDTO.getTripType());
        trip.setStartDate(requestDTO.getStartDate());
        trip.setEndDate(requestDTO.getEndDate());
        trip.setAccount(account);
        return trip;
    }

    // Konvertiert von Trip-Entity zu ResponseDTO
    public static TripResponseDTO toResponseDTO(Trip trip) {
        return new TripResponseDTO(
            trip.getId(),
            trip.getUser(),
            trip.getTripType(),
            trip.getStartDate(),
            trip.getEndDate()
        );
    }
}