package bbw.tm.backend.transport;

import bbw.tm.backend.trip.Trip;

public class TransportMapper {

    // Konvertiere von RequestDTO zu Entity
    public static Transport toTransport(TransportRequestDTO dto, Trip trip) {
        Transport transport = new Transport();
        transport.setType(dto.getType());
        transport.setDate(dto.getDate());
        transport.setPrice(dto.getPrice());
        transport.setTrip(trip);

        // Direkt die Werte aus dem DTO auf die Entity setzen
        if (dto.getDepartureHour() != null && dto.getDepartureMinute() != null) {
            transport.setDepartureHour(dto.getDepartureHour());
            transport.setDepartureMinute(dto.getDepartureMinute());
        }
        if (dto.getArrivalHour() != null && dto.getArrivalMinute() != null) {
            transport.setArrivalHour(dto.getArrivalHour());
            transport.setArrivalMinute(dto.getArrivalMinute());
        }

        // Nur relevante Felder abhängig vom Typ setzen
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

        // Stunden und Minuten in ResponseDTO schreiben
        dto.setDepartureHour(transport.getDepartureHour());
        dto.setDepartureMinute(transport.getDepartureMinute());
        dto.setArrivalHour(transport.getArrivalHour());
        dto.setArrivalMinute(transport.getArrivalMinute());

        // Relevante Felder abhängig vom Typ setzen
        switch (transport.getType()) {
            case AIRPLANE -> dto.setAirline(transport.getAirline());
            case CAR -> dto.setLicensePlate(transport.getLicensePlate());
            case TRAIN -> dto.setTrainNumber(transport.getTrainNumber());
            case BUS -> dto.setBusNumber(transport.getBusNumber());
        }

        return dto;
    }
}