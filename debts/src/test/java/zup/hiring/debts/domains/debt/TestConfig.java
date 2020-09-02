package zup.hiring.debts.domains.debt;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import zup.hiring.debts.domains.debtor.DebtorRepositoryService;

@TestConfiguration
public class TestConfig {

    @Bean
    public DebtRepositoryService createRepository() {
        return new DebtRepositoryService();
    }

    @Bean
    public DebtorRepositoryService createDebtorRepository() {
        return new DebtorRepositoryService();
    }
}
