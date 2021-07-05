package Server;

import java.util.StringTokenizer;
import java.net.Socket;
import java.io.*;

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
                            //----------------------------------------
                            //AddPhoto.Send
                            //----------------------------------------
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
                            User now = Entrance.signUp(name, nationalId, password, phoneNumber, email);
                            int code = (int) (Math.random() * 1000) + 1;
                            Entrance.sendEmail(email, code);
                            writer.println("1");
                            String newCode = reader.readLine();
                            if (code != Integer.parseInt(newCode)) throw new InvalidEmailAddressException();
                            currentUser = now;
                            Information.users.add(currentUser);
                            writer.println("1");
                        } catch (DuplicateNationalIdException e) {
                            writer.println("0");
                        } catch (InvalidEmailAddressException e) {
                            writer.println("-1");
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
                        //----------------------------------------
                        //AddPhoto.Receive
                        //----------------------------------------
                        break;
                    }


                    //**************************************************دکمه افتتاح حساب
                    case 4: {
                        String number;
                        if (Information.accounts.size() != 0) {
                            number = Information.accounts.get(Information.accounts.size() - 1).getAccountNumber().substring(10);
                        } else number = "999";
                        int num = Integer.parseInt(number);
                        writer.println("6062-5525-" + (num + 1));
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
                                    writer.println(transaction.getOriginAccountNumber() + "*" + transaction.getDestinationAccountNumber() + "*" + transaction.getAmount() + "*");
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
                            for (Account account : currentUser.getAccounts()) {
                                if (account.getAccountNumber().compareTo(accountNumber) == 0) {
                                    writer.println(account.getBalance());
                                    break;
                                }
                            }
                        }
                        break;
                    }


                    //**************************************************دکمه بستن حساب در حالتی که حساب دارای موجودی است
                    case 14: {
                        String accountNumber = reader.readLine(); //شماره حساب مبدا
                        String destination = reader.readLine(); //شماره حساب مقصد
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


                    //**************************************************دکمه ورود ادمین
                    case 16: {
                        String adminPassword = reader.readLine();
                        if (adminPassword.compareTo(Information.adminPassword) == 0) { //رمز ادمین صحیح باشد.
                            writer.println("1");
                            writer.println(Information.users.size() + "");
                            for (User user : Information.users) {
                                writer.println(user.getName() + "*" + user.getNationalId() + "*");
                            }
                            String nationalId = reader.readLine();
                            boolean found = false;
                            for (User user : Information.users) {
                                if (user.getNationalId().compareTo(nationalId) == 0) {
                                    writer.println("1");
                                    writer.println(user.getPassword() + "");
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) writer.println("0");
                            //ازینجا به بعدم که خودش میره تو کد یک
                        } else writer.println("0"); //رمز ادمین صحیح نباشد.
                        break;
                    }


                }
            }
            Information.sendInformation();
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