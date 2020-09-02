package zup.hiring.debtcollector.domains.debt.amqp;

import org.junit.jupiter.api.Test;
import zup.hiring.debtcollector.TestUtil;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.Debtor;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DebtListenerDataValidationsTest extends AbstractListenerTest {

    @Test
    public void checkUuidIsRequired() throws Exception {
        this.debt.setUuid(null);
        this.checkRequired();
    }

    @Test
    public void checkDueDateIsRequired() throws Exception {
        this.debt.setDueDate(null);
        this.checkRequired();
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        this.debt.setValue(null);
        this.checkRequired();
    }

    @Test
    public void checkDebtorsIsRequired() throws Exception {
        this.debt.setDebtors(null);
        this.checkRequired();
    }

    @Test
    public void checkDebtorsIsEmpty() throws Exception {
        this.debt.setDebtors(new HashSet<>());
        this.checkRequired();
    }

    @Test
    public void checkDebtorsNameIsRequired() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setName(null);
        this.checkRequired();
    }

    @Test
    public void checkDebtorsNameIsEmpty() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setName("");
        this.checkRequired();
    }

    @Test
    public void checkDebtorsEmailIsRequired() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setEmail(null);
        this.checkRequired();
    }

    @Test
    public void checkDebtorsEmailIsEmpty() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setEmail("");
        this.checkRequired();
    }

    @Test
    public void checkDebtorsCellNumberIsRequired() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setCellNumber(null);
        this.checkRequired();
    }

    @Test
    public void checkDebtorsCellNumberIsEmpty() throws Exception {
        Debtor debtor = this.debt.getDebtors().iterator().next();
        debtor.setCellNumber("");
        this.checkRequired();
    }

    private void checkRequired() throws Exception {
        int databaseSizeBeforeTest = this.repository.findAll().size();

        this.message.setOperation(Operation.POST);
        assertThatThrownBy(() -> { this.listener.listen(this.message); })
                .isInstanceOf(ValidationException.class);

        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debt.class);
        Debt debt1 = new Debt();
        debt1.setUuid(DEFAULT_UUID);
        Debt debt2 = new Debt();
        debt2.setUuid(debt1.getUuid());
        assertThat(debt1).isEqualTo(debt2);
        debt2.setUuid(UPDATED_UUID);
        assertThat(debt1).isNotEqualTo(debt2);
        debt1.setUuid(null);
        assertThat(debt1).isNotEqualTo(debt2);
    }
}
