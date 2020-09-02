package zup.hiring.debts.domains.debt.web;

import org.springframework.core.convert.converter.Converter;
import zup.hiring.debts.domains.debt.DebtStatus;

public class StringToDebtStatusConverter implements Converter<String, DebtStatus> {
    @Override
    public DebtStatus convert(String status) {
        return DebtStatus.getEnum(status);
    }
}
