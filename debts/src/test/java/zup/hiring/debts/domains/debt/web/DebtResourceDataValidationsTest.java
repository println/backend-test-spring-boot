package zup.hiring.debts.domains.debt.web;

import org.junit.jupiter.api.Test;
import zup.hiring.debts.TestUtil;
import zup.hiring.debts.domains.debt.AbstractDebtTest;
import zup.hiring.debts.domains.debt.Debt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DebtResourceDataValidationsTest extends AbstractDebtTest {

    @Test
    public void checkCpfsIsRequired() throws Exception {
        this.simpleDebtDto.setCpfs(null);
        this.checkRequired();
    }

    @Test
    public void checkCpfIsNotEmpty() throws Exception {
        this.simpleDebtDto.setCpfs(new HashSet<>());
        this.checkRequired();
    }

    @Test
    public void checkDueDateIsRequired() throws Exception {
        this.simpleDebtDto.setDueDate(null);
        this.checkRequired();
    }

    @Test
    public void checkDueDateLessThanOneYear() throws Exception {
        this.simpleDebtDto.setDueDate(LocalDate.now().plusYears(1).plusDays(1));
        this.checkRequired();
    }

    @Test
    public void checkDueDateGreaterThanNow() throws Exception {
        this.simpleDebtDto.setDueDate(LocalDate.now().minusDays(1));
        this.checkRequired();
    }

    @Test
    public void checkValueIsRequired() throws Exception {
        this.simpleDebtDto.setValue(null);
        this.checkRequired();
    }

    @Test
    public void checkValueLessThan100() throws Exception {
        this.simpleDebtDto.setValue(new BigDecimal("99.99"));
        this.checkRequired();
    }

    @Test
    public void checkValueDigitsGreaterThan2() throws Exception {
        this.simpleDebtDto.setValue(new BigDecimal("100.028"));
        this.checkRequired();
    }

    private void checkRequired() throws Exception {
        int databaseSizeBeforeTest = this.repository.findAll().size();

        this.restMockMvc
                .perform(post("/api/debts").contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(this.simpleDebtDto)))
                .andExpect(status().isBadRequest());

        List<Debt> debts = this.repository.findAll();
        assertThat(debts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Debt.class);
        Debt debt1 = new Debt();
        debt1.setId(9000L);
        debt1.setUuid(DEFAULT_UUID);
        Debt debt2 = new Debt();
        debt2.setId(debt1.getId());
        debt2.setUuid(debt1.getUuid());
        assertThat(debt1).isEqualTo(debt2);
        debt2.setId(8000L);
        debt2.setUuid(UPDATED_UUID);
        assertThat(debt1).isNotEqualTo(debt2);
        debt1.setId(null);
        debt1.setUuid(null);
        assertThat(debt1).isNotEqualTo(debt2);
    }
}
