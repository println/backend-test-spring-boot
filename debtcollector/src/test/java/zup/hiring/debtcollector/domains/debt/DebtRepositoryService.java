package zup.hiring.debtcollector.domains.debt;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DebtRepositoryService {
    @Autowired
    private DebtRepository repository;

    public Debt save(Debt debt){
        return this.repository.save(debt);
    }
    public List<Debt> findAll(){
        return this.repository.findAll();
    }

    public Optional<Debt> findByUuid(UUID uuid){return this.repository.findByUuid(uuid);}

    public void deleteAll() {
        this.repository.deleteAll();
    }
}
