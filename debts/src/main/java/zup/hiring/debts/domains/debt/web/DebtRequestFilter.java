package zup.hiring.debts.domains.debt.web;

import zup.hiring.debts.domains.debt.DebtStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DebtRequestFilter {
    private UUID uuid;
    private BigDecimal value;
    private BigDecimal maxValue;
    private LocalDate dueDate;
    private LocalDate endDueDate;
    private DebtStatus status;
    private Long debtorId;

    public DebtRequestFilter(UUID uuid, BigDecimal value, BigDecimal maxValue, LocalDate dueDate, LocalDate endDueDate, DebtStatus status, Long debtorId) {
        this.uuid = uuid;
        this.value = value;
        this.maxValue = maxValue;
        this.dueDate = dueDate;
        this.endDueDate = endDueDate;
        this.status = status;
        this.debtorId = debtorId;
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

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getEndDueDate() {
        return endDueDate;
    }

    public void setEndDueDate(LocalDate endDueDate) {
        this.endDueDate = endDueDate;
    }

    public DebtStatus getStatus() {
        return status;
    }

    public void setStatus(DebtStatus status) {
        this.status = status;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public boolean hasUuid() {
        return Objects.nonNull(this.uuid);
    }

    public boolean hasValue() {
        return Objects.nonNull(this.value);
    }

    public boolean hasEndValue() {
        return Objects.nonNull(this.maxValue);
    }

    public boolean hasDueDate() {
        return Objects.nonNull(this.dueDate);
    }

    public boolean hasEndDueDate() {
        return Objects.nonNull(this.endDueDate);
    }

    public boolean hasStatus() {
        return Objects.nonNull(this.status);
    }

    public boolean hasDebtorId() {
        return Objects.nonNull(this.debtorId);
    }

}
