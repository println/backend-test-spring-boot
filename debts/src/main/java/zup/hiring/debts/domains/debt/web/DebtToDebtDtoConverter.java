package zup.hiring.debts.domains.debt.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import zup.hiring.debts.domains.debt.Debt;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DebtToDebtDtoConverter implements Converter<List<Debt>, List<DebtDto>> {

    @Override
    public List<DebtDto> convert(List<Debt> debts) {
        return debts.stream()
                .map(DebtDto::new)
                .collect(Collectors.toList());
    }
}
