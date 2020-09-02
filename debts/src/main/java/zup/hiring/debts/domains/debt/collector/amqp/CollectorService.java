package zup.hiring.debts.domains.debt.collector.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zup.hiring.debts.domains.debt.Debt;

@Service
public class CollectorService {
    private final Logger log = LoggerFactory.getLogger(CollectorService.class);

    private RabbitTemplate rabbitTemplate;
    private String collectorQueue;
    private DebtToCollectorDebtDtoConverter converter;

    public CollectorService(
            RabbitTemplate rabbitTemplate,
            @Value("${queue.debt-collector}") String collectorQueue,
            DebtToCollectorDebtDtoConverter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.collectorQueue = collectorQueue;
        this.converter = converter;
    }

    public void addDebt(Debt debt) {
        this.send(debt, Operation.POST);
    }

    public void changeDebt(Debt debt) {
        this.send(debt, Operation.PUT);
    }

    public void removeDebt(Debt debt) {
        this.send(debt, Operation.DELETE);
    }

    private void send(Debt debt, Operation operation) {
        CollectorDebtDto dto = this.converter.convert(debt);

        Message message = new Message()
                .data(dto)
                .operation(operation);

        this.rabbitTemplate.convertAndSend(this.collectorQueue, message);
        this.log.info("Notified operation[{}] uuid[{}] to Debt Collector!", operation, dto.getUuid());
    }
}
