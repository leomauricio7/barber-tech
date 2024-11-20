package br.com.barbertech.dto;

import br.com.barbertech.enums.StatusSchedulingEnum;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SchedulingDTO {

    private Long id;

    @NotNull(message = "Campo idClient é obrigatório")
    private Long idClient;

    @NotNull(message = "Campo idCompany é obrigatório")
    private Long idCompany;

    @NotNull(message = "Campo idBarber é obrigatório")
    private Long idBarber;

    @NotNull(message = "Campo idService é obrigatório")
    private Long idService;

    @NotNull(message = "Campo date é obrigatório")
    private Date date;

    private StatusSchedulingEnum status;

}
