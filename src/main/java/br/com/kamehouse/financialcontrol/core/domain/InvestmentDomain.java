package br.com.kamehouse.financialcontrol.core.domain;

import br.com.kamehouse.financialcontrol.entrypoint.dto.InvestmentDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.InvestmentTypeEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tbl_investment")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class InvestmentDomain extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = -6730474773977234702L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String name;
    @Enumerated(EnumType.STRING)
    private InvestmentTypeEnum type;
    private LocalDate data;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InvestmentDomain of(){
        return new InvestmentDomain();
    }

    public InvestmentDomain toDomain(InvestmentDto dto){
        var domain = this.of();
        domain.uuid = dto.getUuid();
        domain.id = dto.getId();
        domain.name = dto.getName();
        domain.type = dto.getType();
        domain.data = dto.getData();
        domain.amount = dto.getAmount();
        domain.description = dto.getDescription();
        domain.createdAt = dto.getCreatedAt();
        domain.updatedAt = dto.getUpdatedAt();
        return domain;
    }

    public InvestmentDomain toUpdate(InvestmentDomain newDomain){
        var domain = this.of();
        domain.uuid = newDomain.getUuid();
        domain.id = newDomain.getId();
        domain.name = newDomain.getName();
        domain.type = newDomain.getType();
        domain.data = newDomain.getData();
        domain.amount = newDomain.getAmount();
        domain.description = newDomain.getDescription();
        domain.createdAt = newDomain.getCreatedAt();
        domain.updatedAt = newDomain.getUpdatedAt();
        return domain;
    }

    public InvestmentDto toDto(InvestmentDomain domain){
        var dto = InvestmentDto.of();
        dto.setId(domain.getId());
        dto.setUuid(domain.getUuid());
        dto.setName(domain.getName());
        dto.setType(domain.getType());
        dto.setData(domain.getData());
        dto.setAmount(domain.getAmount());
        dto.setDescription(domain.getDescription());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());
        return dto;
    }
}
