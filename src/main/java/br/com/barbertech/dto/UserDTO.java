package br.com.barbertech.dto;

import br.com.barbertech.enums.GenderEnum;
import br.com.barbertech.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotBlank(message = "Campo username é obrigatório")
    private String username;

    @NotBlank(message = "Campo password é obrigatório")
    private String password;

    @NotNull(message = "Campo role é obrigatório")
    private UserRole role;

}
