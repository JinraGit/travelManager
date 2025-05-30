package bbw.tm.backend.account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponseDTO {
    private AccountResponseDTO account;
    private String token;

}