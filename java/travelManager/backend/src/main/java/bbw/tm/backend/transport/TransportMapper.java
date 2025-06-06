package bbw.tm.backend.transport;

import bbw.tm.backend.trip.Trip;

public class TransportMapper {

    // Konvertiere von RequestDTO zu Entity
    public static Transport toTransport(TransportRequestDTO dto, Trip trip) {
        Transport transport = new Transport();
        transport.setType(dto.getType());
        transport.setDate(dto.getDate());
        transport.setPrice(dto.getPrice());
        transport.setDepartureTime(dto.getDepartureTime());
        transport.setArrivalTime(dto.getArrivalTime());
        transport.setTrip(trip);

        // Nur relevante Felder setzen
        switch (dto.getType()) {
            case CAR -> transport.setLicensePlate(dto.getLicensePlate());
            case AIRPLANE -> transport.setAirline(dto.getAirline());
            case TRAIN -> transport.setTrainNumber(dto.getTrainNumber());
            case BUS -> transport.setBusNumber(dto.getBusNumber());
        }
        return transport;
    }

    // Konvertiere von Entity zu ResponseDTO
    public static TransportResponseDTO toResponseDTO(Transport transport) {
        TransportResponseDTO dto = new TransportResponseDTO();
        dto.setId(transport.getId());
        dto.setType(transport.getType());
        dto.setDate(transport.getDate());
        dto.setPrice(transport.getPrice());
        dto.setDepartureTime(transport.getDepartureTime());
        dto.setArrivalTime(transport.getArrivalTime());
        dto.setLicensePlate(transport.getLicensePlate());
        dto.setAirline(transport.getAirline());
        dto.setTrainNumber(transport.getTrainNumber());
        dto.setBusNumber(transport.getBusNumber());
        return dto;
    }
}