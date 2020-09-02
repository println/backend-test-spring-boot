package zup.hiring.debtcollector.domains.debt.amqp;

import zup.hiring.debtcollector.domains.debt.Debt;

import javax.validation.constraints.NotEmpty;

public class Message {
    @NotEmpty
    private Operation operation;

    @NotEmpty
    private Debt data;

    public Message() {
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Debt getData() {
        return data;
    }

    public void setData(Debt data) {
        this.data = data;
    }

    public Message operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public Message data(Debt data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "operation=" + operation +
                ", data=" + data +
                '}';
    }
}
