package zup.hiring.debts.domains.debtor.web;

import java.util.Objects;

public class DebtorRequestFilter {
	private String cpf;
	private String name;
	private String email;
	private String cellNumber;

	public DebtorRequestFilter(String cpf, String name, String email, String cellNumber) {
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.cellNumber = cellNumber;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public boolean hasCpf() {
		return Objects.nonNull(this.cpf);
	}

	public boolean hasName() {
		return Objects.nonNull(this.name);
	}

	public boolean hasEmail() {
		return Objects.nonNull(this.email);
	}

	public boolean hasCellNumber() {
		return Objects.nonNull(this.cellNumber);
	}

}
