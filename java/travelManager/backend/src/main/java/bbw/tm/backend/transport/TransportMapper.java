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
        transport.setLicensePlate(dto.getLicensePlate());
        transport.setAirline(dto.getAirline());
        transport.setTrainNumber(dto.getTrainNumber());
        transport.setBusNumber(dto.getBusNumber());
        transport.setTrip(trip);
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