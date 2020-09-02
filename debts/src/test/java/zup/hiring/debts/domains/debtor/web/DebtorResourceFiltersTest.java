package zup.hiring.debts.domains.debtor.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;
import zup.hiring.debts.domains.debtor.Debtor;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DebtorResourceFiltersTest extends AbstractDebtorTest {

    private Debtor debtor2;

    @BeforeEach
    public void initTest() {
        super.initTest();

        this.debtor2 = createEntity()
                .cpf(AbstractDebtorTest.UPDATED_CPF)
                .name(AbstractDebtorTest.UPDATED_NAME)
                .email(AbstractDebtorTest.UPDATED_EMAIL)
                .cellNumber(AbstractDebtorTest.UPDATED_CELL_NUMBER);
        this.debtor2 = this.debtorService.create(this.debtor2);

        this.repository.save(this.debtor2);
    }

    @ParameterizedTest
    @MethodSource("dataSingleProvider")
    public void checkParams(String param) throws Exception {
        // Get filtered debtList
        this.restMockMvc.perform(get("/api/debtors?" + param))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].cpf").value(UPDATED_CPF))
                .andExpect(jsonPath("$.[0].name").value(UPDATED_NAME))
                .andExpect(jsonPath("$.[0].email").value(UPDATED_EMAIL))
                .andExpect(jsonPath("$.[0].cellNumber").value(UPDATED_CELL_NUMBER));
    }

    static Stream<Arguments> dataSingleProvider() {
        return Stream.of(
                Arguments.of("cpf=" + UPDATED_CPF),
                Arguments.of("name=" + UPDATED_NAME),
                Arguments.of("email=" + UPDATED_EMAIL),
                Arguments.of("cellNumber=" + UPDATED_CELL_NUMBER));
    }
}
