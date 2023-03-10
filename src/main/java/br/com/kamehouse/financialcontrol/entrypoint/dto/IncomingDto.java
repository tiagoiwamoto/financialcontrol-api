package br.com.kamehouse.financialcontrol.entrypoint.dto;

import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IncomingDto implements Serializable {

    private static final long serialVersionUID = 455440661048306952L;
    private Long id;
    private UUID uuid;
    @NotBlank
    private LocalDate data;
    @NotBlank
    private IncomingTypeEnum type;
    @NotBlank
    @Min(1)
    private BigDecimal amount;
    @NotBlank
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
