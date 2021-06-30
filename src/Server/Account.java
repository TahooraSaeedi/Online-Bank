package Server;

import java.util.ArrayList;

public class Account {
    public static int count=1000;
    private String accountNumber;
    private String password;
    private AccountType accountType;
    private String alias;
    private String balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Loan> loans;

    public Account(AccountType accountType, String password) {
        this.accountType = accountType;
        this.password = password;
        Account.count++;
    }

    //********************

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    //********************

    public boolean compareTo(Account account) {
        boolean result = false;
        if (account != null) {
            if (this.getAccountNumber().compareTo(account.getAccountNumber()) == 0) result = true;
        }
        return result;
    }

    public void getLoan(String amount, int month) {
        this.getLoans().add(new Loan(amount, month));
        this.setBalance(MyMath.findSum(this.getBalance(),amount));
    }

    public void payLoan() {
        if (this.getLoans() != null) {
            for (Loan loan : this.getLoans()) {
                int time = (int) ((System.currentTimeMillis() - loan.getTime()) / (2592000000L));
                String toPay = MyMath.findMultiply(loan.getEachMonth(), (time + ""));
                if (MyMath.findDiff(this.getBalance(), loan.getAmount()).charAt(0) != '-') {
                    this.setBalance(MyMath.findDiff(this.getBalance(), toPay));
                    loan.setTime(System.currentTimeMillis());
                }
            }
        }
    }

    public void payBill(String amount) throws Exception {
        String newBalance = MyMath.findDiff(this.getBalance(), amount);
        if (newBalance.charAt(0) == '-') throw new Exception("Account balance is insufficient.");
        else this.setBalance(newBalance);
    }

    public void moneyTransfer(Account destination, String amount) throws Exception {
        String newBalance = MyMath.findDiff(this.getBalance(), amount);
        if (newBalance.charAt(0) == '-') throw new Exception("Account balance is insufficient.");
        else {
            this.setBalance(newBalance);
            destination.setBalance(MyMath.findSum(destination.getBalance(), amount));
        }
    }

}
