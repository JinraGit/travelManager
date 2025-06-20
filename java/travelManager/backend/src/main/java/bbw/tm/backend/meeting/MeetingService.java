package bbw.tm.backend.meeting;

import bbw.tm.backend.FailedValidationException;
import bbw.tm.backend.account.Account;
import bbw.tm.backend.trip.Trip;
import bbw.tm.backend.trip.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final TripRepository tripRepository;
    private final MeetingMapper meetingMapper;

    public List<MeetingResponseDTO> getMeetings(Integer tripId, Integer accountId) {
        // Validierung des Trips anhand der Account-ID
        Trip trip = getTripByIdAndAccount(tripId, accountId);

        return meetingRepository.findAllByTripIdAndTripAccountId(trip.getId(), accountId).stream()
                .map(meetingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MeetingResponseDTO createMeeting(MeetingRequestDTO dto, Integer accountId) {
        // Validierung des Trips anhand der Account-ID
        Trip trip = getTripByIdAndAccount(dto.getTripId(), accountId);

        Meeting meeting = meetingMapper.toEntity(dto);
        meeting.setTrip(trip); // Verknüpfen mit dem Trip

        return meetingMapper.toDto(meetingRepository.save(meeting));
    }

    /**
     * Aktualisiert ein bestehendes Meeting.
     *
     * @param meetingId ID des Meetings.
     * @param dto       Die neuen Meeting-Daten.
     * @return Das aktualisierte Meeting als Response-DTO.
     */
    @Transactional
    public MeetingResponseDTO updateMeeting(Integer meetingId, MeetingRequestDTO dto, Integer accountId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Meeting wurde nicht gefunden"))
                ));

        // Überprüfung: Gehört das Meeting zum Benutzer?
        getTripByIdAndAccount(meeting.getTrip().getId(), accountId);

        // Meeting-Details aktualisieren
        meeting.setName(dto.getName());
        meeting.setDate(dto.getDate());
        meeting.setStartMeeting(dto.getStartMeeting());
        meeting.setEndMeeting(dto.getEndMeeting());
        meeting.setNotes(dto.getNotes());

        return meetingMapper.toDto(meetingRepository.save(meeting));
    }

    /**
     * Löscht ein Meeting eines Trips, wenn der Benutzer berechtigt ist.
     *
     * @param meetingId ID des Meetings.
     */
    @Transactional
    public void deleteMeeting(Integer meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("id", List.of("Meeting wurde nicht gefunden"))
                ));


        meetingRepository.delete(meeting);
    }

    private Trip getTripByIdAndAccount(Integer tripId, Integer accountId) {
        return tripRepository.findByIdAndAccountId(tripId, accountId)
                .orElseThrow(() -> new FailedValidationException(
                        Map.of("tripId", List.of("Trip wurde nicht gefunden oder gehört nicht zu Ihrem Account"))
                ));
    }
}