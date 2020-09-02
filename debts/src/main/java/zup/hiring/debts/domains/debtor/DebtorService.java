package zup.hiring.debts.domains.debtor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zup.hiring.debts.domains.debtor.web.DebtorRequestFilter;
import zup.hiring.debts.support.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class DebtorService {
    private static final String ENTITY_NAME = "Debtor";

    private final DebtorRepository repository;

    public DebtorService(DebtorRepository repository) {
        this.repository = repository;
    }

    public Page<Debtor> findAll(DebtorRequestFilter filter, Pageable pageable) {
        Specification<Debtor> specification = Specification.where(null);
        if (filter.hasCpf()) {
            specification = specification.and(DebtorSpecification.cpf(filter.getCpf()));
        }
        if (filter.hasName()) {
            specification = specification.and(DebtorSpecification.name(filter.getName()));
        }
        if (filter.hasEmail()) {
            specification = specification.and(DebtorSpecification.email(filter.getEmail()));
        }
        if (filter.hasCellNumber()) {
            specification = specification.and(DebtorSpecification.cellNumber(filter.getCellNumber()));
        }
        return this.repository.findAll(specification, pageable);
    }

    public Optional<Debtor> findById(Long id) {
        return this.repository.findById(id);
    }

    public Long count() {
        return this.repository.count();
    }

    public void deleteById(Long id) {
        Optional<Debtor> result = this.findById(id);
        if (!result.isPresent()) {
            throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
        }

        boolean hasDependents = this.repository.existsDependents(id);
        if (hasDependents) {
            throw new BadRequestAlertException("Cant remove, entity has dependents", ENTITY_NAME, "dependent");
        }

        this.repository.deleteById(id);
    }

    public Debtor create(@Valid Debtor debtor) {
        boolean isPresent = this.repository.existsByCpfOrEmail(debtor.getCpf(), debtor.getEmail());

        if (isPresent) {
            throw new BadRequestAlertException("Cpf or email already exists", ENTITY_NAME, "duplicated");
        }

        Debtor entity = new Debtor()
                .cpf(debtor.getCpf())
                .name(debtor.getName())
                .email(debtor.getEmail())
                .cellNumber(debtor.getCellNumber());

        return this.repository.save(entity);
    }

    public Debtor update(Long id, @Valid Debtor debtor) {
        Optional<Debtor> result = this.findById(id);

        if (!result.isPresent()) {
            throw new BadRequestAlertException("Not exists", ENTITY_NAME, "idnull");
        }

        Debtor entity = result.get()
                .cpf(debtor.getCpf())
                .name(debtor.getName())
                .email(debtor.getEmail())
                .cellNumber(debtor.getCellNumber());

        return this.repository.save(entity);
    }

    public Optional<Debtor> findByCpf(String cpf){
        return this.repository.findByCpf(cpf);
    }

}
