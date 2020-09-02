package zup.hiring.debts.domains.debtor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface DebtorRepository extends JpaRepository<Debtor, Long>, JpaSpecificationExecutor<Debtor> {

    boolean existsByCpfOrEmail(String cpf, String email);

    Optional<Debtor> findByCpf(String cpf);

    @Query(value = "select count(*) > 0 from debtor_debt where debtor_id = ?", nativeQuery = true)
    boolean existsDependents(Long debtorId);
}
