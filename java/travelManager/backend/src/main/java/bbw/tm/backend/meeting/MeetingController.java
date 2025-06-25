package bbw.tm.backend.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/list/{tripId}")
    public ResponseEntity<List<MeetingResponseDTO>> getMeetings(@PathVariable Integer tripId, @RequestParam Integer accountId) {
        List<MeetingResponseDTO> meetings = meetingService.getMeetings(tripId, accountId);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponseDTO> getMeetingById(@PathVariable Integer meetingId, @RequestParam Integer accountId) {
        MeetingResponseDTO meeting = meetingService.getMeetingById(meetingId, accountId);
        return ResponseEntity.ok(meeting);
    }


    @PostMapping("/create")
    public ResponseEntity<MeetingResponseDTO> createMeeting(@RequestParam Integer accountId, @RequestBody MeetingRequestDTO dto) {
        MeetingResponseDTO createdMeeting = meetingService.createMeeting(dto, accountId);
        return ResponseEntity.ok(createdMeeting);
    }

    @PutMapping("/edit/{meetingId}")
    public ResponseEntity<MeetingResponseDTO> updateMeeting(@PathVariable Integer meetingId, @RequestParam Integer accountId, @RequestBody MeetingRequestDTO dto) {
        MeetingResponseDTO updatedMeeting = meetingService.updateMeeting(meetingId, dto, accountId);
        return ResponseEntity.ok(updatedMeeting);
    }

    @DeleteMapping("/delete/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Integer meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }
}