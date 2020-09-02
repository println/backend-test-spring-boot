package zup.hiring.debtcollector.domains.debt;


import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class DebtSpecification {
    public static Criteria value(BigDecimal value, BigDecimal maxValue) {
        Criteria criteria = new Criteria().where("value");

        if (Objects.nonNull(maxValue)) {
            return criteria.gte(value).lte(maxValue);
        }

        return criteria.is(value);
    }
//
    public static Criteria dueDate(LocalDate dueDate, LocalDate endDueDate) {
        Criteria criteria = new Criteria().where("dueDate");

        if (Objects.nonNull(endDueDate)) {
            return criteria.gte(dueDate).lte(endDueDate);
        }

        return criteria.is(dueDate);
    }

    public static Criteria debtorName(String name) {
        return new Criteria().where("debtors.name").regex(".*" + name + ".*", "i");
    }
}
