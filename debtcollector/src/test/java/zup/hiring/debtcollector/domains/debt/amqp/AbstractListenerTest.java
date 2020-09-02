package zup.hiring.debtcollector.domains.debt.amqp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import zup.hiring.debtcollector.domains.debt.AbstractDebtTest;

public abstract class AbstractListenerTest extends AbstractDebtTest {
    protected DebtListener listener;

    protected Message message;

    @BeforeAll
    public void createListener() {
        this.listener = new DebtListener(this.debtService);
    }

    @BeforeEach
    public void init() {
        this.message = new Message();
        this.message.setData(this.debt);
    }
}
