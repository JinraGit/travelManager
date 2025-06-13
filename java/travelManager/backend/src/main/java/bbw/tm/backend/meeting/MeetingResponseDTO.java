package bbw.tm.backend.meeting;

import bbw.tm.backend.address.AddressDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record MeetingResponseDTO(
        Integer id,
        String name,
        LocalDate date,
        String startMeeting,
        String endMeeting,
        AddressDTO location, // Adresse als vollständiges DTO
        String notes
) {}

