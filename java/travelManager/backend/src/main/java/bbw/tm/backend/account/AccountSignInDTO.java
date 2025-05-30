package bbw.tm.backend.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountSignInDTO {
    @NotBlank(message = "Email can't be blank")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, max = 255)
    private String password;

}
