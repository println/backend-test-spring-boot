package zup.hiring.debtcollector.domains.debt.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import zup.hiring.debtcollector.domains.debt.AbstractDebtTest;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DebtResourceFiltersTest extends AbstractDebtTest {

    @BeforeEach
    public void initTest() {
        super.initTest();
        this.repository.save(this.debt);
        this.repository.save(this.debt2);
    }

    @ParameterizedTest
    @MethodSource("dataSingleProvider")
    public void checkParams(String param) throws Exception {
        // Get filtered debtList
        this.restMockMvc.perform(get("/api/debts?" + param))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].uuid").value(UPDATED_UUID.toString()))
                .andExpect(jsonPath("$[0].dueDate").value(UPDATED_DATE.toString()))
                .andExpect(jsonPath("$[0].value").value(UPDATED_VALUE.doubleValue()))
                .andExpect(jsonPath("$[0].debtors.[0].name").value(UPDATED_NAME))
                .andExpect(jsonPath("$[0].debtors.[0].email").value(UPDATED_EMAIL))
                .andExpect(jsonPath("$[0].debtors.[0].cellNumber").value(UPDATED_CELL_NUMBER));
    }

    static Stream<Arguments> dataSingleProvider() {
        return Stream.of(
                Arguments.of("value=" + UPDATED_VALUE.intValue()),
                Arguments.of("value=" + (DEFAULT_VALUE.intValue() + 1) + "&maxValue=" + UPDATED_VALUE.intValue()),
                Arguments.of("dueDate=" + UPDATED_DATE.toString()),
                Arguments.of("dueDate=" + UPDATED_DATE.toString() + "&endDueDate=" + UPDATED_DATE.toString()),
                Arguments.of("debtor=" + UPDATED_NAME));
    }

    @ParameterizedTest
    @MethodSource("dataMultipleProvider")
    public void checkMultipleParams(String param1, String param2) throws Exception {
        // Get all the debtList
        this.restMockMvc.perform(get("/api/debts?" + param1 + "&" + param2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].uuid").value(DEFAULT_UUID.toString()))
                .andExpect(jsonPath("$[0].dueDate").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$[0].value").value(DEFAULT_VALUE.doubleValue()))
                .andExpect(jsonPath("$[0].debtors.[0].name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$[0].debtors.[0].email").value(DEFAULT_EMAIL))
                .andExpect(jsonPath("$[0].debtors.[0].cellNumber").value(DEFAULT_CELL_NUMBER))
                .andExpect(jsonPath("$[1].uuid").value(UPDATED_UUID.toString()))
                .andExpect(jsonPath("$[1].dueDate").value(UPDATED_DATE.toString()))
                .andExpect(jsonPath("$[1].value").value(UPDATED_VALUE.doubleValue()))
                .andExpect(jsonPath("$[1].debtors.[0].name").value(UPDATED_NAME))
                .andExpect(jsonPath("$[1].debtors.[0].email").value(UPDATED_EMAIL))
                .andExpect(jsonPath("$[1].debtors.[0].cellNumber").value(UPDATED_CELL_NUMBER));;
    }

    static Stream<Arguments> dataMultipleProvider() {
        return Stream.of(
                Arguments.of("value=" + DEFAULT_VALUE.intValue(), "maxValue=" + UPDATED_VALUE.intValue()),
                Arguments.of("dueDate=" + DEFAULT_DATE.toString(), "endDueDate=" + UPDATED_DATE.toString()));
    }
}
