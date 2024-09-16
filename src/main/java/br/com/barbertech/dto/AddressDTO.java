package br.com.barbertech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private Long id;

    @NotBlank(message = "Campo city é obrigatório")
    @Size(min = 2, max = 100, message = "O city deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String city;


    @NotBlank(message = "Campo street é obrigatório")
    @Size(min = 10, max = 100, message = "O street deve ter entre 10 e 100 caracteres")
    private String street;

    @NotBlank(message = "Campo state é obrigatório")
    @Size(min = 2, max = 2, message = "O state deve ter 2 caracteres")
    private String state;

    @NotBlank(message = "Campo zipCode é obrigatório")
    @Pattern(
            regexp = "^(\\d{5}-\\d{3}|\\d{8})$",
            message = "O código postal deve estar no formato 99999-999 ou 99999999"
    )
    private String zipCode;
}
