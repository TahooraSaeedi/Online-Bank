package Server;

import java.util.ArrayList;

public class User {

    private String name;
    private final String nationalId;
    private String password;
    private String phoneNumber;
    private String email;
    private ArrayList<Account> accounts;
    private ArrayList<Account> favoriteAccounts;

    public User(String name, String nationalId, String password, String phoneNumber, String email) {
        this.name = name;
        this.nationalId = nationalId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accounts = new ArrayList<Account>();
        this.favoriteAccounts = new ArrayList<Account>();
    }

    //********************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Account> getFavoriteAccounts() {
        return favoriteAccounts;
    }

    //********************

    public Account addAccount(AccountType accountType, String password) {
        Account newAccount = new Account(accountType, password);
        this.accounts.add(newAccount);
        Information.accounts.add(newAccount);
        return newAccount;
    }

    public void closeAccount(String accountNumber) throws CloseAccountException {
        for (Account account : this.accounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                if (account.getBalance().compareTo("0") != 0) throw new CloseAccountException();
                this.accounts.remove(account);
                break;
            }
        }
    }

    public void addFavoriteAccount(String accountNumber) throws DuplicateAccountNumberException {
        for (Account account : this.favoriteAccounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) throw new DuplicateAccountNumberException();
        }
        for (Account account : this.accounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                this.favoriteAccounts.add(account);
                break;
            }
        }
    }

}
