package br.com.kamehouse.financialcontrol.entrypoint.dto;

import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import lombok.Data;

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
    private LocalDate data;
    private IncomingTypeEnum type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
