package zup.hiring.debtcollector.domains.debt;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
interface DebtRepository extends MongoRepository<Debt, String>, CustomMongoRepository {
    boolean existsByUuid(UUID uuid);
    Optional<Debt> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
    Optional<Set<Debt>> findAllByDueDate(LocalDate dueDate);
}
