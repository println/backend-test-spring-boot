package zup.hiring.debts.domains.debtor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import zup.hiring.debts.AbstractResourceTest;
import zup.hiring.debts.domains.debtor.web.DebtorResource;

@Import(TestConfig.class)
public abstract class AbstractDebtorTest extends AbstractResourceTest {
    public static final String DEFAULT_CPF = "47536404000";
    public static final String UPDATED_CPF = "81644777029";

    public static final String DEFAULT_NAME = "AAAAAAAAAA";
    public static final String UPDATED_NAME = "BBBBBBBBBB";

    public static final String DEFAULT_EMAIL = "aaaaaaaaaa@aaaaaaaaaa.com";
    public static final String UPDATED_EMAIL = "bbbbbbbbbb@bbbbbbbbbb.com";

    public static final String DEFAULT_CELL_NUMBER = "21123451234";
    public static final String UPDATED_CELL_NUMBER = "11123451234";

    @Autowired
    protected DebtorRepositoryService repository;

    @Autowired
    protected DebtorService debtorService;

    protected Debtor debtor;

    public static Debtor createEntity() {
        return new Debtor()
                .cpf(DEFAULT_CPF)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .cellNumber(DEFAULT_CELL_NUMBER);
    }

    @Override
    public Object createResource() {
        return new DebtorResource(this.debtorService);
    }

    @BeforeEach
    public void initTest() {
        this.repository.deleteAll();
        this.debtor = createEntity();
    }

    @AfterAll
    public void clean() {
        this.repository.deleteAll();
    }
}
