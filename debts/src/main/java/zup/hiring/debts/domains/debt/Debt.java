package zup.hiring.debts.domains.debt;

import zup.hiring.debts.domains.debtor.Debtor;
import zup.hiring.debts.support.utils.model.AuditableModel;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "DEBT")
public class Debt extends AuditableModel<Debt> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debt_id_seq")
    @SequenceGenerator(name = "debt_id_seq", sequenceName = "DEBT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;

    @DecimalMin(value = "100.00", message = "The value should be less than or equals 100.00")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @NotNull
    @Future(message = "Due Date may not be in past")
    @Column(name = "due_date", nullable = false, columnDefinition = "DATE")
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DebtStatus status;

    @ManyToMany(cascade = {CascadeType.DETACH})
    @JoinTable(name = "DEBTOR_DEBT",
            joinColumns = {@JoinColumn(name = "DEBT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DEBTOR_ID")})
    private Set<Debtor> debtors;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Debt uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Debt value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Debt dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public DebtStatus getStatus() {
        return status;
    }

    public Debt status(DebtStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DebtStatus status) {
        this.status = status;
    }

    public Set<Debtor> getDebtors() {
        return debtors;
    }

    public Debt debtors(Set<Debtor> debtors) {
        this.debtors = debtors;
        return this;
    }

    public void setDebtors(Set<Debtor> debtors) {
        this.debtors = debtors;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Debt debt = (Debt) o;
        if (debt.getId() == null || this.getId() == null) {
            return false;
        }
        if (debt.getUuid() == null || this.getUuid() == null) {
            return false;
        }

        return Objects.equals(this.getId(), debt.getId()) &&
                Objects.equals(this.getUuid(), debt.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId()) + Objects.hashCode(this.getUuid());
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", value=" + value +
                ", dueDate=" + dueDate +
                ", status=" + status +
                '}';
    }
}
