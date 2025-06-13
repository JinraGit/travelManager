package bbw.tm.backend.meeting;

import bbw.tm.backend.address.AddressCreateDTO;
import java.time.LocalDate;
import java.time.LocalTime;

public record MeetingRequestDTO(
    String name,
    LocalDate date,
    String startMeeting,
    String endMeeting,
    AddressCreateDTO location, // Location als AddressCreateDTO
    String notes
) {}