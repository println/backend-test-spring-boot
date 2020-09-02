package zup.hiring.debts.domains.debtor;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DebtorRepositoryService {
    @Autowired
    private DebtorRepository  debtorRepository;

    public void save(Debtor debtor){
        this.debtorRepository.save(debtor);
    }
    public List<Debtor> findAll(){
        return this.debtorRepository.findAll();
    }

    public Optional<Debtor> findById(Long id){
        return this.debtorRepository.findById(id);
    }

    public void deleteAll() {
        this.debtorRepository.deleteAll();
    }


}
