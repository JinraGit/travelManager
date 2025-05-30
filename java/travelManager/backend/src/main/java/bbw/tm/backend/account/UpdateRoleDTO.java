package bbw.tm.backend.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

@Setter
public class UpdateRoleDTO {

    @NotBlank(message = "Role must not be blank")
    private String role;

    @Schema(description = "Die neue Rolle für den Account", example = "ADMIN")
    @NotBlank(message = "Role must not be blank")
    public String getRole() {
        return role;
    }

}
