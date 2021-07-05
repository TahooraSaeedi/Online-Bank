package Server;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String name;
    private final String nationalId;
    private String password;
    private String phoneNumber;
    private String email;
    private String photo;
    private ArrayList<Account> accounts;
    private ArrayList<Account> favoriteAccounts;
    private transient Lock lock;

    public User(String name, String nationalId, String password, String phoneNumber, String email) {
        this.name = name;
        this.nationalId = nationalId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photo = "Profile.PNG";
        this.accounts = new ArrayList<Account>();
        this.favoriteAccounts = new ArrayList<Account>();
        this.lock = new ReentrantLock(true);
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Account> getFavoriteAccounts() {
        return favoriteAccounts;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    //********************

    public Account addAccount(AccountType accountType, String password) {
        Account newAccount = new Account(accountType, password);
        this.accounts.add(newAccount);
        Information.accounts.add(newAccount);
        return newAccount;
    }

    public void closeAccount(String accountNumber) throws CloseAccountException {
        lock.lock();
        for (Account account : this.accounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                if (account.getBalance().charAt(0) != '0') throw new CloseAccountException();
                this.accounts.remove(account);
                for (Account account1 : this.favoriteAccounts) {
                    if (account1.getAccountNumber().compareTo(accountNumber) == 0) {
                        this.favoriteAccounts.remove(account1);
                        break;
                    }
                }
                break;
            }
        }
        lock.unlock();
    }

    public void addFavoriteAccount(String accountNumber) throws DuplicateAccountNumberException {
        lock.lock();
        for (Account account : this.favoriteAccounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) throw new DuplicateAccountNumberException();
        }
        for (Account account : this.accounts) {
            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                this.favoriteAccounts.add(account);
                break;
            }
        }
        lock.unlock();
    }

}