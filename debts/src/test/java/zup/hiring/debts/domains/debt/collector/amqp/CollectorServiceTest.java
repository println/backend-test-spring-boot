package zup.hiring.debts.domains.debt.collector.amqp;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import zup.hiring.debts.domains.debt.AbstractDebtTest;
import zup.hiring.debts.domains.debt.Debt;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectorServiceTest extends AbstractDebtTest {
    
    @Value("${queue.debt-collector}")
    private String queueName;

    @Test
    public void whenCreateDebt() {
        Debt result = this.debtService.create(this.debt);
        assertRabbitTemplate(result, Operation.POST);
    }

    @Test
    public void whenUpdateDebt() {
        this.repository.save(this.debt);

        Debt debt = new Debt()
                .dueDate(UPDATED_DATE)
                .value(UPDATED_VALUE)
                .status(UPDATED_STATUS)
                .debtors(this.debt.getDebtors());

        Debt result = this.debtService.update(this.debt.getId(), debt);
        assertRabbitTemplate(result, Operation.PUT);
    }

    @Test
    @Transactional
    public void whenPayOffDebt() {
        this.repository.save(this.debt);
        Debt result = this.debtService.payOffById(this.debt.getId());
        assertRabbitTemplate(result, Operation.DELETE);
    }

    @Test
    @Transactional
    public void whenDeleteDebt() {
        Debt debt = new Debt();
        this.repository.save(this.debt);

        BeanUtils.copyProperties(this.debt, debt);

        this.debtService.deleteById(this.debt.getId());
        assertRabbitTemplate(debt, Operation.DELETE);
    }

    private void assertRabbitTemplate(Debt debt, Operation operation) {
        ArgumentCaptor<String> queueNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        Mockito.verify(this.rabbitTemplate, Mockito.times(1))
                .convertAndSend(queueNameCaptor.capture(), messageCaptor.capture());

        String capturedQueueName = queueNameCaptor.getValue();
        Message captureMessage = messageCaptor.getValue();
        CollectorDebtDto dto = new DebtToCollectorDebtDtoConverter().convert(debt);

        assertThat(capturedQueueName).isEqualTo(this.queueName);
        assertThat(captureMessage.getData()).isEqualTo(dto);
        assertThat(captureMessage.getOperation()).isEqualTo(operation);
    }
}
