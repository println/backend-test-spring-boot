package zup.hiring.debts.domains.debt.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class SimpleDebtDto {
    @NotNull
    private BigDecimal value;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @NotEmpty
    private Set<String> cpfs;

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

    public Set<String> getCpfs() {
        return cpfs;
    }

    public void setCpfs(Set<String> cpfs) {
        this.cpfs = cpfs;
    }
}
