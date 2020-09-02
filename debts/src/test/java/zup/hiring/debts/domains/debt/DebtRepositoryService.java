package zup.hiring.debts.domains.debt;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DebtRepositoryService {
    @Autowired
    private DebtRepository repository;

    public Debt save(Debt debt){
        return this.repository.save(debt);
    }
    public List<Debt> findAll(){
        return this.repository.findAll();
    }

    public Optional<Debt> findById(Long id){
        return this.repository.findById(id);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }
}
