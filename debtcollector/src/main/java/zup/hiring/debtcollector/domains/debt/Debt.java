package zup.hiring.debtcollector.domains.debt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Document(collection = "debt")
public class Debt {

    @Id
    @JsonIgnore
    private String id;

    @NotNull
    @Field("uuid")
    @Indexed(unique = true)
    private UUID uuid;

    @NotNull
    @Field("value")
    private BigDecimal value;

    @NotNull
    @Field("dueDate")
    private LocalDate dueDate;

    @NotNull
    @NotEmpty
    @Valid
    @Field("debtors")
    private Set<Debtor> debtors;

    public Debt() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    public Set<Debtor> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<Debtor> debtors) {
        this.debtors = debtors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (uuid == null) return false;
        Debt debt = (Debt) o;
        return Objects.equal(uuid, debt.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public String toString() {
        return "Debt{" +
                "uuid=" + uuid +
                ", value=" + value +
                ", dueDate=" + dueDate +
                ", debtors=" + debtors +
                '}';
    }

    public Debt uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Debt value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public Debt dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Debt debtors(Set<Debtor> operation) {
        this.debtors = operation;
        return this;
    }
}
