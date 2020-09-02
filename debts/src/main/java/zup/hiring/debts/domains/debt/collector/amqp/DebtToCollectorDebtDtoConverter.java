package zup.hiring.debts.domains.debt.collector.amqp;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import zup.hiring.debts.domains.debt.Debt;

import java.util.stream.Collectors;

@Component
public class DebtToCollectorDebtDtoConverter implements Converter<Debt, CollectorDebtDto> {

    @Override
    public CollectorDebtDto convert(Debt debt) {
        return new CollectorDebtDto()
                .uuid(debt.getUuid())
                .value(debt.getValue())
                .dueDate(debt.getDueDate())
                .debtors(debt.getDebtors().stream()
                    .map(debtor -> new CollectorDebtorDto()
                        .name(debtor.getName())
                        .email(debtor.getEmail())
                        .cellNumber(debtor.getCellNumber()))
                    .collect(Collectors.toSet()));
    }
}
