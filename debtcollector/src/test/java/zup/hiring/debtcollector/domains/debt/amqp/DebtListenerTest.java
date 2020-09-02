package zup.hiring.debtcollector.domains.debt.amqp;

import org.junit.jupiter.api.Test;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.Debtor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DebtListenerTest extends AbstractListenerTest {



    @Test
    public void addDebt() {
        int databaseSizeBeforeCreate = this.repository.findAll().size();

        // Create the Debt
        this.message.setOperation(Operation.POST);
        this.listener.listen(this.message);

        // Validate the Debt in the database
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeCreate + 1);
        Debt testDebt = debts.get(debts.size() - 1);
        assertThat(testDebt.getDueDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDebt.getValue()).isEqualTo(DEFAULT_VALUE);
        Debtor testDebtor = testDebt.getDebtors().iterator().next();
        assertThat(testDebtor).isNotNull();
        assertThat(testDebtor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDebtor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDebtor.getCellNumber()).isEqualTo(DEFAULT_CELL_NUMBER);
    }

    @Test
    public void updateDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        int databaseSizeBeforeUpdate = this.repository.findAll().size();

        // Update the debt
        Debt debt = this.repository.findByUuid(this.debt.getUuid()).get();
        Debt updatedDebt = this.debt2;

        updatedDebt.setUuid(debt.getUuid());
        this.message.setData(updatedDebt);
        this.message.setOperation(Operation.PUT);
        this.listener.listen(this.message);

        // Validate the Debt in the database
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeUpdate);
        Debt testDebt = debts.get(debts.size() - 1);
        assertThat(testDebt.getDueDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDebt.getValue()).isEqualTo(UPDATED_VALUE);
        Debtor testDebtor = testDebt.getDebtors().iterator().next();
        assertThat(testDebtor).isNotNull();
        assertThat(testDebtor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDebtor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDebtor.getCellNumber()).isEqualTo(UPDATED_CELL_NUMBER);
    }

    @Test
    public void deleteDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        int databaseSizeBeforeDelete = this.repository.findAll().size();

        // Delete the debt
        this.message.setOperation(Operation.DELETE);
        this.listener.listen(this.message);

        // Validate the database is empty
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
