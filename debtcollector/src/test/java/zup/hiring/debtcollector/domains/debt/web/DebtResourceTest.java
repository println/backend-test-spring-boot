package zup.hiring.debtcollector.domains.debt.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import zup.hiring.debtcollector.domains.debt.AbstractDebtTest;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class DebtResourceTest extends AbstractDebtTest {
    @Test
    public void getAllDebts() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        // Get all the debtList
        this.restMockMvc.perform(get("/api/debts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
                .andExpect(jsonPath("$[*].dueDate").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
                .andExpect(jsonPath("$[*].debtors.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$[*].debtors.[*].email").value(hasItem(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$[*].debtors.[*].cellNumber").value(hasItem(DEFAULT_CELL_NUMBER)));;
    }

    @Test
    public void getDebt() throws Exception {
        // Initialize the database
        this.repository.save(this.debt);

        // Get the debt
        this.restMockMvc.perform(get("/api/debts/{id}", this.debt.getUuid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
                .andExpect(jsonPath("$.dueDate").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
                .andExpect(jsonPath("$.debtors.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.debtors.[*].email").value(hasItem(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.debtors.[*].cellNumber").value(hasItem(DEFAULT_CELL_NUMBER)));
    }

    @Test
    public void getNonExistingDebt() throws Exception {
        // Get the debt
        this.restMockMvc.perform(get("/api/debts/{id}", UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

}
