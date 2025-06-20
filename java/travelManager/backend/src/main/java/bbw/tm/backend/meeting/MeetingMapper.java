package bbw.tm.backend.meeting;

import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {

    public Meeting toEntity(MeetingRequestDTO dto) {
        Meeting meeting = new Meeting();
        meeting.setName(dto.getName());
        meeting.setDate(dto.getDate());
        meeting.setStartMeeting(dto.getStartMeeting());
        meeting.setEndMeeting(dto.getEndMeeting());
        meeting.setNotes(dto.getNotes());
        return meeting;
    }

    public MeetingResponseDTO toDto(Meeting meeting) {
        MeetingResponseDTO responseDto = new MeetingResponseDTO();
        responseDto.setId(meeting.getId());
        responseDto.setName(meeting.getName());
        responseDto.setDate(meeting.getDate());
        responseDto.setStartMeeting(meeting.getStartMeeting());
        responseDto.setEndMeeting(meeting.getEndMeeting());
        responseDto.setNotes(meeting.getNotes());
        responseDto.setTripId(meeting.getTrip().getId());
        return responseDto;
    }
}