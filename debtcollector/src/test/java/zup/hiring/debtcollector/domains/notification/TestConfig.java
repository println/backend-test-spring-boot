package zup.hiring.debtcollector.domains.notification;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import zup.hiring.debtcollector.domains.debt.DebtRepositoryService;

@TestConfiguration
public class TestConfig {

    @Bean
    public DebtRepositoryService createRepository() {
        return new DebtRepositoryService();
    }
}
