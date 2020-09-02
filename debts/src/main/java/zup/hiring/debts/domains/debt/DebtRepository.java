package zup.hiring.debts.domains.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface DebtRepository extends JpaRepository<Debt, Long>, JpaSpecificationExecutor<Debt> {
    boolean existsByUuid(UUID uuid);
}
