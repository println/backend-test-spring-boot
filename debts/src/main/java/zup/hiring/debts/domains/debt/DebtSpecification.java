package zup.hiring.debts.domains.debt;

import org.springframework.data.jpa.domain.Specification;
import zup.hiring.debts.domains.debtor.Debtor;

import javax.persistence.criteria.Join;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DebtSpecification {
    public static Specification<Debt> uuid(UUID uuid) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("uuid"), uuid);
    }

    public static Specification<Debt> value(BigDecimal value, BigDecimal maxValue) {
        if (Objects.nonNull(maxValue)) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .between(root.get("value"), value, maxValue);
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("value"), value);
    }

    public static Specification<Debt> dueDate(LocalDate dueDate, LocalDate endDueDate) {
        if (Objects.nonNull(endDueDate)) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .between(root.get("dueDate"), dueDate, endDueDate);
        }
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("dueDate"), dueDate);
    }

    public static Specification<Debt> status(DebtStatus status) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("status"), status);
    }

    public static Specification<Debt> debtorId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Debt, Debtor> debtorJoin = root.join("debtors");
            return debtorJoin.get("id").in(id);
        };
    }
}
