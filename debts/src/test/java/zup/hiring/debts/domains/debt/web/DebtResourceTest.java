package zup.hiring.debts.domains.debt.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import zup.hiring.debts.TestUtil;
import zup.hiring.debts.domains.debt.AbstractDebtTest;
import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class DebtResourceTest extends AbstractDebtTest {

    @Test
    public void createDebt() throws Exception {
        int databaseSizeBeforeCreate = this.repository.findAll().size();

        // Create the Debt
        this.restMockMvc
                .perform(post("/api/debts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(this.simpleDebtDto)))
                .andExpect(status().isCreated());

        // Validate the Debt in the database
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeCreate + 1);
        Debt testDebt = debts.get(debts.size() - 1);
        assertThat(testDebt.getDueDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDebt.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDebt.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void getAllDebts() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        // Get all the debtList
        this.restMockMvc.perform(get("/api/debts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*].id").value(hasItem(debt.getId().intValue())))
                .andExpect(jsonPath("$[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
                .andExpect(jsonPath("$[*].dueDate").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
                .andExpect(jsonPath("$[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        // Get the debt
        this.restMockMvc.perform(get("/api/debts/{id}", this.debt.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(debt.getId().intValue()))
                .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
                .andExpect(jsonPath("$.dueDate").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
                .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.debtors.[*].id").value(hasItem(this.debtor.getId().intValue())))
                .andExpect(jsonPath("$.debtors.[*].cpf").value(hasItem(AbstractDebtorTest.DEFAULT_CPF)))
                .andExpect(jsonPath("$.debtors.[*].name").value(hasItem(AbstractDebtorTest.DEFAULT_NAME)))
                .andExpect(jsonPath("$.debtors.[*].email").value(hasItem(AbstractDebtorTest.DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.debtors.[*].cellNumber").value(hasItem(AbstractDebtorTest.DEFAULT_CELL_NUMBER)));
    }

    @Test
    public void getNonExistingDebt() throws Exception {
        // Get the debt
        this.restMockMvc.perform(get("/api/debts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        int databaseSizeBeforeUpdate = this.repository.findAll().size();

        // Update the debt
        Debt updatedDebt = this.repository.findById(this.debt.getId()).get();

        SimpleDebtDto updatedSimpleDebtDto = new SimpleDebtDto();
        updatedSimpleDebtDto.setCpfs(new HashSet<>(Arrays.asList(AbstractDebtorTest.DEFAULT_CPF)));
        updatedSimpleDebtDto.setValue(UPDATED_VALUE);
        updatedSimpleDebtDto.setDueDate(UPDATED_DATE);

        this.restMockMvc
                .perform(put("/api/debts/" + updatedDebt.getId())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(updatedSimpleDebtDto)))
                .andExpect(status().isOk());

        // Validate the Debt in the database
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeUpdate);
        Debt testDebt = debts.get(debts.size() - 1);
        assertThat(testDebt.getDueDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDebt.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDebt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDebt.getUuid()).isEqualTo(this.debt.getUuid());
    }

    @Test
    @Transactional
    public void deleteDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        int databaseSizeBeforeDelete = this.repository.findAll().size();

        // Delete the debt
        this.restMockMvc
                .perform(delete("/api/debts/{id}", this.debt.getId())
                        .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void payOffDebt() throws Exception {
        this.repository.save(this.debt);

        // Get the unpaid debt
        this.restMockMvc.perform(get("/api/debts/{id}", this.debt.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));

        // Pay off Debt
        this.restMockMvc
                .perform(put("/api/debts/{id}/payoff", this.debt.getId())
                        .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Get the paid debt
        this.restMockMvc.perform(get("/api/debts/{id}", this.debt.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value(UPDATED_STATUS.toString()));
    }

}
