package zup.hiring.debts.domains.debt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import zup.hiring.debts.AbstractResourceTest;
import zup.hiring.debts.domains.debt.web.*;
import zup.hiring.debts.domains.debtor.AbstractDebtorTest;
import zup.hiring.debts.domains.debtor.Debtor;
import zup.hiring.debts.domains.debtor.DebtorRepositoryService;
import zup.hiring.debts.domains.debtor.DebtorService;

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

    public static final DebtStatus DEFAULT_STATUS = DebtStatus.UNPAID;
    public static final DebtStatus UPDATED_STATUS = DebtStatus.PAID;

    @MockBean
    protected RabbitTemplate rabbitTemplate;

    @Autowired
    protected DebtRepositoryService repository;

    @Autowired
    protected DebtorRepositoryService debtorRepository;

    @Autowired
    protected DebtService debtService;

    @Autowired
    protected DebtorService debtorService;

    protected Debtor debtor;
    protected Debt debt;
    protected DebtDto debtDto;
    protected SimpleDebtDto simpleDebtDto;

    public static Debt createEntity(Set<Debtor> debtors) {
        return new Debt()
                .dueDate(DEFAULT_DATE)
                .value(DEFAULT_VALUE)
                .status(DEFAULT_STATUS)
                .uuid(DEFAULT_UUID)
                .debtors(debtors);
    }

    public static DebtDto createEntityDto() {
        return new DebtDto(createEntity(null));
    }

    public static SimpleDebtDto createEntitySimpleDto() {
        Debt debt = createEntity(null);
        SimpleDebtDto simpleDebtDto = new SimpleDebtDto();
        simpleDebtDto.setValue(debt.getValue());
        simpleDebtDto.setDueDate(debt.getDueDate());
        simpleDebtDto.setCpfs(new HashSet<>(Arrays.asList(AbstractDebtorTest.DEFAULT_CPF)));
        return simpleDebtDto;
    }

    public static Debtor createMandatoryEntity() {
        return AbstractDebtorTest.createEntity();
    }

    @Override
    public Object createResource() {
        return new DebtResource(this.debtService, new SimpleDebtDtoToDebtConverter(), new DebtToDebtDtoConverter());
    }

    @BeforeEach
    public void initTest() {
        this.repository.deleteAll();
        this.debtorRepository.deleteAll();

        this.debtor = createMandatoryEntity();
        this.debtor = this.debtorService.create(this.debtor);

        this.debt = createEntity(new HashSet<>(Arrays.asList(this.debtor)));
        this.debtDto = createEntityDto();
        this.simpleDebtDto = createEntitySimpleDto();
    }

    @AfterAll
    public void clean() {
        this.repository.deleteAll();
        this.debtorRepository.deleteAll();
    }
}
