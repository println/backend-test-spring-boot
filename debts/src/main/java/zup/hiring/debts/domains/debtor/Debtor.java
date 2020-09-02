package zup.hiring.debts.domains.debtor;

import org.hibernate.validator.constraints.br.CPF;
import zup.hiring.debts.support.utils.model.AuditableModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "DEBTOR")
public class Debtor extends AuditableModel<Debtor> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debtor_id_seq")
    @SequenceGenerator(name = "debtor_id_seq", sequenceName = "DEBTOR_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "CPF may not be blank")
    @CPF(message = "CPF is incorrect")
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 50, message = "The name should be between 3 and 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Cell Number may not be blank")
    @Pattern(regexp = "([0-9]{11})", message = "Cell Number should have 11 digits")
    @Column(name = "cell_number", nullable = false)
    private String cellNumber;

    @NotBlank(message = "Email may not be blank")
    @Email(message = "Email is incorrect")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Debtor cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return this.name;
    }

    public Debtor name(String firstName) {
        this.name = firstName;
        return this;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getEmail() {
        return this.email;
    }

    public Debtor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String role) {
        this.email = role;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public Debtor cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Debtor debtor = (Debtor) o;
        if (debtor.getId() == null || this.getId() == null) {
            return false;
        }
        if (debtor.getCpf() == null || this.getCpf() == null) {
            return false;
        }

        return Objects.equals(this.getId(), debtor.getId()) &&
                Objects.equals(this.getCpf(), debtor.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId()) + Objects.hashCode(this.getCpf());
    }

    @Override
    public String toString() {
        return "Debtor{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
