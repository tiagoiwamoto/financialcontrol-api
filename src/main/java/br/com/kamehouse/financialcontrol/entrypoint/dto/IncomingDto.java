package br.com.kamehouse.financialcontrol.core.domain;

import br.com.kamehouse.financialcontrol.entrypoint.enumerate.IncomingTypeEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
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
@ToString
public class IncomingDomain extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 7277582165322711987L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private LocalDate data;
    private IncomingTypeEnum type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void toCreate(){
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void toUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
