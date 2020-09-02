package zup.hiring.debtcollector.domains.debt.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class DebtRequestFilter {
    private BigDecimal value;
    private BigDecimal maxValue;
    private LocalDate dueDate;
    private LocalDate endDueDate;
    private String debtorName;

    public DebtRequestFilter(BigDecimal value, BigDecimal maxValue, LocalDate dueDate, LocalDate endDueDate, String debtorName) {
        this.value = value;
        this.maxValue = maxValue;
        this.dueDate = dueDate;
        this.endDueDate = endDueDate;
        this.debtorName = debtorName;
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

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
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

    public boolean hasDebtorName() {
        return Objects.nonNull(this.debtorName);
    }

}
