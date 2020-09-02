package zup.hiring.debtcollector.domains.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.hiring.debtcollector.domains.debt.Debt;
import zup.hiring.debtcollector.domains.debt.Debtor;
import zup.hiring.debtcollector.domains.notification.platforms.Platform;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationService {
    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private List<Platform> platforms;

    @Autowired
    public NotificationService(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public void sendNotification(Set<Debt> debts) {
        for (Debt debt: debts) {
            for (Debtor debtor : debt.getDebtors()) {
                String message = this.createBodyMessage(debt);
                this.notifyDebtor(debtor, message);
            }
        }
    }

    private void notifyDebtor(Debtor debtor, String bodyMessage) {
        for (Platform platform : this.platforms) {
            this.notifyDebtor(debtor, bodyMessage, platform);
        }
    }

    private void notifyDebtor(Debtor debtor, String bodyMessage, Platform platform) {
        try {
            platform.notifyDebtor(debtor, bodyMessage.replace("{}", debtor.getName()));
        } catch (Exception ex) {
            this.log.debug("Can't notify customer through {}", platform.getPlatformName());
        }
    }

    private String createBodyMessage(Debt debt) {
        String date = debt.getDueDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String value = NumberFormat.getCurrencyInstance().format(debt.getValue());
        return String.format("\nOlá {},\nSua dívida está em %s e com a data de vencimento para %s.",
                value, date);
    }
}