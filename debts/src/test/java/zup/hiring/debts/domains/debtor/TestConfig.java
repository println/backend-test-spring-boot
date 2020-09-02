package zup.hiring.debts.domains.debtor;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public DebtorRepositoryService createRepository() {
        return new DebtorRepositoryService();
    }
}
