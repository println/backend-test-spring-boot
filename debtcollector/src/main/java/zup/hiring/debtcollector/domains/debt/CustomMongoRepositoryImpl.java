package zup.hiring.debtcollector.domains.debt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

class CustomMongoRepositoryImpl implements CustomMongoRepository {
    private static final String COLLECTION_NAME = "debt";

    private MongoTemplate mongoTemplate;

    public CustomMongoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Page<Debt> findAll(Query query, Pageable pageable) {
        query.with(pageable);
        query.limit(pageable.getPageSize());

        List<Debt> list = this.mongoTemplate.find(query, Debt.class, COLLECTION_NAME);
        Page<Debt> page = PageableExecutionUtils.getPage(list, pageable,
                () -> this.mongoTemplate.count(query, Debt.class));
        return page;
    }
}
