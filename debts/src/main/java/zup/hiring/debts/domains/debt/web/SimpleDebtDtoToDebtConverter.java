package zup.hiring.debts.domains.debt.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import zup.hiring.debts.domains.debt.Debt;
import zup.hiring.debts.domains.debt.DebtStatus;
import zup.hiring.debts.domains.debtor.Debtor;

import java.util.stream.Collectors;

@Component
public class SimpleDebtDtoToDebtConverter implements Converter<SimpleDebtDto, Debt> {

    @Override
    public Debt convert(SimpleDebtDto dto) {
        return new Debt()
                .value(dto.getValue())
                .dueDate(dto.getDueDate())
                .status(DebtStatus.UNPAID)
                .debtors(dto.getCpfs().stream()
                        .map(cpf -> new Debtor().cpf(cpf))
                        .collect(Collectors.toSet()));
    }
}
