package zup.hiring.debtcollector.domains.notification.platforms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import zup.hiring.debtcollector.domains.debt.Debtor;

import javax.validation.Valid;

@Service
@ConditionalOnProperty(name = "notify.platform.phone-call")
public class PhoneCall implements Platform {
    @Override
    public String getPlatformName() {
        return "Phone call";
    }

    @Override
    public void notifyDebtor(@Valid Debtor debtor, String bodyMessage) {
        StringBuilder builder = new StringBuilder()
                .append("Platform: ").append(this.getPlatformName()).append(System.getProperty("line.separator"))
                .append("Contact: ").append(debtor.getCellNumber()).append(System.getProperty("line.separator"))
                .append("Message: ").append(bodyMessage).append(System.getProperty("line.separator"));

        System.out.println(builder.toString());
    }
}
