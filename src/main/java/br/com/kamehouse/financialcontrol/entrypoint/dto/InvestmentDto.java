package br.com.kamehouse.financialcontrol.entrypoint.dto;

import br.com.kamehouse.financialcontrol.entrypoint.enumerate.InvestmentTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InvestmentDto implements Serializable {

    private static final long serialVersionUID = -2202500909029988445L;
    private Long id;
    private UUID uuid;
    private String name;
    private InvestmentTypeEnum type;
    private LocalDate data;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InvestmentDto of(){
        return new InvestmentDto();
    }
}
