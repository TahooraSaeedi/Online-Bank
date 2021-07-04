package Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Client extends Application {
    Scene scene;

    Socket server;
    int port = 16999;
    String serverAddress = "Localhost";

    InputStream fromServer;
    OutputStream toServer;
    DataInputStream reader;
    PrintWriter writer;

    private String nationalId;
    private String name;
    private String password;
    private String phoneNumber;
    private String email;
    private String numOfAccount;
    private String numOfFavoriteAccount;

//    private String numberOfNewAccount; // شماره حساب ساخته شده اولیه
//    private String inventory; // موجودی
//    private String transaction = ""; // صورت حساب حساب
//    private String loan = ""; // وام های حساب

    HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
    int countAccount = 0; // تعداد حساب ها
    int checkAccount = 1; // جا به حا شدن روی  حساب ها

    HashMap<Integer, Account> favoriteAccounts = new HashMap<Integer, Account>();
    int countFavoriteAccount = 0; // تعداد حساب ها
    int checkFavoriteAccount = 0; // جا به جا شدن روی حساب خا

    int accountType = 1; // 1 == accounts  &&  2 == favoriteAccounts

    public Client() {
        try {
            server = new Socket(serverAddress, port);
            System.out.println("server accept client");

            fromServer = server.getInputStream();
            toServer = server.getOutputStream();

            reader = new DataInputStream(fromServer);
            writer = new PrintWriter(toServer, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        scene = new Scene(entrance(), 798, 570, Color.web("646464FF"));

        primaryStage.setResizable(false);
        primaryStage.setTitle("e-Bank");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    int entrance = 1; // sign in == 1  &&  sign up == 2

    public Pane entrance() {
        if (entrance == 1) {
            Pane signIn = new Pane();
            signIn.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Label welcome = new Label("«Welcome to the e-bank app»");
            welcome.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-translate-x: 355; -fx-translate-y: 38; -fx-text-fill: " + Theme.text1 + "; ");

            Button bSignIn = new Button("Login");// ورود سمت چپ
            bSignIn.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            Button bSingUp = new Button("Sign up");// ثبت نام سمت چپ
            bSingUp.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            TextField tFNationalId = new TextField();
            tFNationalId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFNationalId.setPromptText("National id");

            TextField tFPassword = new TextField();
            tFPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 380; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFPassword.setPromptText("Password");

            Button bSignInFinal = new Button("Login");// برای اتصال به سرور
            bSignInFinal.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 38; -fx-max-height: 38;-fx-translate-x: 475; -fx-translate-y: 494; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            bSingUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 2;
                    scene.setRoot(entrance());
                }
            }); // برای رفتن به صفحه ثبت نام

            bSignInFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!tFNationalId.getText().equals("") && !tFPassword.getText().equals(""))
                        try {
                            writer.println("1");
                            writer.println(tFNationalId.getText() + "*" + tFPassword.getText() + "*");

                            String answer = reader.readLine();
                            if (answer.equals("1")) {
                                StringTokenizer check = new StringTokenizer(reader.readLine(), "*");

                                nationalId = check.nextToken();
                                name = check.nextToken();
                                password = check.nextToken();
                                phoneNumber = check.nextToken();
                                email = check.nextToken();
                                numOfAccount = check.nextToken();
                                countAccount = Integer.parseInt(numOfAccount);
                                numOfFavoriteAccount = check.nextToken();
                                countFavoriteAccount = Integer.parseInt(numOfFavoriteAccount);

                                for (int i = 1; i <= Integer.parseInt(numOfAccount); i++) {
                                    StringTokenizer saveInformation = new StringTokenizer(reader.readLine(), "*");
                                    String accountNumber = saveInformation.nextToken();
                                    String accountType = saveInformation.nextToken();
                                    String alias = saveInformation.nextToken();
                                    Account newAccount = new Account(accountNumber, AccountType.valueOf(accountType), alias);
                                    countAccount = i;
                                    accounts.put(countAccount, newAccount);
                                }

                                for (int i = 0; i < Integer.parseInt(numOfFavoriteAccount); i++) {
                                    StringTokenizer saveInformation = new StringTokenizer(reader.readLine(), "*");
                                    String accountNumber = saveInformation.nextToken();
                                    String accountType = saveInformation.nextToken();
                                    String alias = saveInformation.nextToken();
                                    Account newAccount = new Account(accountNumber, AccountType.valueOf(accountType), alias);
                                    countFavoriteAccount = i;
                                    favoriteAccounts.put(countFavoriteAccount, newAccount);
                                }

                                scene.setRoot(homePage());
                            } else if (answer.equals("0")) {
                                tFNationalId.clear();
                                tFPassword.clear();

                                Pane newBackgroundPage = new Pane();
                                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                Pane errorSignIn = new Pane();
                                errorSignIn.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                                Label tFError = new Label("User not found");
                                tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                                Button bError = new Button("OK");
                                bError.setShape(new Circle(5));
                                bError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                                errorSignIn.getChildren().addAll(tFError, bError);
                                newBackgroundPage.getChildren().add(errorSignIn);
                                signIn.getChildren().add(newBackgroundPage);

                                bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        signIn.getChildren().remove(newBackgroundPage);
                                    }
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            });

            signIn.getChildren().addAll(welcome, bSignIn, bSingUp, tFNationalId, tFPassword, bSignInFinal);
            return signIn;
        } else if (entrance == 2) {
            Pane signUp = new Pane();
            signUp.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Label welcome = new Label("«Welcome to the e-bank app»");
            welcome.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40;  -fx-translate-x: 355; -fx-translate-y: 38; -fx-text-fill: " + Theme.text1 + "; ");

            Button bSignIn = new Button("Login");// دکمه سمت چپ
            bSignIn.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            Button bSingUp = new Button("Sign up");// دکمه سمت چپ
            bSingUp.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            TextField tFName = new TextField();
            tFName.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFName.setPromptText("Name");

            TextField tFNationalId = new TextField();
            tFNationalId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 570; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFNationalId.setPromptText("National id");

            TextField tFPassword = new TextField();
            tFPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFPassword.setPromptText("Password");

            TextField tFPhoneNumber = new TextField();
            tFPhoneNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 570; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFPhoneNumber.setPromptText("Phone number");

            TextField tFEmail = new TextField();
            tFEmail.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 380; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFEmail.setPromptText("Email");

            Button bSignUpFinal = new Button("Sign up");// برای اتصال به سرور
            bSignUpFinal.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 38; -fx-max-height: 38;-fx-translate-x: 475; -fx-translate-y: 494; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            bSignIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 1;
                    scene.setRoot(entrance());
                }
            });

            bSignUpFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!tFName.getText().equals("") && !tFNationalId.getText().equals("") && !tFPassword.getText().equals("") && !tFPhoneNumber.getText().equals("") && !tFEmail.getText().equals("")) {
                        try {
                            writer.println("2");
                            writer.println(tFNationalId.getText() + "*" + tFName.getText() + "*" + tFPassword.getText() + "*" + tFPhoneNumber.getText() + "*" + tFEmail.getText() + "*");

                            String answer = reader.readLine();
                            if (answer.equals("1")) {

                                /*nationalId = tFNationalId.getText();
                                name = tFName.getText();
                                password = tFPassword.getText();
                                phoneNumber = tFPhoneNumber.getText();
                                email = tFEmail.getText();*/

                                Pane newBackgroundPage = new Pane();
                                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                Pane checkEmail = new Pane();
                                checkEmail.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                                TextField tFCheckEmail = new TextField();
                                tFCheckEmail.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 15; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill-text-fill: " + Theme.text3 + "; ");
                                tFCheckEmail.setPromptText("Please enter your code");

                                Button bCheckEmail = new Button("OK");
                                bCheckEmail.setShape(new Circle(5));
                                bCheckEmail.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                                checkEmail.getChildren().addAll(tFCheckEmail, bCheckEmail);
                                newBackgroundPage.getChildren().add(checkEmail);
                                signUp.getChildren().add(newBackgroundPage);

                                bCheckEmail.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        if (!tFCheckEmail.getText().equals("")) {
                                            writer.println(tFCheckEmail.getText());
                                        }
                                        try {
                                            String answer = reader.readLine();
                                            if (answer.equals("1")) {
                                                nationalId = tFNationalId.getText();
                                                name = tFName.getText();
                                                password = tFPassword.getText();
                                                phoneNumber = tFPhoneNumber.getText();
                                                email = tFEmail.getText();
                                                scene.setRoot(homePage());
                                            } else if (answer.equals("-1")) {

                                                Pane newBackgroundPage = new Pane();
                                                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                                Pane checkEmail = new Pane();
                                                checkEmail.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                                                Label tFCheckEmail = new Label();
                                                tFCheckEmail.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");
                                                tFCheckEmail.setText("Incorrect code");

                                                Button bCheckEmail = new Button("OK");
                                                bCheckEmail.setShape(new Circle(5));
                                                bCheckEmail.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                                                checkEmail.getChildren().addAll(tFCheckEmail, bCheckEmail);
                                                newBackgroundPage.getChildren().add(checkEmail);
                                                signUp.getChildren().add(newBackgroundPage);

                                                bCheckEmail.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                                    @Override
                                                    public void handle(MouseEvent event) {
                                                        scene.setRoot(entrance());
                                                    }
                                                });
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            } else if (answer.equals("0") || answer.equals("-1")) {

                                Pane newBackgroundPage = new Pane();
                                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                Pane errorSignUp = new Pane();
                                errorSignUp.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                                Label tFError = new Label();
                                if (answer.equals("0")) {
                                    tFError.setText("The national id is\n   already exists");
                                } else if (answer.equals("-1")) {
                                    tFError.setText("Invalid email address");
                                }
                                tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text2 + "; -fx-alignment: center");

                                Button bError = new Button("OK");
                                bError.setShape(new Circle(5));
                                bError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                                errorSignUp.getChildren().addAll(tFError, bError);
                                newBackgroundPage.getChildren().add(errorSignUp);
                                signUp.getChildren().add(newBackgroundPage);

                                bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        tFNationalId.clear();
                                        tFName.clear();
                                        tFPassword.clear();
                                        tFPhoneNumber.clear();
                                        tFEmail.clear();
                                        signUp.getChildren().remove(newBackgroundPage);
                                    }
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            signUp.getChildren().addAll(welcome, bSignIn, bSingUp, tFName, tFNationalId, tFPassword, tFPhoneNumber, tFEmail, bSignUpFinal);
            return signUp;
        }
        return new Pane();
    }

    private Pane homePage() {

        Pane homePage = new Pane();
        homePage.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Image image = new Image("file:src/picture/Inkedphoto_2021-06-29_03-11-07_LI.jpg");
        Circle circle = new Circle(95);
        circle.setStyle("-fx-translate-x: 133; -fx-translate-y: 114");

        Button bUserProfile = new Button("Profile");
        bUserProfile.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 247; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bSetting = new Button("Setting");
        bSetting.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 323; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bAboutUs = new Button("About us");
        bAboutUs.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bExit = new Button("Exit");
        bExit.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Label lName = new Label("User");
        lName.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 310; -fx-translate-y: 38; -fx-text-fill: " + Theme.text1 + "; ");

        Button bTransaction = new Button("Transaction");//تراکنش
        bTransaction.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 15; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 323; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bInventory = new Button("Balance");
        bInventory.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 437; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bTransfer = new Button("Transfer");
        bTransfer.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 551; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bOpenAnAccount = new Button("New");
        bOpenAnAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 665; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bCloseAnAccount = new Button("Close");
        bCloseAnAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 323; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bBills = new Button("Bill");
        bBills.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 437; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bLoan = new Button("Loan");
        bLoan.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 551; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bAddInFavoriteAccount = new Button("Favorite");
        bAddInFavoriteAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 665; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        bUserProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //go line  ...
                scene.setRoot(userProfilePage());
            }
        });

        bSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        bAboutUs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        bExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Pane newBackgroundPage = new Pane();
                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                Pane successfulExit = new Pane();
                successfulExit.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                Label tFSuccessful = new Label("Do you want to exit?");
                tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + ";  -fx-alignment: center ");

                Button bSuccessful = new Button("YES");
                bSuccessful.setShape(new Circle(5));
                bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                Button bNo = new Button("NO");
                bNo.setStyle("-fx-font-family: '" + Theme.button2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 38; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                successfulExit.getChildren().addAll(tFSuccessful, bSuccessful, bNo);
                newBackgroundPage.getChildren().add(successfulExit);
                homePage.getChildren().add(newBackgroundPage);

                bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        writer.println("15");
                        System.exit(0);
                    }
                });

                bNo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        scene.setRoot(homePage());
                    }
                });
            }
        });

        bTransaction.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (countAccount == 0) {
                    Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
                    //این تیکه رو گفتی نزنم برای بقیه تابعش میکنی
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane errorTransaction = new Pane();
                    errorTransaction.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    Label tFError = new Label("No account available");
                    tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                    Button bError = new Button("OK");
                    bError.setShape(new Circle(5));
                    bError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                    bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    errorTransaction.getChildren().addAll(tFError, bError);
                    newBackgroundPage.getChildren().add(errorTransaction);
                    homePage.getChildren().add(newBackgroundPage);

                } else {
                    writer.println("8");
                    if (accountType == 1) {
                        writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                    } else if (accountType == 2) {
                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                    }

                    homePage.getChildren().add(transaction());
                }
            }
        });

        bInventory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (countAccount == 0) {
                    Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane errorInventory = new Pane();
                    errorInventory.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    TextArea tFError = new TextArea("هنوز حسابی نداری!!");
                    tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

                    Button bError = new Button("OK");
                    bError.setShape(new Circle(5));
                    bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

                    bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    errorInventory.getChildren().addAll(tFError, bError);
                    newBackgroundPage.getChildren().add(errorInventory);
                    homePage.getChildren().add(newBackgroundPage);

                } else {
                    writer.println("7");
                    if (accountType == 1) {
                        writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                    } else if (accountType == 2) {
                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                    }
                    homePage.getChildren().add(inventory());
                }
            }
        });

        bTransfer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(transfer());
            }
        });

        bOpenAnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("4");
                homePage.getChildren().add(openAnAccountPage());
            }
        });

        bCloseAnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (countAccount == 0) {
                    Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane errorCloseAnAccount = new Pane();
                    errorCloseAnAccount.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    TextArea tFError = new TextArea("هنوز حسابی نداری!!");
                    tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

                    Button bError = new Button("OK");
                    bError.setShape(new Circle(5));
                    bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

                    bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    errorCloseAnAccount.getChildren().addAll(tFError, bError);
                    newBackgroundPage.getChildren().add(errorCloseAnAccount);
                    homePage.getChildren().add(newBackgroundPage);
                } else {
                    try {
                        writer.println("13");
                        if (accountType == 1) {
                            writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                        } else if (accountType == 2) {
                            writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                        }

                        String answer = reader.readLine();
                        if (answer.equals("0")) {
                            homePage.getChildren().add(closeAnAccount());
                        } else if (answer.equals("1")) {
                            Pane newBackgroundPage = new Pane();
                            newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                            Pane successfulCloseAccount = new Pane();
                            successfulCloseAccount.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                            Label tFSuccessful = new Label();
                            tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-prompt-text-fill: " + Theme.text1 + "; -fx-alignment: center");
                            tFSuccessful.setText("Account closed\n   successfully");

                            Button bSuccessful = new Button("OK");
                            bSuccessful.setShape(new Circle(5));
                            bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                            successfulCloseAccount.getChildren().addAll(tFSuccessful, bSuccessful);
                            newBackgroundPage.getChildren().add(successfulCloseAccount);
                            homePage.getChildren().add(newBackgroundPage);

                            if (checkAccount == countAccount) {
                                accounts.remove(checkAccount);
                                checkAccount--;
                                countAccount--;
                            } else {
                                Account saveAccount = accounts.get(countAccount);
                                accounts.remove(countAccount);
                                countAccount--;
                                accounts.remove(checkAccount);
                                accounts.put(checkAccount, saveAccount);
                            }

                            bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    scene.setRoot(homePage());
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        bBills.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(bills());
            }
        });

        bLoan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (countAccount == 0) {

                    Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane errorLoan = new Pane();
                    errorLoan.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    TextArea tFError = new TextArea("هنوز حسابی نداری!!");
                    tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

                    Button bError = new Button("OK");
                    bError.setShape(new Circle(5));
                    bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

                    bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    errorLoan.getChildren().addAll(tFError, bError);
                    newBackgroundPage.getChildren().add(errorLoan);
                    homePage.getChildren().add(newBackgroundPage);
                } else {
                    writer.println("10");
                    if (accountType == 1) {
                        writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                    } else if (accountType == 2) {
                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                    }
                    homePage.getChildren().add(loan());
                }
            }
        }); // "10"

        bAddInFavoriteAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (countAccount == 0) {
                    Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane errorFavoriteAccount = new Pane();
                    errorFavoriteAccount.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    TextArea tFError = new TextArea("هنوز حسابی نداری!!");
                    tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

                    Button bError = new Button("OK");
                    bError.setShape(new Circle(5));
                    bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

                    bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    errorFavoriteAccount.getChildren().addAll(tFError, bError);
                    newBackgroundPage.getChildren().add(errorFavoriteAccount);
                    homePage.getChildren().add(newBackgroundPage);
                } else {
                    try {

                        writer.println("9");
                        if (accountType == 1) {
                            writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                        } else if (accountType == 2) {
                            writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                        }

                        String answer = reader.readLine();

                        Pane backgroundPage = new Pane();
                        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                        Pane successfulAddInFavoriteAccount = new Pane();
                        successfulAddInFavoriteAccount.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                        Label tFSuccessful = new Label();
                        tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");
                        if (answer.equals("0")) {
                            tFSuccessful.setText("The account already\n exists in favorite list");
                        } else if (answer.equals("1")) {
                            tFSuccessful.setText("The account added to\n      list of favorites");
                            countFavoriteAccount++;
                            favoriteAccounts.put(countFavoriteAccount, accounts.get(checkAccount));
                        }

                        Button bSuccessful = new Button("OK");
                        bSuccessful.setShape(new Circle(5));
                        bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 15; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                        bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                scene.setRoot(homePage());
                            }
                        });

                        successfulAddInFavoriteAccount.getChildren().addAll(tFSuccessful, bSuccessful);
                        backgroundPage.getChildren().add(successfulAddInFavoriteAccount);
                        homePage.getChildren().add(backgroundPage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }); // "9"

        homePage.getChildren().addAll(circle, bUserProfile, bSetting, bAboutUs, bExit,
                lName, showAccounts(),
                bTransaction, bInventory, bTransfer, bOpenAnAccount, bCloseAnAccount, bBills, bLoan, bAddInFavoriteAccount);
        return homePage;

    }

    private Pane showAccounts() {
        Pane showAccountsPage = new Pane();
        showAccountsPage.setStyle("-fx-background-color: " + Theme.back3 + "; -fx-min-width: 456; -fx-max-width: 456; -fx-min-height: 228; -fx-max-height: 228; -fx-translate-x: 304; -fx-translate-y: 152");

        Button bFavoriteAccount = new Button("Favorite accounts");
        bFavoriteAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 38; -fx-translate-y: -38; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bAllAccount = new Button("All accounts");
        bAllAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 266; -fx-translate-y: -38; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Circle imageLeft = new Circle(38, 114, 19);
        imageLeft.setFill(new ImagePattern(new Image("file:src/Client/picture/Left.png")));

        Label lInformationAccount = new Label();// اطلاعات از هش مپ حساب ها خوانده میشود

        if (accountType == 1) {
            if (countAccount != 0) {
                lInformationAccount.setText(accounts.get(checkAccount).getAlias() + "\n" +
                        accounts.get(checkAccount).getAccountType() + "\n" +
                        accounts.get(checkAccount).getAccountNumber());
            }
        } else if (accountType == 2) {
            if (countFavoriteAccount != 0) {
                lInformationAccount.setText(favoriteAccounts.get(checkFavoriteAccount).getAlias() + "\n" +
                        favoriteAccounts.get(checkFavoriteAccount).getAccountType() + "\n" +
                        favoriteAccounts.get(checkFavoriteAccount).getAccountNumber());
            }
        }

        lInformationAccount.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 25; -fx-font-weight: bold;  -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 76; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: " + "#FFFFFF; -fx-text-fill: #000000; ");

        Circle imageRight = new Circle(418, 114, 19);
        imageRight.setFill(new ImagePattern(new Image("file:src/Client/picture/Right.png")));

        bFavoriteAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                accountType = 2;
                checkFavoriteAccount = 1;
                scene.setRoot(homePage());
            }
        });

        bAllAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                accountType = 1;
                checkAccount = 1;
                for (int i = 1; i <= countAccount; i++) {
                    System.out.println(accounts.get(i).getAccountNumber());
                }
                scene.setRoot(homePage());
            }
        });

        imageLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (accountType == 1) {
                    if (checkAccount > 1) {
                        checkAccount--;
                        scene.setRoot(homePage());
                    }
                } else if (accountType == 2) {
                    if (checkFavoriteAccount > 1) {
                        checkFavoriteAccount--;
                        scene.setRoot(homePage());
                    }
                }
            }
        });

        imageRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (accountType == 1) {
                    if (checkAccount < countAccount) {
                        checkAccount++;
                        scene.setRoot(homePage());
                    }
                } else if (accountType == 2) {
                    if (checkFavoriteAccount < countFavoriteAccount) {
                        checkFavoriteAccount++;
                        scene.setRoot(homePage());
                    }
                }
            }
        });

        showAccountsPage.getChildren().addAll(bFavoriteAccount, bAllAccount, imageLeft, lInformationAccount, imageRight);
        return showAccountsPage;
    }

    private Pane userProfilePage() {


        Pane userProfilePage = new Pane();
        userProfilePage.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Button bChangeImage = new Button("Change photo");
        bChangeImage.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 456; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Label lName = new Label("Name:");
        lName.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 375; -fx-translate-y: 76; -fx-text-fill: " + Theme.text1 + "; ");// x - 5

        TextField tfName = new TextField();
        tfName.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 114; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tfName.setText(name);

        Label lNationalId = new Label("National id:");
        lNationalId.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 584; -fx-translate-y: 76; -fx-text-fill: " + Theme.text1 + "; ");// x - 5

        TextField tfNationalId = new TextField();
        tfNationalId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 551; -fx-translate-y: 114; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tfNationalId.setText(nationalId);
        tfNationalId.editableProperty().asObject().set(false);

        Label lPassword = new Label("Password:");
        lPassword.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 375; -fx-translate-y: 190; -fx-text-fill: " + Theme.text1 + "; ");// x - 5

        TextField tfPassword = new TextField();
        tfPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 228; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: +Theme.text3+; ");
        tfPassword.setText(password);

        Label lPhoneNumber = new Label("PhoneNumber:");
        lPhoneNumber.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 584; -fx-translate-y: 190; -fx-text-fill: " + Theme.text1 + "; ");// x - 5

        TextField tfPhoneNumber = new TextField();
        tfPhoneNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 551; -fx-translate-y: 228; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: +Theme.text3+; ");
        tfPhoneNumber.setText(phoneNumber);

        Label lEmail = new Label("Email:");
        lEmail.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 375; -fx-translate-y: 304; -fx-text-fill: " + Theme.text1 + "; ");// x - 5

        TextField tfEmail = new TextField();
        tfEmail.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 380; -fx-max-width: 380; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 342; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tfEmail.setText(email);

        Circle cGoHome = new Circle(532, 494, 38);
        cGoHome.setFill(new ImagePattern(new Image("file:src/Client/picture/Home76.png")));

        Button bSaveChanges = new Button("Save changes");
        bSaveChanges.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 608; -fx-translate-y: 456; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        bSaveChanges.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (!tfName.getText().equals("") && !tfPassword.getText().equals("") && !tfPhoneNumber.getText().equals("") && !tfEmail.getText().equals("")) {

                    writer.println("3");
                    writer.println(tfName.getText() + "*" + tfPassword.getText() + "*" + tfPhoneNumber.getText() + "*" + tfEmail.getText() + "*");
                    name = tfName.getText();
                    password = tfPassword.getText();
                    phoneNumber = tfPhoneNumber.getText();
                    email = tfEmail.getText();

                    Pane newBackgroundPage = new Pane();
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane successfulChanges = new Pane();
                    successfulChanges.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    Label tFSuccessful = new Label("      Profile was\nsuccessfully edited");
                    tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                    Circle cSuccessful = new Circle(133, 152, 19);
                    cSuccessful.setFill(new ImagePattern(new Image("file:src/Client/picture/Home38.png")));

                    successfulChanges.getChildren().addAll(tFSuccessful, cSuccessful);
                    newBackgroundPage.getChildren().add(successfulChanges);
                    userProfilePage.getChildren().add(newBackgroundPage);

                    cSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                }
            }
        });

        cGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });


        userProfilePage.getChildren().addAll(bChangeImage,
                lName, tfName, lNationalId, tfNationalId, lPassword, tfPassword, lPhoneNumber, tfPhoneNumber, lEmail, tfEmail,
                cGoHome, bSaveChanges);
        return userProfilePage;
    }

    private Pane openAnAccountPage() {
        String answer = "";
        try {
            answer = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");
        Pane openAnAccountPage = new Pane();
        openAnAccountPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

        TextField tFPassword = new TextField();
        tFPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFPassword.setPromptText("Password");

        TextField tFAccountNumber = new TextField();
        tFAccountNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFAccountNumber.setText(answer);
        tFAccountNumber.editableProperty().asObject().set(false);

        TextField tFAlias = new TextField();
        tFAlias.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFAlias.setPromptText("Alias");

        TextField tFAccountType = new TextField("Account type");
        tFAccountType.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 14; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFAccountType.editableProperty().asObject().set(false);

        TextField tFSaveAccountType = new TextField();
        tFSaveAccountType.setText("");

        Button bOpenAnAccount = new Button("Open a new account");
        bOpenAnAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        tFAccountType.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane accountTypePage = new Pane();
                accountTypePage.setStyle("-fx-background-color: " + Theme.button2 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 95");

                Pane newBackgroundPage = new Pane();
                newBackgroundPage.setStyle(" -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 0; -fx-translate-y: 0");

                Label text = new Label("Choose your account type");
                text.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 0; -fx-text-fill: " + Theme.text1 + ";  -fx-alignment: center");

                Button s_b = new Button("Seporde boland modat");
                s_b.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 38; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #000000");

                Button s_k = new Button("Seporde kootah modat");
                s_k.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 76; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #000000");

                Button gh_j = new Button("Gharzal hasane jari");
                gh_j.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 16; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 114; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #000000");

                Button gh_p = new Button("Gharzal hasane pasandaz");
                gh_p.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 152; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #000000");

                s_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tFAccountType.setText("Seporde boland modat");
                        tFSaveAccountType.setText("S_B");
                        openAnAccountPage.getChildren().remove(newBackgroundPage);
                    }
                });

                s_k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tFAccountType.setText("Seporde kootah modat");
                        tFSaveAccountType.setText("S_K");
                        openAnAccountPage.getChildren().remove(newBackgroundPage);
                    }
                });

                gh_j.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tFAccountType.setText("Gharzal hasane jari");
                        tFSaveAccountType.setText("GH_J");
                        openAnAccountPage.getChildren().remove(newBackgroundPage);
                    }
                });

                gh_p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tFAccountType.setText("Gharzal hasane pasandaz");
                        tFSaveAccountType.setText("GH_P");
                        openAnAccountPage.getChildren().remove(newBackgroundPage);
                    }
                });


                accountTypePage.getChildren().addAll(text, s_b, s_k, gh_j, gh_p);
                newBackgroundPage.getChildren().add(accountTypePage);
                openAnAccountPage.getChildren().add(newBackgroundPage);
            }
        });

        bOpenAnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!tFPassword.getText().equals("") && !tFAccountNumber.getText().equals("") && !tFSaveAccountType.getText().equals("")) {
                    System.out.println(tFPassword.getText() + "---" + tFAccountNumber.getText() + "---" + tFSaveAccountType.getText());
                    writer.println("5");
                    if (tFAlias.getText().equals("")) {
                        tFAlias.setText("+");
                    }
                    writer.println(tFAccountNumber.getText() + "*" + tFPassword.getText() + "*" + tFSaveAccountType.getText() + "*" + tFAlias.getText() + "*");

                    countAccount++;
                    System.out.println(countAccount);
                    accounts.put(countAccount, new Account(tFAccountNumber.getText(), AccountType.valueOf(tFSaveAccountType.getText()), tFAlias.getText()));

                    Pane backgroundPage = new Pane();
                    backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane successfulOpen = new Pane();
                    successfulOpen.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 95");

                    Label tFSuccessful = new Label("The new account has\n    been successfully");
                    tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                    Button bSuccessful = new Button("OK");
                    bSuccessful.setShape(new Circle(5));
                    bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                    successfulOpen.getChildren().addAll(tFSuccessful, bSuccessful);
                    backgroundPage.getChildren().add(successfulOpen);
                    openAnAccountPage.getChildren().add(backgroundPage);

                    bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                }
            }
        });

        openAnAccountPage.getChildren().addAll(tFPassword, tFAccountNumber, tFAlias, tFAccountType, bOpenAnAccount);
        backgroundPage.getChildren().addAll(openAnAccountPage);
        return backgroundPage;
    }

    private Pane transfer() {
        if (countAccount == 0) {
            Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
            newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Pane errorTransfer = new Pane();
            errorTransfer.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

            TextArea tFError = new TextArea("هنوز حسابی نداری!!");
            tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

            Button bError = new Button("OK");
            bError.setShape(new Circle(5));
            bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

            bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });

            errorTransfer.getChildren().addAll(tFError, bError);
            newBackgroundPage.getChildren().add(errorTransfer);

            return newBackgroundPage;
        } else {
            Pane backgroundPage = new Pane(); // برای گرفتن اکشن از دکمه های صفحه هوم
            backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Pane transferPage = new Pane();
            transferPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

            TextField tFDestinationAccountNumber = new TextField();
            tFDestinationAccountNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFDestinationAccountNumber.setPromptText("Destination");

            TextField tFSourceAccountNumber = new TextField();
            tFSourceAccountNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFSourceAccountNumber.editableProperty().asObject().set(false);

            if (accountType == 1) {
                tFSourceAccountNumber.setText(accounts.get(checkAccount).getAccountNumber());
            } else if (accountType == 2) {
                tFSourceAccountNumber.setText(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber());
            }

            TextField tFAccountPassword = new TextField();
            tFAccountPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFAccountPassword.setPromptText("Password");

            TextField tFTransferAmount = new TextField();
            tFTransferAmount.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFTransferAmount.setPromptText("Amount");

            Button bTransfer = new Button("Money transfer");
            bTransfer.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

            bTransfer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        writer.println("6");
                        if (accountType == 1) {
                            writer.println(accounts.get(checkAccount).getAccountNumber() + "*" + tFDestinationAccountNumber.getText() + "*" + tFTransferAmount.getText() + "*" + tFAccountPassword.getText() + "*");
                        } else if (accountType == 2) {
                            writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*" + tFDestinationAccountNumber.getText() + "*" + tFTransferAmount.getText() + "*" + tFAccountPassword.getText() + "*");
                        }
                        String answer = reader.readLine();

                        Pane newBackgroundPage2 = new Pane(); // برای اعلام نتیجه عملیات
                        newBackgroundPage2.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                        Pane successfulTransfer = new Pane();
                        successfulTransfer.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 95");

                        Label tFSuccessful = new Label();
                        tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + ";-fx-alignment: center");

                        Button bSuccessful = new Button("OK");
                        bSuccessful.setShape(new Circle(5));
                        bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; -fx-alignment: center");

                        bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                scene.setRoot(homePage());
                            }
                        });

                        if (answer.equals("0")) {
                            tFSuccessful.setText("   The account's\npassword is invalid");
                        } else if (answer.equals("1")) {
                            tFSuccessful.setText("The destination is\n         invalid");
                        } else if (answer.equals("2")) {
                            tFSuccessful.setText("The balance is not\nenough to transfer");
                        } else if (answer.equals("3")) {
                            tFSuccessful.setText("The money transfer\n     has been done");
                        }

                        successfulTransfer.getChildren().addAll(tFSuccessful, bSuccessful);
                        newBackgroundPage2.getChildren().add(successfulTransfer);
                        transferPage.getChildren().add(newBackgroundPage2);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            transferPage.getChildren().addAll(tFDestinationAccountNumber, tFSourceAccountNumber, tFAccountPassword, tFTransferAmount, bTransfer);
            backgroundPage.getChildren().add(transferPage);
            return backgroundPage;
        }
    } // code 6

    private Pane inventory() {
        StringTokenizer answer = null;
        try {
            answer = new StringTokenizer(reader.readLine(), "*");

        } catch (IOException e) {
            e.printStackTrace();
        }
        String inventory = answer.nextToken();

        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane inventoryPage = new Pane();
        inventoryPage.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label lInventory = new Label("Balance" + "\n" +
                inventory);
        lInventory.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 19; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 76;");

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        inventoryPage.getChildren().addAll(lInventory, bGoHome);
        backgroundPage.getChildren().add(inventoryPage);
        return backgroundPage;
    } // code 7

    private Pane transaction() {//صورت حساب تراکنش ها
        String transaction = "";
        StringTokenizer answer = null;
        try {
            answer = new StringTokenizer(reader.readLine(), "*");

            String numberOfTransaction = answer.nextToken();

            for (int i = 0; i < Integer.parseInt(numberOfTransaction); i++) {
                StringTokenizer newAnswer = new StringTokenizer(reader.readLine(), "*");
                String destination = newAnswer.nextToken();
                String amount = newAnswer.nextToken();
                transaction = transaction + "to: " + destination + " / Amount: " + amount + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane transactionPage = new Pane();
        transactionPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 456; -fx-max-height: 456; -fx-translate-x: 133; -fx-translate-y: 57");

        TextArea textArea = new TextArea();
        textArea.setText(transaction);
        textArea.setStyle(" -fx-min-width: 494; -fx-max-width: 494; -fx-min-height: 342; -fx-max-height: 342; -fx-translate-x: 19; -fx-translate-y: 19; -fx-font-family:" + Theme.font2 + "; -fx-font-size: 20;  -fx-text-fill: #000000;");
        textArea.editableProperty().asObject().set(false);

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 247; -fx-translate-y: 380;");

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        transactionPage.getChildren().addAll(textArea, bGoHome);
        backgroundPage.getChildren().add(transactionPage);
        return backgroundPage;
    }

    private Pane loan() {
        String loan = "";
        try {
            StringTokenizer answer = new StringTokenizer(reader.readLine(), "*");
            String amountOfLoan = answer.nextToken();
            for (int i = 0; i < Integer.parseInt(amountOfLoan); i++) {
                StringTokenizer newAnswer = new StringTokenizer(reader.readLine(), "*");
                String amount = newAnswer.nextToken();
                String time = newAnswer.nextToken();
                loan = loan + "Amount: " + amount + " / Months: " + time + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane loanPage = new Pane();
        loanPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 570; -fx-max-height: 570; -fx-translate-x: 133; -fx-translate-y: 0");

        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-text-fill: #000000; -fx-font-size: 20; -fx-font-family: " + Theme.font2 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 570; -fx-max-height: 570; -fx-translate-x: 0; -fx-translate-y: 0");
        textArea.setText(loan);
        textArea.editableProperty().asObject().set(false);

        TextField tFAmount = new TextField();
        tFAmount.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 76; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFAmount.setPromptText("Amount");

        TextField tFTime = new TextField();
        tFTime.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 190; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
        tFTime.setPromptText("Months");

        Button bGetLoan = new Button("Get Loan");
        bGetLoan.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 342; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 456;");

        bGetLoan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("11");
                writer.println(accounts.get(checkAccount).getAccountNumber() + "*" + tFAmount.getText() + "*" + tFTime.getText() + "*");

                Pane newBackgroundPage = new Pane();
                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                Pane successfulGetLoan = new Pane();
                successfulGetLoan.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                Label tFSuccessful = new Label("Getting a loan is\ndone successfully");
                tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-prompt-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                Button bSuccessful = new Button("OK");
                bSuccessful.setShape(new Circle(5));
                bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color:" + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                successfulGetLoan.getChildren().addAll(tFSuccessful, bSuccessful);
                newBackgroundPage.getChildren().add(successfulGetLoan);
                backgroundPage.getChildren().add(newBackgroundPage);

                bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        scene.setRoot(homePage());
                    }
                });
            }
        });

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        loanPage.getChildren().addAll(textArea, tFAmount, tFTime, bGetLoan, bGoHome);
        backgroundPage.getChildren().add(loanPage);
        return backgroundPage;
    }

    private Pane bills() {
        if (countAccount == 0) {
            Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
            newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Pane errorInventory = new Pane();
            errorInventory.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

            TextArea tFError = new TextArea("هنوز حسابی نداری!!");
            tFError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

            Button bError = new Button("OK");
            bError.setShape(new Circle(5));
            bError.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 10; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 104.5; -fx-translate-y: 123.5; -fx-background-radius: 15px; -fx-background-color: #A8DADC; -fx-text-fill: #000000; ");

            bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });

            errorInventory.getChildren().addAll(tFError, bError);
            newBackgroundPage.getChildren().add(errorInventory);
            return newBackgroundPage;
        } else {
            Pane backgroundPage = new Pane();
            backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Pane billsPage = new Pane();
            billsPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

            TextField tFBillId = new TextField();
            tFBillId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFBillId.setPromptText("Bill id");

            TextField tFPaymentId = new TextField();
            tFPaymentId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFPaymentId.setPromptText("Payment id");

            TextField tFAmount = new TextField();
            tFAmount.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 152; -fx-background-radius: 100px; -fx-background-color: " + Theme.field + "; -fx-prompt-text-fill: " + Theme.text3 + "; ");
            tFAmount.setPromptText("Amount");

            Button bPayingBill = new Button("Pay bill");
            bPayingBill.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

            Button bGoHome = new Button();
            bGoHome.setShape(new Circle(5));
            bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 418; -fx-translate-y: 266;");

            bPayingBill.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    writer.println("12");
                    if (accountType == 1) {
                        writer.println(accounts.get(checkAccount).getAccountNumber() + "*" + tFAmount.getText() + "*");
                    } else if (accountType == 2) {
                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*" + tFAmount.getText() + "*");
                    }
                    String answer = "";
                    try {
                        answer = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Pane newBackgroundPage = new Pane();
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane successfulPayBill = new Pane();
                    successfulPayBill.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    Label tFSuccessful = new Label();
                    tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                    if (answer.equals("0")) {
                        tFSuccessful.setText("The balance is not \nenough to pay bill");
                    } else if (answer.equals("1")) {
                        tFSuccessful.setText("The bill has payed");
                    }

                    Button bSuccessful = new Button("OK");
                    bSuccessful.setShape(new Circle(5));
                    bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                    successfulPayBill.getChildren().addAll(tFSuccessful, bSuccessful);
                    newBackgroundPage.getChildren().add(successfulPayBill);
                    backgroundPage.getChildren().add(newBackgroundPage);

                    bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });
                }
            });

            bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });

            billsPage.getChildren().addAll(tFBillId, tFPaymentId, tFAmount, bPayingBill, bGoHome);
            backgroundPage.getChildren().add(billsPage);
            return backgroundPage;
        }
    }

    private Pane closeAnAccount() {

        Pane backgroundPage = new Pane();// برای خانه
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane closeAnAccountPage = new Pane();
        closeAnAccountPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 494; -fx-max-height: 494; -fx-translate-x: 266; -fx-translate-y: 38");

        Label tAInventory = new Label();
        tAInventory.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text2 + "; -fx-alignment: center");
        tAInventory.setText("Balance" + "\n" + "???");


        TextField tFDestinationAccountNumber = new TextField();
        tFDestinationAccountNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 190; -fx-background-radius: 100px;-fx-background-color: " + Theme.field + " ; -fx-text-fill: " + Theme.text3 + "; ");
        tFDestinationAccountNumber.setPromptText("Destination");

        Button bCloseAccount = new Button("Close account");
        bCloseAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 399;");

        bCloseAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String answer = "";
                try {
                    writer.println("14");
                    if (accountType == 1) {
                        writer.println(accounts.get(checkAccount).getAccountNumber() + "*" + tFDestinationAccountNumber.getText() + "*");
                    } else if (accountType == 2) {
                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*" + tFDestinationAccountNumber.getText() + "*");
                    }

                    answer = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Pane newBackgroundPage = new Pane();
                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                Pane successfulCloseAccount = new Pane();
                successfulCloseAccount.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                Label tFSuccessful = new Label();
                tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-prompt-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                Button bSuccessful = new Button("OK");
                bSuccessful.setShape(new Circle(5));
                bSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                if (answer.equals("0")) {
                    tFSuccessful.setText("The destination is\n         invalid");
                } else if (answer.equals("1")) {
                    tFSuccessful.setText("Account closed\n   successfully");
                    if (checkAccount == countAccount) {
                        accounts.remove(checkAccount);
                        checkAccount--;
                        countAccount--;
                    } else {
                        Account saveAccount = accounts.get(countAccount);
                        accounts.remove(countAccount);
                        countAccount--;
                        accounts.remove(checkAccount);
                        accounts.put(checkAccount, saveAccount);
                    }
                }

                bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        scene.setRoot(homePage());
                    }
                });

                successfulCloseAccount.getChildren().addAll(tFSuccessful, bSuccessful);
                newBackgroundPage.getChildren().add(successfulCloseAccount);
                backgroundPage.getChildren().add(newBackgroundPage);

            }
        });

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        closeAnAccountPage.getChildren().addAll(tAInventory, tFDestinationAccountNumber, bCloseAccount, bGoHome);
        backgroundPage.getChildren().add(closeAnAccountPage);
        return backgroundPage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}