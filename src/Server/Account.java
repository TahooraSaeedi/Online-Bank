package Server;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String accountNumber;
    private final String password;
    private final AccountType accountType;
    private String alias;
    private String balance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Loan> loans;

    public Account(AccountType accountType, String password) {
        this.accountNumber = "";
        this.password = password;
        this.accountType = accountType;
        this.alias = "Personal Account";
        this.balance = "0";
        this.transactions = new ArrayList<Transaction>();
        this.loans = new ArrayList<Loan>();
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

    public AccountType getAccountType() {
        return accountType;
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

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    //********************

    public void getLoan(String amount, int month) {
        this.getLoans().add(new Loan(amount, month));
        this.setBalance(MyMath.findSum(this.getBalance(), amount));
    }

    public void payLoan() {
        if (this.getLoans() != null) {
            for (Loan loan : this.getLoans()) {
                int time = (int) ((System.currentTimeMillis() - loan.getTime()) / (2592000000L));
                String toPay = MyMath.findMultiply(loan.getEachMonth(), (time + ""));
                if (MyMath.findDiff(this.getBalance(), toPay).charAt(0) != '-') {
                    this.setBalance(MyMath.findDiff(this.getBalance(), toPay));
                    this.transactions.add(new Transaction(this.getAccountNumber(), "Loan", toPay));
                    loan.setTime(System.currentTimeMillis());
                }
            }
        }
    }

    public void payBill(String amount) throws InsufficientFundsException {
        String newBalance = MyMath.findDiff(this.getBalance(), amount);
        if (newBalance.charAt(0) == '-') throw new InsufficientFundsException();
        else {
            this.setBalance(newBalance);
            this.transactions.add(new Transaction(this.getAccountNumber(), "Bill", amount));
        }
    }

    public void moneyTransfer(String destination, String amount, String password) throws Exception {
        if (this.getPassword().compareTo(password) != 0) throw new InvalidPasswordException();
        boolean found = false;
        Account destinationAccount = null;
        for (Account account : Information.accounts) {
            if (account.getAccountNumber().compareTo(destination) == 0) {
                destinationAccount = account;
                found = true;
                break;
            }
        }
        if (!found) throw new InvalidAccountNumberException();
        else {
            String newBalance = MyMath.findDiff(this.getBalance(), amount);
            if (newBalance.charAt(0) == '-') throw new InsufficientFundsException();
            else {
                this.setBalance(newBalance);
                destinationAccount.setBalance(MyMath.findSum(destinationAccount.getBalance(), amount));
                this.transactions.add(new Transaction(this.getAccountNumber(), destination, amount));
                destinationAccount.transactions.add(new Transaction(this.getAccountNumber(), destination, amount));
            }
        }
    }

}
