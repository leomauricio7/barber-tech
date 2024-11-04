package br.com.barbertech.dto;

import br.com.barbertech.enums.GenderEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {

    private Long id;

    @NotBlank(message = "Campo name é obrigatório")
    @Size(min = 2, max = 100, message = "O name deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String name;

    @NotBlank(message = "Campo price é obrigatório")
    @Pattern(
            regexp = "^\\d{1,3}(\\.\\d{3})*(,\\d{2})?$",
            message = "O preço deve estar no formato 1.234,56"
    )
    private String price;

    @NotBlank(message = "Campo description é obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "A descrição deve conter apenas letras e espaços")
    private String description;


}
