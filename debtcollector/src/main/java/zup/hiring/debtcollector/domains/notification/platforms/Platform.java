package zup.hiring.debtcollector.domains.notification.platforms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zup.hiring.debtcollector.domains.debt.Debtor;

import javax.annotation.PostConstruct;

public interface Platform {
    Logger log = LoggerFactory.getLogger(Platform.class);

    String getPlatformName();
    void notifyDebtor(Debtor debtor, String bodyMessage);

    @PostConstruct
    default void init(){
        log.info("Notification platform up: [{}]", getPlatformName());
    }
}
