package zup.hiring.debts.domains.debt.collector.amqp;

public class Message {
    private Operation operation;
    private CollectorDebtDto data;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public CollectorDebtDto getData() {
        return data;
    }

    public void setData(CollectorDebtDto data) {
        this.data = data;
    }

    public Message operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public Message data(CollectorDebtDto data) {
        this.data = data;
        return this;
    }

}
