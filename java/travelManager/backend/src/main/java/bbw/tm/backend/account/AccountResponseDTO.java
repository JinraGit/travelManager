package bbw.tm.backend.account;

import lombok.Data;

import java.util.Set;
@Data
public class AccountResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private Set<String> roles;    private boolean enabled;
    private Integer personId;
    private Integer vacationDays = 30;

}
