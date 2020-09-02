package zup.hiring.debts.domains.debt.collector.amqp;

import com.google.common.base.Objects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class CollectorDebtDto {
    private UUID uuid;
    private BigDecimal value;
    private LocalDate dueDate;
    private Set<CollectorDebtorDto> debtors;

    public CollectorDebtDto() {
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

    public Set<CollectorDebtorDto> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<CollectorDebtorDto> debtors) {
        this.debtors = debtors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectorDebtDto dto = (CollectorDebtDto) o;
        return Objects.equal(uuid, dto.uuid) &&
                Objects.equal(value, dto.value) &&
                Objects.equal(dueDate, dto.dueDate) &&
                Objects.equal(debtors, dto.debtors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, value, dueDate, debtors);
    }

    @Override
    public String toString() {
        return "CollectorDebtDto{" +
                "uuid=" + uuid +
                ", value=" + value +
                ", dueDate=" + dueDate +
                ", debtors=" + debtors +
                '}';
    }

    public CollectorDebtDto uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public CollectorDebtDto value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public CollectorDebtDto dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public CollectorDebtDto debtors(Set<CollectorDebtorDto> operation) {
        this.debtors = operation;
        return this;
    }
}
