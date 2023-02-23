package br.com.kamehouse.financialcontrol.core.domain;

import br.com.kamehouse.financialcontrol.entrypoint.dto.ExpenseDto;
import br.com.kamehouse.financialcontrol.entrypoint.enumerate.ExpenseTypeEnum;
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
@Table(name = "tbl_expense")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseDomain extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 4443265822818347999L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private ExpenseTypeEnum type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseDomain toCreate(ExpenseDto expenseDto) {
        this.data = expenseDto.getData();
        this.type = expenseDto.getType();
        this.amount = expenseDto.getAmount();
        this.description = expenseDto.getDescription();
        return this;
    }

    public ExpenseDomain toUpdate(ExpenseDto expenseDto) {
        this.data = expenseDto.getData();
        this.type = expenseDto.getType();
        this.amount = expenseDto.getAmount();
        this.description = expenseDto.getDescription();
        this.uuid = expenseDto.getUuid();
        this.id = expenseDto.getId();
        return this;
    }

    public void toCreate(){
        this.uuid = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void toUpdate(ExpenseDomain record){
        this.updatedAt = LocalDateTime.now();
        this.createdAt = record.getCreatedAt();
    }
}
