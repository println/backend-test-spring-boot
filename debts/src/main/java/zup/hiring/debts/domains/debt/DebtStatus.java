package zup.hiring.debts.domains.debt;

public enum DebtStatus {
    PAID, UNPAID;

    public static DebtStatus getEnum(String value) {
        for (DebtStatus status : values()) {
            if (status.toString().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
