package br.com.barbertech.dto;

import br.com.barbertech.enums.GenderEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private Long id;

    @NotBlank(message = "Campo name é obrigatório")
    @Size(min = 2, max = 100, message = "O name deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String name;

    @NotNull(message = "Campo price é obrigatório")
    private BigDecimal price;

    @NotBlank(message = "Campo description é obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "A descrição deve conter apenas letras e espaços")
    private String description;


    @NotNull(message = "Campo idCompany é obrigatório")
    private Long idCompany;

}
