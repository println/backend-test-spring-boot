package zup.hiring.debts.domains.debtor.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import zup.hiring.debts.TestUtil;
import zup.hiring.debts.domains.debt.AbstractDebtTest;
import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;
import zup.hiring.debts.domains.debtor.Debtor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class DebtorResourceTest extends AbstractDebtorTest {

	@Test
	public void createDebtor() throws Exception {
		int databaseSizeBeforeCreate = this.repository.findAll().size();

		// Create the Debtor
		this.restMockMvc
		.perform(post("/api/debtors").contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(this.debtor)))
		.andExpect(status().isCreated());

		// Validate the Debtor in the database
		List<Debtor> debtors = this.repository.findAll();
		assertThat(debtors).hasSize(databaseSizeBeforeCreate + 1);
		Debtor testDebtor = debtors.get(debtors.size() - 1);
		assertThat(testDebtor.getCpf()).isEqualTo(DEFAULT_CPF);
		assertThat(testDebtor.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testDebtor.getEmail()).isEqualTo(DEFAULT_EMAIL);
		assertThat(testDebtor.getCellNumber()).isEqualTo(DEFAULT_CELL_NUMBER);
	}

	@Test
	public void getAllDebtors() throws Exception {
		// Initialize the database
		this.repository.save(this.debtor);

		// Get all the debtorList
		this.restMockMvc.perform(get("/api/debtors?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.[*].id").value(hasItem(this.debtor.getId().intValue())))
		.andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
		.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
		.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
		.andExpect(jsonPath("$.[*].cellNumber").value(hasItem(DEFAULT_CELL_NUMBER)));
	}

	@Test
	public void getDebtor() throws Exception {
		// Initialize the database
		this.repository.save(this.debtor);

		// Get the debtor
		this.restMockMvc.perform(get("/api/debtors/{id}", this.debtor.getId()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id").value(this.debtor.getId()))
		.andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
		.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
		.andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
		.andExpect(jsonPath("$.cellNumber").value(DEFAULT_CELL_NUMBER));
	}

	@Test
	public void getNonExistingDebtor() throws Exception {
		// Get the debtor
		this.restMockMvc.perform(get("/api/debtors/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void updateDebtor() throws Exception {
		// Initialize the database
		this.repository.save(this.debtor);

		int databaseSizeBeforeUpdate = this.repository.findAll().size();

		// Update the debtor
		Debtor updatedDebtor = this.repository.findById(this.debtor.getId()).get();
		updatedDebtor
				.cpf(UPDATED_CPF)
				.name(UPDATED_NAME)
				.email(UPDATED_EMAIL)
				.cellNumber(UPDATED_CELL_NUMBER);

		this.restMockMvc
		.perform(put("/api/debtors/" + updatedDebtor.getId()).contentType(TestUtil.APPLICATION_JSON_UTF8)
		         .content(TestUtil.convertObjectToJsonBytes(updatedDebtor)))
		.andExpect(status().isOk());

		// Validate the Debtor in the database
		List<Debtor> debtors = this.repository.findAll();
		assertThat(debtors).hasSize(databaseSizeBeforeUpdate);
		Debtor testDebtor = debtors.get(debtors.size() - 1);
		assertThat(testDebtor.getCpf()).isEqualTo(UPDATED_CPF);
		assertThat(testDebtor.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testDebtor.getEmail()).isEqualTo(UPDATED_EMAIL);
		assertThat(testDebtor.getCellNumber()).isEqualTo(UPDATED_CELL_NUMBER);
	}

	@Test
	public void deleteDebtor() throws Exception {
		// Initialize the database
		this.repository.save(this.debtor);

		int databaseSizeBeforeDelete = this.repository.findAll().size();

		// Delete the debtor
		this.restMockMvc
		.perform(delete("/api/debtors/{id}", this.debtor.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());

		// Validate the database is empty
		List<Debtor> debtors = this.repository.findAll();
		assertThat(debtors).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	public void getDebtorDebts() throws Exception {
		Long param = 1L;
		this.restMockMvc.perform(get("/api/debtors/{id}/debts", param))
				.andExpect(forwardedUrl("/api/debts?debtor=" + param));
	}
}
