package zup.hiring.debtcollector.domains.debt;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public DebtRepositoryService createRepository() {
        return new DebtRepositoryService();
    }
}
