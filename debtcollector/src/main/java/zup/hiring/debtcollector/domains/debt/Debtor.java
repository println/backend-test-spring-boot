package zup.hiring.debtcollector.domains.debt;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Debtor {
    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Cell Number may not be blank")
    @Pattern(regexp = "([0-9]{11})", message = "Cell Number should have 11 digits")
    private String cellNumber;

    @NotBlank(message = "Email may not be blank")
    @Email(message = "Email is incorrect")
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
    public String toString() {
        return "Debtor{" +
                "name='" + name + '\'' +
                ", cellNumber='" + cellNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Debtor name(String name) {
        this.name = name;
        return this;
    }

    public Debtor cellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
        return this;
    }

    public Debtor email(String email) {
        this.email = email;
        return this;
    }
}
