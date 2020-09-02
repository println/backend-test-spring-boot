package zup.hiring.debts.domains.debtor;

import org.springframework.data.jpa.domain.Specification;

public class DebtorSpecification {
	public static Specification<Debtor> name(String name) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<Debtor> cpf(String cpf) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("cpf")), "%" + cpf.toLowerCase() + "%");
	}

	public static Specification<Debtor> cellNumber(String cellNumber) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("cellNumber")), "%" + cellNumber.toLowerCase() + "%");
	}

	public static Specification<Debtor> email(String email) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
	}
}
