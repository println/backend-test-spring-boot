package zup.hiring.debtcollector.domains.debt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

interface CustomMongoRepository {
    Page<Debt> findAll(Query var1, Pageable var2);
}
