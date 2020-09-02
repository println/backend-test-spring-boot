package zup.hiring.debts.domains.debt.web;

import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debt.DebtStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DebtDto {
    private Long id;
    private UUID uuid;
    private BigDecimal value;
    private LocalDate dueDate;
    private DebtStatus status;

     public DebtDto(Debt debt){
         this.id = debt.getId();
         this.uuid = debt.getUuid();
         this.value = debt.getValue();
         this.dueDate = debt.getDueDate();
         this.status = debt.getStatus();
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public DebtStatus getStatus() {
        return status;
    }

    public void setStatus(DebtStatus status) {
        this.status = status;
    }
}
