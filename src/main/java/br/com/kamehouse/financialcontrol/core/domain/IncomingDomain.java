package br.com.kamehouse.financialcontrol.core.domain;

import br.com.kamehouse.financialcontrol.entrypoint.dto.IncomingDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
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
import java.util.UUID;

@Entity
@Table(name = "tbl_incoming")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class IncomingDomain extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 7277582165322711987L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private IncomingTypeEnum type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IncomingDomain toCreate(IncomingDto incomingDto) {
        this.data = incomingDto.getData();
        this.type = incomingDto.getType();
        this.amount = incomingDto.getAmount();
        this.description = incomingDto.getDescription();
        return this;
    }

    public IncomingDomain toUpdate(IncomingDto incomingDto) {
        this.data = incomingDto.getData();
        this.type = incomingDto.getType();
        this.amount = incomingDto.getAmount();
        this.description = incomingDto.getDescription();
        this.uuid = incomingDto.getUuid();
        this.id = incomingDto.getId();
        return this;
    }

    public void toCreate(){
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void toUpdate(IncomingDomain record){
        this.updatedAt = LocalDateTime.now();
        this.createdAt = record.getCreatedAt();
    }
}
