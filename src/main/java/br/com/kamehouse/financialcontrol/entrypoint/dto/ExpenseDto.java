package br.com.kamehouse.financialcontrol.entrypoint.dto;

import br.com.kamehouse.financialcontrol.entrypoint.enumerate.ExpenseTypeEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExpenseDto implements Serializable {

    private static final long serialVersionUID = 8759720358730633165L;
    private Long id;
    private UUID uuid;
    @NotNull
    private LocalDate data;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ExpenseTypeEnum type;
    @NotNull
    @Min(1)
    private BigDecimal amount;
    @NotNull
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
