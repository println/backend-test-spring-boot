package zup.hiring.debtcollector.domains.debt.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.DebtService;

import javax.validation.Valid;

@Component
@Validated
public class DebtListener {
    private final Logger log = LoggerFactory.getLogger(DebtListener.class);
    private DebtService service;

    public DebtListener(DebtService service) {
        this.service = service;
        this.log.info("Listen messages now!");
    }

    @RabbitListener(queues = "${queue.debt-collector}")
    public void listen(Message message) {
        Debt debt = message.getData();
        Operation operation = message.getOperation();
        this.log.info("Received operation[{}] uuid[{}] to Debt Collector!", operation, debt.getUuid());
        this.handle(operation, debt);
    }

    private void handle(@Valid Operation operation, @Valid Debt debt) {
        switch (operation) {
            case POST:
                this.service.create(debt);
                break;
            case PUT:
                this.service.update(debt);
                break;
            case DELETE:
                this.service.deleteByUuid(debt.getUuid());
                break;
        }
    }
}
