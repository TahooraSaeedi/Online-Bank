package Server;

import java.util.ArrayList;

public class User {

    private String name;
    private String nationalId;
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

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
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

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Account> getFavoriteAccounts() {
        return favoriteAccounts;
    }

    public void setFavoriteAccounts(ArrayList<Account> favoriteAccounts) {
        this.favoriteAccounts = favoriteAccounts;
    }

    //********************

    public boolean compareTo(User user) {
        if (user != null) return this.getNationalId() == user.getNationalId();
        else return false;
    }

    public void addAccount(AccountType accountType, String password) {
        this.accounts.add(new Account(accountType, password));
    }

    public void closeAccount(Account account) {
        for (Account a : this.accounts) {
            if (a.compareTo(account)) this.accounts.remove(account);
        }
    }

    public void addUsefulAccount(Account account) {
        for (Account a : this.accounts) {
            if (a.compareTo(account)) this.favoriteAccounts.add(account);
        }
    }

}
