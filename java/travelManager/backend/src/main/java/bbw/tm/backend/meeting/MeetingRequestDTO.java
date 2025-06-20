package bbw.tm.backend.meeting;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingRequestDTO {
    private String name;
    private LocalDate date;
    private String startMeeting;
    private String endMeeting;
    private String notes;
    private Integer tripId;
}
