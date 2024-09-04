package br.com.barbertech.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarberDTO {

    private Long id;

    @NotBlank(message = "Campo name é obrigatório")
    @Size(min = 2, max = 100, message = "O name deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String name;

    @NotBlank(message = "Campo phone é obrigatório")
    @Pattern(
            regexp = "^\\d{2} \\d{4,5}-\\d{4}$",
            message = "O phone deve estar no formato 84 99234-5678 ou 84 3234-5678"
    )
    private String phone;

    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Email invalido")
    private String email;

    @Null()
    private AddressDTO address;


}
