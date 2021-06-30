package Server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientManager implements Runnable {
    Server server;
    Socket client;
    User currentUser;
    DataInputStream reader;
    PrintWriter writer;
    StringTokenizer x;

    public ClientManager(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            reader = new DataInputStream(client.getInputStream());
            writer = new PrintWriter(client.getOutputStream(), true);
            int command = 0;
            while (command != 15) {
                command = Integer.parseInt(reader.readLine());
                switch (command) {


                    //**************************************************دکمه ورود
                    case 1: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        boolean found = false;
                        String nationalId = x.nextToken();
                        String password = x.nextToken();
                        for (User user : Information.users) {
                            if (user.getNationalId().compareTo(nationalId) == 0 && user.getPassword().compareTo(password) == 0) {
                                found = true;
                                currentUser = user;
                            }
                        }
                        if (found) {
                            writer.println("1");
                            writer.println(currentUser.getNationalId() + "*" + currentUser.getName() + "*" + currentUser.getPassword() + "*" + currentUser.getPhoneNumber() + "*" + currentUser.getEmail() + "*" + currentUser.getAccounts().size() + "*" + currentUser.getFavoriteAccounts().size() + "*");
                            for (Account account : currentUser.getAccounts()) {
                                writer.println(account.getAccountNumber() + "*" + account.getAccountType() + "*" + account.getAlias() + "*");
                            }
                            for (Account account : currentUser.getFavoriteAccounts()) {
                                writer.println(account.getAccountNumber() + "*" + account.getAccountType() + "*" + account.getAlias() + "*");
                            }
                        } else writer.println("0");
                        break;
                    }


                    //**************************************************دکمه ثبت نام
                    case 2: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        boolean found = false;
                        String nationalId = x.nextToken();
                        String name = x.nextToken();
                        String password = x.nextToken();
                        String phoneNumber = x.nextToken();
                        String email = x.nextToken();
                        for (User user : Information.users) {
                            if (user.getNationalId().compareTo(nationalId) == 0) found = true;
                        }
                        if (!found) {
                            currentUser = new User(name, nationalId, password, phoneNumber, email);
                            Information.users.add(currentUser);
                            writer.println("1");
                        } else writer.println("0");
                        break;
                    }


                    //**************************************************دکمه ذخیره تغییرات
                    case 3: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        currentUser.setName(x.nextToken());
                        currentUser.setPassword(x.nextToken());
                        currentUser.setPhoneNumber(x.nextToken());
                        currentUser.setEmail(x.nextToken());
                        break;
                    }


                    //**************************************************دکمه افتتاح حساب
                    case 4: {
                        writer.println("6062-5611-5525-" + Account.count);
                        break;
                    }


                    //**************************************************دکمه افتتاح حساب
                    case 5: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String password = x.nextToken();
                        String accountType = x.nextToken();
                        String alias = x.nextToken();
                        Account newAccount = new Account(AccountType.valueOf(accountType), password);
                        newAccount.setAccountNumber(accountNumber);
                        newAccount.setAlias(alias);
                        currentUser.getAccounts().add(newAccount);
                        Information.accounts.add(newAccount);
                        break;
                    }


                    //**************************************************دکمه انتقال وجه
                    case 6: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String destination = x.nextToken();
                        String amount = x.nextToken();
                        String password = x.nextToken();
                        boolean checkPassword = false;
                        boolean checkDestination = false;
                        boolean checkBalance = false;
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0 && account.getPassword().compareTo(password) == 0) {
                                checkPassword = true;
                                for (Account account1 : Information.accounts) {
                                    if (account1.getAccountNumber().compareTo(destination) == 0) {
                                        checkDestination = true;
                                        try {
                                            account.moneyTransfer(account1, amount);
                                            checkBalance = true;
                                        } catch (Exception ignored) {
                                        }
                                    }
                                }
                            }
                        }
                        if (!checkPassword) writer.println("0");
                        else if (!checkDestination) writer.println("1");
                        else if (!checkBalance) writer.println("2");
                        else writer.println("3");
                        break;
                    }


                    //**************************************************دکمه موجودی
                    case 7: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) writer.println(account.getBalance() + "*");
                        }
                        break;
                    }


                    //**************************************************دکمه صورت حساب
                    case 8: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                writer.println(account.getTransactions().size() + "*");
                                for (Transaction transaction : account.getTransactions()) {
                                    writer.println(transaction.getDestination() + "*" + transaction.getAmount() + "*");
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه افزودن به حساب های منتخب
                    case 9: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        boolean found = false;
                        for (Account account : currentUser.getFavoriteAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) found = true;
                        }
                        if (found) writer.println("0");
                        else {
                            for (Account account : currentUser.getAccounts()) {
                                if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                    currentUser.getFavoriteAccounts().add(account);
                                    writer.println("1");
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه وام ها
                    case 10: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                writer.println(account.getLoans().size() + "*");
                                for (Loan loan : account.getLoans()) {
                                    writer.println(loan.getAmount() + "*" + loan.getMonths() + "*");
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه وام ها
                    case 11: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String amount = x.nextToken();
                        String month = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                account.getLoan(amount, Integer.parseInt(month));
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه پرداخت قبض
                    case 12: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String amount = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                try {
                                    account.payBill(amount);
                                    writer.println("1");
                                } catch (Exception e) {
                                    writer.println("0");
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه بستن حساب
                    case 13: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                if (account.getBalance().compareTo("0") == 0) {
                                    currentUser.getAccounts().remove(account);
                                    writer.println("1");
                                } else {
                                    writer.println("0");
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه بستن حساب در حالتی که حساب دارای موجودی است
                    case 14: {
                        StringTokenizer x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String destination = x.nextToken();
                        boolean found = false;
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                for (Account account1 : Information.accounts) {
                                    found = true;
                                    try {
                                        account.moneyTransfer(account1, account.getBalance());
                                    } catch (Exception ignored) {
                                    }
                                }

                            }
                        }
                        if (found) writer.println("1");
                        else writer.println("0");
                        break;
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
