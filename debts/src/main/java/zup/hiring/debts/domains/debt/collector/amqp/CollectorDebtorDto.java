package zup.hiring.debts.domains.debt.collector.amqp;

import com.google.common.base.Objects;

public class CollectorDebtorDto {
    private String name;
    private String cellNumber;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectorDebtorDto that = (CollectorDebtorDto) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(cellNumber, that.cellNumber) &&
                Objects.equal(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, cellNumber, email);
    }

    @Override
    public String toString() {
        return "CollectorDebtorDto{" +
                "name='" + name + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public CollectorDebtorDto name(String name) {
        this.name = name;
        return this;
    }

    public CollectorDebtorDto cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public CollectorDebtorDto email(String email) {
        this.email = email;
        return this;
    }
}
