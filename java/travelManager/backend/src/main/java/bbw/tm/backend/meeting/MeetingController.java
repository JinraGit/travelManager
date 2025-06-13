package bbw.tm.backend.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips/{tripId}/meetings") // Meetings immer in Verbindung mit einem Trip
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    // Hole alle Meetings zu einem Trip
    @GetMapping
    public List<MeetingResponseDTO> getAllMeetingsForTrip(@PathVariable Integer tripId) {
        return meetingService.getAllMeetingsForTrip(tripId);
    }

    // Erstelle ein neues Meeting
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingResponseDTO createMeeting(@PathVariable Integer tripId, @RequestBody MeetingRequestDTO requestDTO) {
        return meetingService.createMeeting(requestDTO, tripId);
    }

    // Aktualisiere ein Meeting
    @PutMapping("/{meetingId}")
    public MeetingResponseDTO updateMeeting(@PathVariable Integer meetingId,
                                            @RequestBody MeetingRequestDTO requestDTO) {
        return meetingService.updateMeeting(meetingId, requestDTO);
    }

    // Lösche ein Meeting
    @DeleteMapping("/{meetingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeeting(@PathVariable Integer meetingId) {
        meetingService.deleteMeeting(meetingId);
    }
}