package zup.hiring.debtcollector.domains.notification.cron;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import zup.hiring.debtcollector.AbstractTest;
import zup.hiring.debtcollector.domains.debt.*;
import zup.hiring.debtcollector.domains.notification.NotificationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestConfig.class)
class NotificationCronTest extends AbstractTest {

    @MockBean
    private NotificationService service;

    @Autowired
    private DebtService debtService;

    @Autowired
    private DebtRepositoryService repository;

    private NotificationCron cron;

    private Debt debt;
    private Debtor debtor;

    @BeforeAll
    public void createInstances(){
        this.cron = new NotificationCron(this.service, debtService);
    }

    @BeforeEach
    public void init(){
        this.repository.deleteAll();
        this.debtor = AbstractDebtTest.createAuxEntity();
        this.debt = AbstractDebtTest.createEntity(new HashSet<>(Arrays.asList(debtor)));
    }

    @Test
    public void notifyDueDateBefore10Days(){
        this.debt.setDueDate(LocalDate.now().plusDays(10));
        this.debtService.create(debt);
        this.cron.fireNotifications();
        this.assertNotification(debt);
    }

    @Test
    public void notifyDueDateEqualsTomorrow(){
        this.debt.setDueDate(LocalDate.now().plusDays(1));
        this.debtService.create(debt);
        this.cron.fireNotifications();
        this.assertNotification(debt);
    }

    private void assertNotification(Debt debt) {
        ArgumentCaptor<Set<Debt>> debtArgumentCaptor = ArgumentCaptor.forClass(Set.class);

        Mockito.verify(this.service, Mockito.times(1))
                .sendNotification(debtArgumentCaptor.capture());

        Set<Debt> capturedDebts = debtArgumentCaptor.getValue();
        Debt capturedDebt = capturedDebts.iterator().next();

        assertThat(capturedDebt).isEqualTo(debt);
    }
}