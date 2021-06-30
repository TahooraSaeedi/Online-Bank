package Server;

public class Transaction {
    private Account origin;
    private Account destination;
    private String amount;

    public Transaction(Account origin, Account destination, String amount) {
        this.origin = origin;
        this.destination = destination;
        this.amount = amount;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}