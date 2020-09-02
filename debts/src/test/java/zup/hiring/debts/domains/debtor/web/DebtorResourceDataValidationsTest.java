package zup.hiring.debts.domains.debtor.web;

import org.junit.jupiter.api.Test;
import zup.hiring.debts.TestUtil;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;
import zup.hiring.debts.domains.debtor.Debtor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DebtorResourceDataValidationsTest extends AbstractDebtorTest {
	@Test
	public void checkCpfIsRequired() throws Exception {
		this.debtor.setCpf(null);
		this.checkRequired();
	}

	@Test
	public void checkCpfIsDuplicated() throws Exception {
		this.repository.save(this.debtor);
		this.debtor.setEmail(UPDATED_EMAIL);
		this.checkRequired();
	}

	@Test
	public void checkCpfIsValid() throws Exception {
		this.debtor.setCpf("80044007000");
		this.checkRequired();
	}

	@Test
	public void checkNameIsRequired() throws Exception {
		this.debtor.setName(null);
		this.checkRequired();
	}

	@Test
	public void checkNameLessThan3() throws Exception {
		this.debtor.setName("ex");
		this.checkRequired();
	}

	@Test
	public void checkNameGreaterThan50() throws Exception {
		this.debtor.setName("b80wP37cpLtP79eIcoHYaF5CUT85zCbCiNmnAx5tWeWBHG1mj1i");
		this.checkRequired();
	}

	@Test
	public void checkEmailIsRequired() throws Exception {
		this.debtor.setEmail(null);
		this.checkRequired();
	}

	@Test
	public void checkEmailIsDuplicated() throws Exception {
		this.repository.save(this.debtor);
		this.debtor.cpf(UPDATED_CPF);
		this.checkRequired();
	}

	@Test
	public void checkCellNumberIsRequired() throws Exception {
		this.debtor.setCellNumber(null);
		this.checkRequired();
	}

	@Test
	public void checkCellNumberLessThan11() throws Exception {
		this.debtor.setCellNumber("2112341234");
		this.checkRequired();
	}

	@Test
	public void checkCellNumberGreaterThan11() throws Exception {
		this.debtor.setCellNumber("211234512345");
		this.checkRequired();
	}

	private void checkRequired() throws Exception {
		int databaseSizeBeforeTest = this.repository.findAll().size();

		this.restMockMvc
		.perform(post("/api/debtors").contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(this.debtor)))
		.andExpect(status().isBadRequest());

		List<Debtor> debtors = this.repository.findAll();
		assertThat(debtors).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Debtor.class);
		Debtor debtor1 = new Debtor();
		debtor1.setId(9000L);
		debtor1.setCpf("123456789");
		Debtor debtor2 = new Debtor();
		debtor2.setId(debtor1.getId());
		debtor2.setCpf(debtor1.getCpf());
		assertThat(debtor1).isEqualTo(debtor2);
		debtor2.setId(8000L);
		debtor2.setCpf("987654321");
		assertThat(debtor1).isNotEqualTo(debtor2);
		debtor1.setId(null);
		debtor1.setCpf(null);
		assertThat(debtor1).isNotEqualTo(debtor2);
	}
}
