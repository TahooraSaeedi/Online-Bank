package Server;

import java.io.Serializable;

public class Transaction implements Serializable {
    private final String originAccountNumber;
    private final String destinationAccountNumber;
    private final String amount;

    public Transaction(String originAccountNumber, String destinationAccountNumber, String amount) {
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public String getAmount() {
        return amount;
    }

}