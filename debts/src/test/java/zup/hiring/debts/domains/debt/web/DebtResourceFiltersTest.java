package zup.hiring.debts.domains.debt.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import zup.hiring.debts.domains.debt.AbstractDebtTest;
import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;
import zup.hiring.debts.domains.debtor.Debtor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DebtResourceFiltersTest extends AbstractDebtTest {

    private Debt debt2;
    private Debtor debtor2;

    @BeforeEach
    public void initTest() {
        super.initTest();

        this.debtor2 = createMandatoryEntity()
                .cpf(AbstractDebtorTest.UPDATED_CPF)
                .name(AbstractDebtorTest.UPDATED_NAME)
                .email(AbstractDebtorTest.UPDATED_EMAIL)
                .cellNumber(AbstractDebtorTest.UPDATED_CELL_NUMBER);
        this.debtor2 = this.debtorService.create(this.debtor2);

        this.debt2 = createEntity(new HashSet<>(Arrays.asList(this.debtor2)))
                .uuid(UPDATED_UUID)
                .dueDate(UPDATED_DATE)
                .value(UPDATED_VALUE)
                .status(UPDATED_STATUS);

        this.repository.save(this.debt);
        this.repository.save(this.debt2);
    }

    @Test
    public void checkDebtorIdParam() throws Exception {
        this.checkParams("debtor=" + debtor2.getId());
    }

    @ParameterizedTest
    @MethodSource("dataSingleProvider")
    public void checkParams(String param) throws Exception {
        // Get filtered debtList
        this.restMockMvc.perform(get("/api/debts?" + param))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(debt2.getId().intValue()))
                .andExpect(jsonPath("$[0].uuid").value(UPDATED_UUID.toString()))
                .andExpect(jsonPath("$[0].dueDate").value(UPDATED_DATE.toString()))
                .andExpect(jsonPath("$[0].value").value(UPDATED_VALUE.doubleValue()))
                .andExpect(jsonPath("$[0].status").value(UPDATED_STATUS.toString()));
    }

    static Stream<Arguments> dataSingleProvider() {
        return Stream.of(
                Arguments.of("uuid=" + UPDATED_UUID.toString()),
                Arguments.of("value=" + UPDATED_VALUE.intValue()),
                Arguments.of("value=" + (DEFAULT_VALUE.intValue() + 1) + "&maxValue=" + UPDATED_VALUE.intValue()),
                Arguments.of("dueDate=" + UPDATED_DATE.toString()),
                Arguments.of("dueDate=" + UPDATED_DATE.toString() + "&endDueDate=" + UPDATED_DATE.toString()),
                Arguments.of("status=" + UPDATED_STATUS.toString()));
    }

    @ParameterizedTest
    @MethodSource("dataMultipleProvider")
    public void checkMultipleParams(String param1, String param2) throws Exception {
        // Get all the debtList
        this.restMockMvc.perform(get("/api/debts?" + param1 + "&" + param2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(debt.getId().intValue()))
                .andExpect(jsonPath("$[0].uuid").value(DEFAULT_UUID.toString()))
                .andExpect(jsonPath("$[0].dueDate").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$[0].value").value(DEFAULT_VALUE.doubleValue()))
                .andExpect(jsonPath("$[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$[1].id").value(debt2.getId().intValue()))
                .andExpect(jsonPath("$[1].uuid").value(UPDATED_UUID.toString()))
                .andExpect(jsonPath("$[1].dueDate").value(UPDATED_DATE.toString()))
                .andExpect(jsonPath("$[1].value").value(UPDATED_VALUE.doubleValue()))
                .andExpect(jsonPath("$[1].status").value(UPDATED_STATUS.toString()));
    }

    static Stream<Arguments> dataMultipleProvider() {
        return Stream.of(
                Arguments.of("value=" + DEFAULT_VALUE.intValue(), "maxValue=" + UPDATED_VALUE.intValue()),
                Arguments.of("dueDate=" + DEFAULT_DATE.toString(), "endDueDate=" + UPDATED_DATE.toString()));
    }
}
