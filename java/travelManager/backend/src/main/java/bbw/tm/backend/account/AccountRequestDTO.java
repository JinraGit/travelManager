package bbw.tm.backend.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRequestDTO extends AccountSignInDTO {
    @NotBlank(message = "Username can't be blank")
    private String username;

    @NotBlank(message = "Role is required")
    private String role;
}

