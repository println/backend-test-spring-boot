package zup.hiring.debts.domains.debt;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import zup.hiring.debts.domains.debt.collector.amqp.CollectorService;
import zup.hiring.debts.domains.debt.web.DebtRequestFilter;
import zup.hiring.debts.domains.debtor.Debtor;
import zup.hiring.debts.domains.debtor.DebtorService;
import zup.hiring.debts.support.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class DebtService {
    private static final String ENTITY_NAME = "Debt";

    private final DebtRepository repository;
    private final DebtorService debtorService;
    private final CollectorService collectorService;

    public DebtService(DebtRepository repository,
                       DebtorService debtorService,
                       CollectorService collectorService) {
        this.repository = repository;
        this.debtorService = debtorService;
        this.collectorService = collectorService;
    }

    public Page<Debt> findAll(DebtRequestFilter filter, Pageable pageable) {
        Specification<Debt> specification = Specification.where(null);
        if (filter.hasUuid()) {
            specification = specification.and(DebtSpecification.uuid(filter.getUuid()));
        }
        if (filter.hasValue()) {
            specification = specification.and(DebtSpecification.value(filter.getValue(), filter.getMaxValue()));
        }
        if (filter.hasDueDate()) {
            specification = specification.and(DebtSpecification.dueDate(filter.getDueDate(), filter.getEndDueDate()));
        }
        if (filter.hasStatus()) {
            specification = specification.and(DebtSpecification.status(filter.getStatus()));
        }
        if (filter.hasDebtorId()) {
            specification = specification.and(DebtSpecification.debtorId(filter.getDebtorId()));
        }
        return this.repository.findAll(specification, pageable);
    }

    public Optional<Debt> findById(Long id) {
        return this.repository.findById(id);
    }

    public Long count() {
        return this.repository.count();
    }

    public void deleteById(Long id) {
        Optional<Debt> result = this.findById(id);

        if (!result.isPresent()) {
            throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
        }

        Debt entity = result.get();
        Debt detachedEntity = new Debt();
        BeanUtils.copyProperties(entity, detachedEntity);
        detachedEntity.setDebtors(entity.getDebtors().stream().collect(Collectors.toSet()));

        entity.setDebtors(null);
        this.repository.delete(entity);
        this.collectorService.removeDebt(detachedEntity);
    }

    public Debt create(@Valid Debt debt) {
        assertDueDate(debt);
        Set<Debtor> debtors = findDebtorsByCpf(debt.getDebtors());

        Debt entity = new Debt()
                .uuid(UUID.randomUUID())
                .dueDate(debt.getDueDate())
                .value(debt.getValue())
                .status(DebtStatus.UNPAID)
                .debtors(debtors);

        Debt savedEntity = this.repository.save(entity);
        this.collectorService.addDebt(savedEntity);
        return savedEntity;
    }

    public Debt update(Long id, @Valid Debt debt) {
        Optional<Debt> result = this.findById(id);

        if (!result.isPresent()) {
            throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
        }

        assertDueDate(debt);
        Set<Debtor> debtors = findDebtorsByCpf(debt.getDebtors());

        Debt entity = result.get()
                .dueDate(debt.getDueDate())
                .value(debt.getValue())
                .debtors(debtors);

        Debt savedEntity = this.repository.save(entity);
        this.collectorService.changeDebt(savedEntity);
        return savedEntity;
    }

    private void assertDueDate(Debt debt) {
        LocalDate dueDate = debt.getDueDate();
        LocalDate now = LocalDate.now();
        LocalDate nextYear =  now.plusYears(1);

        if (dueDate.isBefore(now) || dueDate.isAfter(nextYear)) {
            throw new BadRequestAlertException(
                    "Due date should be between today and 1 year in the future", ENTITY_NAME, "idnull");
        }
    }

    private Set<Debtor> findDebtorsByCpf(Set<Debtor> cpfDebts) {
        List<String> errors = new ArrayList<>();
        Set<Debtor> debtors = new HashSet<>();

        for (Debtor debtor : cpfDebts) {
            Optional<Debtor> optionalDebtor = this.debtorService.findByCpf(debtor.getCpf());

            if (optionalDebtor.isPresent()) {
                debtors.add(optionalDebtor.get());
            } else {
                errors.add(debtor.getCpf());
            }
        }

        if (!errors.isEmpty()) {
            String cpfs = String.join(",", errors);
            throw new BadRequestAlertException("The cpfs[" + cpfs + "] not exists", ENTITY_NAME, "idnull");
        }

        return debtors;
    }

    public Debt payOffById(Long id) {
        Optional<Debt> result = this.findById(id);

        if (!result.isPresent()) {
            throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
        }

        Debt entity = result.get().status(DebtStatus.PAID);

        Debt savedEntity = this.repository.save(entity);
        this.collectorService.removeDebt(savedEntity);
        return savedEntity;
    }
}
