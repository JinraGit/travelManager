package bbw.tm.backend.meeting;

import bbw.tm.backend.address.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingMapper {

    private final AddressMapper addressMapper;

    // Mapping von Meeting zu ResponseDTO
    public MeetingResponseDTO toResponseDTO(Meeting meeting) {
        return new MeetingResponseDTO(
                meeting.getId(),
                meeting.getName(),
                meeting.getDate(),
                meeting.getStartMeeting(),
                meeting.getEndMeeting(),
                addressMapper.toDTO(meeting.getLocation()),
                meeting.getNotes()
        );
    }
}