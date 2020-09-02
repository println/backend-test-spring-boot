package zup.hiring.debtcollector.domains.debt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import zup.hiring.debtcollector.AbstractResourceTest;
import zup.hiring.debtcollector.domains.debt.web.DebtResource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Import(TestConfig.class)
public abstract class AbstractDebtTest extends AbstractResourceTest {
    public static final UUID DEFAULT_UUID = UUID.fromString("694cba04-e8ac-11ea-adc1-0242ac120002");
    public static final UUID UPDATED_UUID = UUID.fromString("5cdecd30-f273-439c-9638-923318fb91c5");

    public static final BigDecimal DEFAULT_VALUE = new BigDecimal("1010.00");
    public static final BigDecimal UPDATED_VALUE = new BigDecimal("2020.00");

    public static final LocalDate DEFAULT_DATE = LocalDate.now().plusDays(1);
    public static final LocalDate UPDATED_DATE = LocalDate.now().plusDays(7);

    public static final String DEFAULT_NAME = "AAAAAAAAAA";
    public static final String UPDATED_NAME = "BBBBBBBBBB";

    public static final String DEFAULT_EMAIL = "aaaaaaaaaa@aaaaaaaaaa.com";
    public static final String UPDATED_EMAIL = "bbbbbbbbbb@bbbbbbbbbb.com";

    public static final String DEFAULT_CELL_NUMBER = "21123451234";
    public static final String UPDATED_CELL_NUMBER = "11123451234";

    @MockBean
    protected RabbitTemplate rabbitTemplate;

    @Autowired
    protected DebtRepositoryService repository;

    @Autowired
    protected DebtService debtService;

    protected Debt debt;
    protected Debt debt2;
    protected Debt debt3;

    public static Debtor createAuxEntity() {
        return new Debtor()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .cellNumber(DEFAULT_CELL_NUMBER);
    }

    public static Debtor createAuxEntity2() {
        return new Debtor()
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .cellNumber(UPDATED_CELL_NUMBER);
    }

    public static Debt createEntity(Set<Debtor> debtors) {
        return new Debt()
                .dueDate(DEFAULT_DATE)
                .value(DEFAULT_VALUE)
                .uuid(DEFAULT_UUID)
                .debtors(debtors);
    }

    public static Debt createEntity2(Set<Debtor> debtors) {
        return new Debt()
                .dueDate(UPDATED_DATE)
                .value(UPDATED_VALUE)
                .uuid(UPDATED_UUID)
                .debtors(debtors);
    }

    @Override
    public Object createResource() {
        return new DebtResource(this.debtService);
    }

    @BeforeEach
    public void initTest() {
        this.repository.deleteAll();
        Debtor debtor = createAuxEntity();
        Debtor debtor2 = createAuxEntity2();
        this.debt = createEntity(new HashSet<>(Arrays.asList(debtor)));
        this.debt2 = createEntity2(new HashSet<>(Arrays.asList(debtor2)));
        this.debt3 = createEntity2(new HashSet<>(Arrays.asList(debtor, debtor2)));
    }

    @AfterAll
    public void clean() {
        this.repository.deleteAll();
    }
}
