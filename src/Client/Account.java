package Client;

public class Account {
    private String accountNumber;
    private final AccountType accountType;
    private String alias;

    public Account(String accountNumber, AccountType accountType, String alias) {
        this.accountNumber = accountNumber ;
        this.accountType = accountType ;
        this.alias = alias ;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
