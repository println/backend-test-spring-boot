package zup.hiring.debtcollector.domains.notification.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.DebtService;
import zup.hiring.debtcollector.domains.notification.NotificationService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "notify.cron")
public class NotificationCron {
    private final Logger log = LoggerFactory.getLogger(NotificationCron.class);

    private final NotificationService notificationService;
    private final DebtService debtService;

    public NotificationCron(NotificationService notificationService, DebtService debtService) {
        this.notificationService = notificationService;
        this.debtService = debtService;
    }

    @Scheduled(cron = "${notify.cron}")
    public void fireNotifications() {
        this.notifyDueDateBefore10Days();
        this.notifyDueDateEqualsToday();
    }

    private void notifyDueDateBefore10Days() {
        Optional<Set<Debt>> debtsResult = this.debtService.findAllByDueDate(LocalDate.now().plusDays(10));
        notifyDueDate(debtsResult);
    }

    private void notifyDueDateEqualsToday() {
        Optional<Set<Debt>> debtsResult = this.debtService.findAllByDueDate(LocalDate.now().plusDays(1));
        notifyDueDate(debtsResult);
    }

    private void notifyDueDate(Optional<Set<Debt>> debtsResult) {
        if (debtsResult.isPresent() && !debtsResult.get().isEmpty()) {
            Set<Debt> debts = debtsResult.get();
            this.log.info("Notifying {} debts for payment!", debts.size());
            this.notificationService.sendNotification(debts);
        }
    }
}
