package bbw.tm.backend.meeting;

import bbw.tm.backend.address.Address;
import bbw.tm.backend.address.AddressCreateDTO;
import bbw.tm.backend.address.AddressMapper;
import bbw.tm.backend.address.AddressRepository;
import bbw.tm.backend.trip.Trip;
import bbw.tm.backend.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final TripService tripService;
    private final AddressMapper addressMapper;
    private final MeetingMapper meetingMapper;
    private final AddressRepository addressRepository;


    // Hole alle Meetings zu einem bestimmten Trip
    public List<MeetingResponseDTO> getAllMeetingsForTrip(Integer tripId) {
        return meetingRepository.findAllByTripId(tripId).stream()
                .map(meetingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Erstelle ein neues Meeting
    public MeetingResponseDTO createMeeting(MeetingRequestDTO requestDTO, Integer tripId) {
        // Hole den Trip (Validierung über TripService)
        Trip trip = tripService.getTripById(tripId);

        Address location = findOrCreateAddress(requestDTO.location());

        // Baue das Meeting aus den DTOs
        Meeting meeting = new Meeting();
        meeting.setName(requestDTO.name());
        meeting.setDate(requestDTO.date());
        meeting.setStartMeeting(requestDTO.startMeeting());
        meeting.setEndMeeting(requestDTO.endMeeting());
        meeting.setNotes(requestDTO.notes());
        meeting.setLocation(location);
        meeting.setTrip(trip);

        // Speichere in der Datenbank und gebe die Response zurück
        return meetingMapper.toResponseDTO(meetingRepository.save(meeting));
    }

    // Aktualisiere ein vorhandenes Meeting
    public MeetingResponseDTO updateMeeting(Integer meetingId, MeetingRequestDTO requestDTO) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting nicht gefunden"));

        Address location = findOrCreateAddress(requestDTO.location());

        meeting.setName(requestDTO.name());
        meeting.setDate(requestDTO.date());
        meeting.setStartMeeting(requestDTO.startMeeting());
        meeting.setEndMeeting(requestDTO.endMeeting());
        meeting.setNotes(requestDTO.notes());
        meeting.setLocation(location);

        return meetingMapper.toResponseDTO(meetingRepository.save(meeting));
    }

    // Lösche ein Meeting
    public void deleteMeeting(Integer meetingId) {
        if (!meetingRepository.existsById(meetingId)) {
            throw new RuntimeException("Meeting nicht gefunden");
        }
        meetingRepository.deleteById(meetingId);
    }

    // Methode, um eine Adresse zu finden oder zu erstellen
    private Address findOrCreateAddress(AddressCreateDTO addressCreateDTO) {
        // Suche nach vorhandener Adresse
        return addressRepository
                .findByAddress(
                        addressCreateDTO.street(),
                        addressCreateDTO.houseNumber(),
                        addressCreateDTO.zipCode(),
                        addressCreateDTO.city()
                )
                .orElseGet(() -> { // Wenn die Adresse nicht gefunden wird
                    Address newAddress = addressMapper.fromCreateDTO(addressCreateDTO);
                    return addressRepository.save(newAddress); // Neue Adresse speichern
                });
    }

}