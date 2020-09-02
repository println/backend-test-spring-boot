package zup.hiring.debtcollector.domains.debt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import zup.hiring.debtcollector.domains.debt.web.DebtRequestFilter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Service
@Validated
public class DebtService {
    private final Logger log = LoggerFactory.getLogger(DebtService.class);

    private final DebtRepository repository;

    public DebtService(DebtRepository repository) {
        this.repository = repository;
    }

    public Page<Debt> findAll(DebtRequestFilter filter, Pageable pageable) {
        List<Criteria> criterias = new ArrayList<>();

        if (filter.hasValue()) {
            criterias.add(DebtSpecification.value(filter.getValue(), filter.getMaxValue()));
        }
        if (filter.hasDueDate()) {
            criterias.add(DebtSpecification.dueDate(filter.getDueDate(), filter.getEndDueDate()));
        }
        if (filter.hasDebtorName()) {
            criterias.add(DebtSpecification.debtorName(filter.getDebtorName()));
        }

        Query query = new Query();

        if(!criterias.isEmpty()) {
            Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[]{}));
            query.addCriteria(criteria);
        }

        return this.repository.findAll(query, pageable);
    }

    public Optional<Debt> findById(UUID uuid) {
        return this.repository.findByUuid(uuid);
    }

    public Long count() {
        return this.repository.count();
    }

    public void deleteByUuid(UUID uuid) {
        this.repository.deleteByUuid(uuid);
    }

    public Debt create(@Valid Debt debt) {
        Debt entity = new Debt()
                .uuid(debt.getUuid())
                .dueDate(debt.getDueDate())
                .value(debt.getValue())
                .debtors(debt.getDebtors());

        this.log.info("Create: {}", entity);
        return this.repository.save(entity);
    }

    public Debt update(@Valid Debt debt) {
        Optional<Debt> result = this.repository.findByUuid(debt.getUuid());

        if (!result.isPresent()) {
            this.log.info("Not exists to update", debt.getUuid());
        }

        Debt entity = result.get()
                .dueDate(debt.getDueDate())
                .value(debt.getValue())
                .debtors(debt.getDebtors());

        this.log.info("Update: {}", entity);
        return this.repository.save(entity);
    }

    public Optional<Set<Debt>> findAllByDueDate(LocalDate dueDate) {
        return this.repository.findAllByDueDate(dueDate);
    }
}
