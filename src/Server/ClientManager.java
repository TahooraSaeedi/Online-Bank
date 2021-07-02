package Server;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientManager implements Runnable {
    private final Server server;
    private final Socket client;
    private User currentUser;
    private DataInputStream reader;
    private PrintWriter writer;
    private StringTokenizer x;

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
                        x = new StringTokenizer(reader.readLine(), "*");
                        String nationalId = x.nextToken();
                        String password = x.nextToken();
                        try {
                            currentUser = Entrance.logIn(nationalId, password);
                            writer.println("1");
                            writer.println(currentUser.getNationalId() + "*" + currentUser.getName() + "*" + currentUser.getPassword() + "*" + currentUser.getPhoneNumber() + "*" + currentUser.getEmail() + "*" + currentUser.getAccounts().size() + "*" + currentUser.getFavoriteAccounts().size() + "*");
                            for (Account account : currentUser.getAccounts()) {
                                writer.println(account.getAccountNumber() + "*" + account.getAccountType() + "*" + account.getAlias() + "*");
                            }
                            for (Account account : currentUser.getFavoriteAccounts()) {
                                writer.println(account.getAccountNumber() + "*" + account.getAccountType() + "*" + account.getAlias() + "*");
                            }
                        } catch (UserNotFoundException e) {
                            writer.println("0");
                        }
                        break;
                    }


                    //**************************************************دکمه ثبت نام
                    case 2: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String nationalId = x.nextToken();
                        String name = x.nextToken();
                        String password = x.nextToken();
                        String phoneNumber = x.nextToken();
                        String email = x.nextToken();
                        try {
                            currentUser = Entrance.signUp(name, nationalId, password, phoneNumber, email);
                            writer.println("1");
                        } catch (DuplicateNationalId e) {
                            writer.println("0");
                        }
                        break;
                    }


                    //**************************************************دکمه ذخیره تغییرات
                    case 3: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        currentUser.setName(x.nextToken());
                        currentUser.setPassword(x.nextToken());
                        currentUser.setPhoneNumber(x.nextToken());
                        currentUser.setEmail(x.nextToken());
                        break;
                    }


                    //**************************************************دکمه افتتاح حساب
                    case 4: {
                        writer.println("6062-5611-5525-" + (1000 + Information.accounts.size()));
                        break;
                    }


                    //**************************************************دکمه افتتاح حساب
                    case 5: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String password = x.nextToken();
                        String accountType = x.nextToken();
                        String alias = x.nextToken();
                        Account newAccount = currentUser.addAccount(AccountType.valueOf(accountType), password);
                        newAccount.setAccountNumber(accountNumber);
                        newAccount.setAlias(alias);
                        break;
                    }


                    //**************************************************دکمه انتقال وجه
                    case 6: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String destination = x.nextToken();
                        String amount = x.nextToken();
                        String password = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                try {
                                    account.moneyTransfer(destination, amount, password);
                                    writer.println("3");
                                } catch (InvalidPasswordException e) {
                                    writer.println("0");
                                } catch (InvalidAccountNumberException e) {
                                    writer.println("1");
                                } catch (InsufficientFundsException e) {
                                    writer.println("2");
                                } catch (Exception ignored) {

                                }
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه موجودی
                    case 7: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                writer.println(account.getBalance() + "*");
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه صورت حساب
                    case 8: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                writer.println(account.getTransactions().size() + "*");
                                for (Transaction transaction : account.getTransactions()) {
                                    writer.println(transaction.getDestinationAccountNumber() + "*" + transaction.getAmount() + "*");
                                }
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه افزودن به حساب های منتخب
                    case 9: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        try {
                            currentUser.addFavoriteAccount(accountNumber);
                            writer.println("1");
                        } catch (DuplicateAccountNumberException e) {
                            writer.println("0");
                        }
                        break;
                    }


                    //**************************************************دکمه وام ها
                    case 10: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                writer.println(account.getLoans().size() + "*");
                                for (Loan loan : account.getLoans()) {
                                    writer.println(loan.getAmount() + "*" + loan.getMonths() + "*");
                                }
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه وام ها
                    case 11: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String amount = x.nextToken();
                        String month = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                account.getLoan(amount, Integer.parseInt(month));
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه پرداخت قبض
                    case 12: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String amount = x.nextToken();
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                try {
                                    account.payBill(amount);
                                    writer.println("1");
                                } catch (InsufficientFundsException e) {
                                    writer.println("0");
                                }
                                break;
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه بستن حساب
                    case 13: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        try {
                            currentUser.closeAccount(accountNumber);
                            writer.println("1");
                        } catch (CloseAccountException e) {
                            writer.println("0");
                        }
                        break;
                    }


                    //**************************************************دکمه بستن حساب در حالتی که حساب دارای موجودی است
                    case 14: {
                        x = new StringTokenizer(reader.readLine(), "*");
                        String accountNumber = x.nextToken();
                        String destination = x.nextToken();
                        boolean found = false;
                        for (Account account : currentUser.getAccounts()) {
                            if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                try {
                                    account.moneyTransfer(destination, account.getBalance(), account.getPassword());
                                    currentUser.closeAccount(accountNumber);
                                    writer.println("1");
                                } catch (InvalidAccountNumberException e) {
                                    writer.println("0");
                                } catch (Exception ignored) {

                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
