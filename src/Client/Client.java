package Client;

import javafx.scene.paint.ImagePattern;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;

import java.util.StringTokenizer;

import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.HashMap;
import java.net.Socket;
import java.io.*;

public class Client extends Application {
    Scene scene;

    Socket server;
    int port = 16999;
    String serverAddress = "151.245.40.218";

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

    private final String numberPattern = "[0-9]*";
    private final String namePattern = "[a-zA-Z]*";
    private final String accountPattern = "[0-9-]*";
    private final String phoneNumberPattern = "[0-9]{11}";
    private final String emailPattern = "[a-zA-Z._'0-9-]*[@]{1}[a-zA-Z]*.com";

    HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
    int countAccount = 0; // تعداد حساب ها
    int checkAccount = 1; // جا به حا شدن روی  حساب ها

    HashMap<Integer, Account> favoriteAccounts = new HashMap<Integer, Account>();
    int countFavoriteAccount = 0; // تعداد حساب ها
    int checkFavoriteAccount = 1; // جا به جا شدن روی حساب خا

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
        scene = new Scene(entrance(), 798, 570);

        primaryStage.getIcons().add(new Image("file:src/Client/picture/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("e-Bank");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    int entrance = 1; // sign in == 1  &&  sign up == 2

    private Pane entrance() {
        if (entrance == 1) {
            Pane signIn = new Pane();
            signIn.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Label welcome = new Label("«Welcome to the e-bank app»");
            welcome.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-translate-x: 355; -fx-translate-y: 38; -fx-text-fill: " + Theme.text1 + "; ");

            Button bSignIn = new Button("Login");// ورود سمت چپ
            bSignIn.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            Button bSingUp = new Button("Sign up");// ثبت نام سمت چپ
            bSingUp.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 57; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

            Rectangle r = new Rectangle(190, 152);
            r.setFill(new ImagePattern(new Image("file:src/Client/picture/Logo.png")));
            r.setStyle("-fx-translate-x: 437; -fx-translate-y: 95");

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
                    if (!tFNationalId.getText().equals("") && !tFPassword.getText().equals("") && tFNationalId.getText().matches(numberPattern) && tFPassword.getText().matches(numberPattern)) {
                        try {
                            writer.println("1");
                            writer.println(tFNationalId.getText() + "*" + tFPassword.getText() + "*");

                            String answer = reader.readLine();
                            if (answer.equals("1")) {
                                helpEntrance();
                            } else if (answer.equals("0")) {
                                tFNationalId.clear();
                                tFPassword.clear();

                                Pane newBackgroundPage = new Pane();
                                newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                Pane errorSignIn = new Pane();
                                errorSignIn.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                                Label tFError = new Label("User not found");
                                tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                                Button bError = new Button();
                                bError.setShape(new Circle(5));
                                bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

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
                    } else {
                        signIn.getChildren().add(invalidInput1());
                    }
                }
            });

            signIn.getChildren().addAll(welcome, bSignIn, bSingUp, r, tFNationalId, tFPassword, bSignInFinal);
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
                    if (!tFName.getText().equals("") && !tFNationalId.getText().equals("") && !tFPassword.getText().equals("") && !tFPhoneNumber.getText().equals("") && !tFEmail.getText().equals("") && tFName.getText().matches(namePattern) && tFNationalId.getText().matches(numberPattern) && tFPassword.getText().matches(numberPattern) && tFPhoneNumber.getText().matches(phoneNumberPattern) && tFEmail.getText().matches(emailPattern)) {
                        try {
                            writer.println("2");
                            writer.println(tFNationalId.getText() + "*" + tFName.getText() + "*" + tFPassword.getText() + "*" + tFPhoneNumber.getText() + "*" + tFEmail.getText() + "*");

                            String answer = reader.readLine();
                            if (answer.equals("1")) {

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

                                                Button bCheckEmail = new Button();
                                                bCheckEmail.setShape(new Circle(5));
                                                bCheckEmail.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

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

                                Button bError = new Button();
                                bError.setShape(new Circle(5));
                                if (answer.equals("0"))
                                    bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");
                                if (answer.equals("-1"))
                                    bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

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
                    } else {
                        signUp.getChildren().add(invalidInput1());
                    }
                }
            });
            signUp.getChildren().addAll(welcome, bSignIn, bSingUp, tFName, tFNationalId, tFPassword, tFPhoneNumber, tFEmail, bSignUpFinal);
            return signUp;
        }
        return new Pane();
    }

    private void helpEntrance() {
        StringTokenizer check = null;
        try {
            check = new StringTokenizer(reader.readLine(), "*");
            nationalId = check.nextToken();
            name = check.nextToken();
            password = check.nextToken();
            phoneNumber = check.nextToken();
            email = check.nextToken();
            numOfAccount = check.nextToken();
            numOfFavoriteAccount = check.nextToken();

            for (int i = 1; i <= Integer.parseInt(numOfAccount); i++) {
                StringTokenizer saveInformation = new StringTokenizer(reader.readLine(), "*");
                String accountNumber = saveInformation.nextToken();
                String accountType = saveInformation.nextToken();
                String alias = saveInformation.nextToken();
                Account newAccount = new Account(accountNumber, AccountType.valueOf(accountType), alias);
                countAccount = i;
                accounts.put(countAccount, newAccount);
            }

            for (int i = 1; i <= Integer.parseInt(numOfFavoriteAccount); i++) {
                StringTokenizer saveInformation = new StringTokenizer(reader.readLine(), "*");
                String accountNumber = saveInformation.nextToken();
                String accountType = saveInformation.nextToken();
                String alias = saveInformation.nextToken();
                Account newAccount = new Account(accountNumber, AccountType.valueOf(accountType), alias);
                countFavoriteAccount = i;
                favoriteAccounts.put(countFavoriteAccount, newAccount);
            }

            scene.setRoot(homePage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pane homePage() {

        Pane homePage = new Pane();
        homePage.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Circle cProfile = new Circle(95);
        cProfile.setFill(new ImagePattern(new Image("file:src/Client/picture/Profile.png")));
        cProfile.setStyle("-fx-translate-x: 133; -fx-translate-y: 114");

        Button bUserProfile = new Button("Profile");
        bUserProfile.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 247; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bSetting = new Button("Setting");
        bSetting.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 323; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bAboutUs = new Button("About us");
        bAboutUs.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Button bExit = new Button("Exit");
        bExit.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        Label lName = new Label(name);
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
                scene.setRoot(userProfile());
            }
        });

        bSetting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(setting());
            }
        });

        bAboutUs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(aboutUs());
            }
        });

        bExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(exit());
            }
        });

        bTransaction.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(transaction());
            }
        });

        bInventory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(inventory());
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
                homePage.getChildren().add(openAnAccount());
            }
        });

        bCloseAnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(closeAnAccount());
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
                homePage.getChildren().add(loan());
            }
        }); // "10"

        bAddInFavoriteAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homePage.getChildren().add(addInFavoriteAccount());
            }
        }); // "9"

        homePage.getChildren().addAll(cProfile, bUserProfile, bSetting, bAboutUs, bExit,
                lName, showAccounts(),
                bTransaction, bInventory, bTransfer, bOpenAnAccount, bCloseAnAccount, bBills, bLoan, bAddInFavoriteAccount);
        return homePage;

    }

    private Pane userProfile() {
        Pane userProfilePage = new Pane();
        userProfilePage.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Circle cProfile = new Circle(114);
        cProfile.setFill(new ImagePattern(new Image("file:src/Client/picture/Profile.png")));
        cProfile.setStyle("-fx-translate-x: 133; -fx-translate-y: 171");

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

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 494; -fx-translate-y: 456; ");

        Button bSaveChanges = new Button("Save changes");
        bSaveChanges.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 608; -fx-translate-y: 456; -fx-background-radius: 15px; -fx-background-color: " + Theme.button1 + "; -fx-text-fill: " + Theme.text2 + "; ");

        bSaveChanges.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (!tfName.getText().equals("") && !tfPassword.getText().equals("") && !tfPhoneNumber.getText().equals("") && !tfEmail.getText().equals("") && tfName.getText().matches(namePattern) && tfPassword.getText().matches(numberPattern) && tfPhoneNumber.getText().matches(phoneNumberPattern) && tfEmail.getText().matches(emailPattern)) {

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

                } else {
                    userProfilePage.getChildren().add(invalidInput2());
                }
            }
        });

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });


        userProfilePage.getChildren().addAll(cProfile, bChangeImage,
                lName, tfName, lNationalId, tfNationalId, lPassword, tfPassword, lPhoneNumber, tfPhoneNumber, lEmail, tfEmail,
                bGoHome, bSaveChanges);
        return userProfilePage;
    }

    private Pane setting() {
        Pane newBackgroundPage = new Pane();
        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane setting = new Pane();
        setting.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 228; -fx-max-height: 228; -fx-translate-x: 266; -fx-translate-y: 171");

        Label lSetting = new Label("Choose your theme");
        lSetting.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 25; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 0; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

        Button themeDefault = new Button("Default");
        themeDefault.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 36; -fx-max-height: 36; -fx-translate-x: 2; -fx-translate-y: 38; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button themeDark = new Button("Dark");
        themeDark.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 36; -fx-max-height: 36; -fx-translate-x: 2; -fx-translate-y: 76; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button themeLight = new Button("Light");
        themeLight.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 36; -fx-max-height: 36; -fx-translate-x: 2; -fx-translate-y: 114; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button adminUsers = new Button("Admin (Users)");
        adminUsers.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 36; -fx-max-height: 36; -fx-translate-x: 2; -fx-translate-y: 152; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button adminAccounts = new Button("Admin (Accounts)");
        adminAccounts.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 36; -fx-max-height: 36; -fx-translate-x: 2; -fx-translate-y: 190; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        themeDefault.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Theme.theme = 1;
                Theme.changeTheme();
                scene.setRoot(homePage());
            }
        });

        themeDark.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Theme.theme = 2;
                Theme.changeTheme();
                scene.setRoot(homePage());
            }
        });

        themeLight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Theme.theme = 3;
                Theme.changeTheme();
                scene.setRoot(homePage());
            }
        });

        adminUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("15");
                Pane newBackgroundPage2 = new Pane();
                newBackgroundPage2.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                Pane checkAdmin = new Pane();
                checkAdmin.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                TextField tFPassword = new TextField();
                tFPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 2; -fx-translate-y: 19; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center ");
                tFPassword.setPromptText("Password");

                Button bSend = new Button("SEND");
                bSend.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 114; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                bSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String answer;
                        String numberOfUsers;
                        String users = "";
                        if (!tFPassword.getText().equals("") && tFPassword.getText().matches(numberPattern)) {
                            writer.println(tFPassword.getText());
                            try {

                                answer = reader.readLine();
                                if (answer.equals("0")) {
                                    newBackgroundPage.getChildren().add(invalidInput2());
                                } else {
                                    numberOfUsers = reader.readLine();
                                    for (int i = 0; i < Integer.parseInt(numberOfUsers); i++) {
                                        StringTokenizer save = new StringTokenizer(reader.readLine(), "*");
                                        String name = save.nextToken();
                                        String nationalId = save.nextToken();
                                        users = users + "Name :" + name + " / National id :" + nationalId + "\n";
                                    }

                                    Pane newBackgroundPage3 = new Pane();
                                    newBackgroundPage3.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                    Pane informationPage = new Pane();
                                    informationPage.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 190");

                                    TextArea information = new TextArea(users );
                                    information.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 342; -fx-max-width: 342; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 171; -fx-translate-y: 19; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: #000000; -fx-alignment: center ");
                                    information.editableProperty().asObject().set(false);

                                    TextField tFNationId = new TextField();
                                    tFNationId.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 133; -fx-max-width: 133; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center ");
                                    tFNationId.setPromptText("NationID");

                                    Button bSendFinal = new Button("SEND");
                                    bSendFinal.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 95; -fx-max-width: 95; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 38; -fx-translate-y: 114; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                                    bSendFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            if (!tFNationId.getText().equals("") && tFNationId.getText().matches(numberPattern)) {
                                                writer.println(tFNationId.getText());
                                            }
                                            try {
                                                String answer2 = reader.readLine();
                                                if (answer2.equals("0")) {
                                                    newBackgroundPage.getChildren().add(invalidInput2());
                                                } else {
                                                    String password = reader.readLine();
                                                    writer.println("1");
                                                    writer.println(tFNationId.getText() + "*" + password + "*");
                                                    emptyInformation();
                                                    String answer = reader.readLine();
                                                    helpEntrance();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    informationPage.getChildren().addAll(information, tFNationId, bSendFinal);
                                    newBackgroundPage3.getChildren().add(informationPage);
                                    newBackgroundPage.getChildren().add(newBackgroundPage3);

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                checkAdmin.getChildren().addAll(tFPassword, bSend);
                newBackgroundPage2.getChildren().add(checkAdmin);
                newBackgroundPage.getChildren().add(newBackgroundPage2);
            }
        });

        adminAccounts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("16");
                Pane newBackgroundPage4 = new Pane();
                newBackgroundPage4.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                Pane checkAdmin1 = new Pane();
                checkAdmin1.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                TextField tFPassword = new TextField();
                tFPassword.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 262; -fx-max-width: 262; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 2; -fx-translate-y: 19; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center ");
                tFPassword.setPromptText("Password");

                Button bSend = new Button("SEND");
                bSend.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 95; -fx-translate-y: 114; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                bSend.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String answer = "" ;
                        String numberOfAccounts = "" ;
                        String saveAccounts = "" ;
                        writer.println(tFPassword.getText());
                        try {
                            answer = reader.readLine();
                            if (answer.equals("0")){
                                newBackgroundPage.getChildren().add(invalidInput2());
                            } else {
                                numberOfAccounts = reader.readLine();
                                for (int i = 0; i < Integer.parseInt(numberOfAccounts); i++) {
                                    StringTokenizer save = new StringTokenizer(reader.readLine(), "*");
                                    String accountNumber = save.nextToken();
                                    String accountPassword = save.nextToken();
                                    saveAccounts = saveAccounts + "accountNumber: " + accountNumber + " / pass: " + accountPassword + "\n";
                                }
                                Pane newBackgroundPage5 = new Pane();
                                newBackgroundPage5.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                                Pane informationAccountPage = new Pane();
                                informationAccountPage.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 190");

                                TextArea informationAccount = new TextArea(saveAccounts);
                                informationAccount.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 399; -fx-max-width: 399; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 114; -fx-translate-y: 19; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: #000000; -fx-alignment: center ");
                                informationAccount.editableProperty().asObject().set(false);

                                Button bGoHome = new Button();
                                bGoHome.setShape(new Circle(5));
                                bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 19; -fx-translate-y: 57; ");

                                bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        scene.setRoot(homePage());
                                    }
                                });

                                informationAccountPage.getChildren().addAll(informationAccount, bGoHome);
                                newBackgroundPage5.getChildren().add(informationAccountPage);
                                newBackgroundPage.getChildren().add(newBackgroundPage5);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                checkAdmin1.getChildren().addAll(tFPassword, bSend);
                newBackgroundPage4.getChildren().add(checkAdmin1);
                newBackgroundPage.getChildren().add(newBackgroundPage4);
            }
        });

        setting.getChildren().addAll(lSetting, themeDefault, themeDark, themeLight, adminUsers, adminAccounts);
        newBackgroundPage.getChildren().add(setting);

        return newBackgroundPage;
    }

    private Pane aboutUs() {


        Pane pAboutUs = new Pane();
        //آدرس این بکگراند رو بر مبنای تم چیزش کن لطفا
        pAboutUs.setStyle("-fx-background-image: url(\"file:src/Client/picture/back" + Theme.theme + ".png\"); -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Label lAboutUs = new Label("About us");
        lAboutUs.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 150; -fx-font-weight: bold; -fx-translate-x: -66.5; -fx-translate-y: 0; -fx-text-fill: " + Theme.text1 + "; -fx-rotate: -90deg; -fx-max-height: 570; -fx-min-height: 570; -fx-tab-max-width: 133; -fx-min-width: 133; -fx-alignment: center");

        Label lTitle = new Label("You can connect us through following ways\n                  and share your ideas");
        lTitle.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 35; -fx-font-weight: bold; -fx-translate-x: 266; -fx-translate-y: 38; -fx-text-fill: " + Theme.button2 + "; -fx-rotate: 0; -fx-max-width: 532; -fx-min-width: 532; -fx-alignment: center");

        Label lMhmd = new Label("Mohammadreza Rasi");
        lMhmd.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-translate-x: 247; -fx-translate-y: 190; -fx-text-fill: " + "#000000" + "; -fx-rotate: 0; -fx-max-width: 266; -fx-min-width: 266; -fx-alignment: center");

        Label lThr = new Label("Tahoora Saeedi");
        lThr.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-translate-x: 480; -fx-translate-y: 190; -fx-text-fill: " + "#000000" + "; -fx-rotate: 0; -fx-max-width: 266; -fx-min-width: 266; -fx-alignment: center");

        //زحمت آدرس اینم بکش
        Image iTel = new Image("file:src/Client/picture/tel.png");
        ImageView ivTel1 = new ImageView(iTel);
        ivTel1.setStyle("-fx-font-size: 38; -fx-translate-x: 275.5; -fx-translate-y: 266; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        ImageView ivTel2 = new ImageView(iTel);
        ivTel2.setStyle("-fx-font-size: 38; -fx-translate-x: 541.5; -fx-translate-y: 266; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        //و آدرس این
        Image iInsta = new Image("file:src/Client/picture/insta.png");
        ImageView ivInsta1 = new ImageView(iInsta);
        ivInsta1.setStyle("-fx-font-size: 38; -fx-translate-x: 275.5; -fx-translate-y: 342; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        ImageView ivInsta2 = new ImageView(iInsta);
        ivInsta2.setStyle("-fx-font-size: 38; -fx-translate-x: 541.5; -fx-translate-y: 342; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        //و آدرس این یکی
        Image iEmail = new Image("file:src/Client/picture/email.png");
        ImageView ivEmail1 = new ImageView(iEmail);
        ivEmail1.setStyle("-fx-font-size: 38; -fx-translate-x: 275.5; -fx-translate-y: 418; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        ImageView ivEmail2 = new ImageView(iEmail);
        ivEmail2.setStyle("-fx-font-size: 38; -fx-translate-x: 541.5; -fx-translate-y: 418; -fx-min-width: 266; -fx-max-width: 266; -fx-alignment: center");

        Label lTel = new Label("   @mhmdrzrs                            @ltahooral");
        lTel.setStyle("-fx-font-size: 30; -fx-translate-x: 300; -fx-translate-y: 266; -fx-font-family: " + Theme.font1 + "; -fx-text-fill: #000000");

        Label lInsta = new Label("   @mhmdrz___rs                         @_ltahooral_");
        lInsta.setStyle("-fx-font-size: 30; -fx-translate-x: 300; -fx-translate-y: 342; -fx-font-family: " + Theme.font1 + "; -fx-text-fill: #000000");

        Label lEmail = new Label("    mhmdrzrasi@gmail.com                 tahoora.saeedi@gmail.com");
        lEmail.setStyle("-fx-font-size: 25; -fx-translate-x: 300; -fx-translate-y: 418; -fx-font-family: " + Theme.font1 + "; -fx-text-fill: #000000");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 494; -fx-translate-y: 465.5; ");

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });


        pAboutUs.getChildren().addAll(lAboutUs, lTitle, lMhmd, lThr, ivTel1, ivTel2, ivInsta1, ivInsta2, ivEmail1, ivEmail2, lTel, lInsta, lEmail, bGoHome);
        return pAboutUs;
    }

    private Pane exit() {

        Pane newBackgroundPage = new Pane();
        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane successfulExit = new Pane();
        successfulExit.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label tFSuccessful = new Label("Do you want to exit?");
        tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + ";  -fx-alignment: center ");

        Button bYes = new Button("YES");
        bYes.setShape(new Circle(5));
        bYes.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 152; -fx-translate-y: 114; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bNo = new Button("NO");
        bNo.setShape(new Circle(5));
        bNo.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 57; -fx-max-width: 57; -fx-min-height: 57; -fx-max-height: 57; -fx-translate-x: 57; -fx-translate-y: 114; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        bYes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("17");
                try {
                    writer.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        bNo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        successfulExit.getChildren().addAll(tFSuccessful, bYes, bNo);
        newBackgroundPage.getChildren().add(successfulExit);
        return newBackgroundPage;
    }

    private Pane showAccounts() {
        Pane showAccountsPage = new Pane();
        showAccountsPage.setStyle("-fx-background-color: " + Theme.back3 + "; -fx-min-width: 456; -fx-max-width: 456; -fx-min-height: 228; -fx-max-height: 228; -fx-translate-x: 304; -fx-translate-y: 152");

        Button bFavoriteAccount = new Button("Favorite accounts");
        bFavoriteAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 38; -fx-translate-y: -39; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bAllAccount = new Button("All accounts");
        bAllAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 266; -fx-translate-y: -39; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Circle imageLeft = new Circle(38, 114, 19);
        imageLeft.setFill(new ImagePattern(new Image("file:src/Client/picture/Left.png")));

        Label lInformationAccount = new Label();// اطلاعات از هش مپ حساب ها خوانده میشود

        if (accountType == 1) {
            if (countAccount != 0) {
                lInformationAccount.setText(accounts.get(checkAccount).getAlias() + "\n" +
                        accounts.get(checkAccount).getAccountType() + "\n" +
                        accounts.get(checkAccount).getAccountNumber());
            } else {
                lInformationAccount.setText("");
            }
        } else if (accountType == 2) {
            if (countFavoriteAccount != 0) {
                lInformationAccount.setText(favoriteAccounts.get(checkFavoriteAccount).getAlias() + "\n" +
                        favoriteAccounts.get(checkFavoriteAccount).getAccountType() + "\n" +
                        favoriteAccounts.get(checkFavoriteAccount).getAccountNumber());
            } else {
                lInformationAccount.setText("");
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

    private Pane openAnAccount() {

        writer.println("4");
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
        bOpenAnAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 266; ");

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
                if (!tFPassword.getText().equals("") && !tFAccountNumber.getText().equals("") && !tFSaveAccountType.getText().equals("") && tFPassword.getText().matches(numberPattern)) {
                    writer.println("5");
                    if (tFAlias.getText().equals("")) {
                        tFAlias.setText("+");
                    }
                    writer.println(tFAccountNumber.getText() + "*" + tFPassword.getText() + "*" + tFSaveAccountType.getText() + "*" + tFAlias.getText() + "*");

                    checkAccount = 1;
                    countAccount++;
                    accounts.put(countAccount, new Account(tFAccountNumber.getText(), AccountType.valueOf(tFSaveAccountType.getText()), tFAlias.getText()));

                    Pane backgroundPage = new Pane();
                    backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane successfulOpen = new Pane();
                    successfulOpen.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 95");

                    Label tFSuccessful = new Label("The new account has\n    been successfully");
                    tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                    Button bSuccessful = new Button();
                    bSuccessful.setShape(new Circle(5));
                    bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");

                    successfulOpen.getChildren().addAll(tFSuccessful, bSuccessful);
                    backgroundPage.getChildren().add(successfulOpen);
                    openAnAccountPage.getChildren().add(backgroundPage);

                    bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                } else {
                    backgroundPage.getChildren().add(invalidInput2());
                }
            }
        });

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        openAnAccountPage.getChildren().addAll(tFPassword, tFAccountNumber, tFAlias, tFAccountType, bOpenAnAccount, bGoHome);
        backgroundPage.getChildren().addAll(openAnAccountPage);
        return backgroundPage;
    }

    private Pane transfer() {
        if (countAccount == 0) {
            return noAccountAvailable();
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
            bTransfer.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

            Button bGoHome = new Button();
            bGoHome.setShape(new Circle(5));
            bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 266; ");

            bTransfer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!tFDestinationAccountNumber.getText().equals("") && !tFTransferAmount.getText().equals("") && !tFAccountPassword.getText().equals("") && tFDestinationAccountNumber.getText().matches(accountPattern) && tFTransferAmount.getText().matches(numberPattern) && tFAccountPassword.getText().matches(numberPattern)) {
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

                            Button bSuccessful = new Button();
                            bSuccessful.setShape(new Circle(5));
                            bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");

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
                    } else {
                        backgroundPage.getChildren().add(invalidInput2());
                    }
                }
            });

            bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });


            transferPage.getChildren().addAll(tFDestinationAccountNumber, tFSourceAccountNumber, tFAccountPassword, tFTransferAmount, bTransfer, bGoHome);
            backgroundPage.getChildren().add(transferPage);
            return backgroundPage;
        }

    } // code 6

    private Pane inventory() {

        if (countAccount == 0) {
            return noAccountAvailable();
        } else {
            writer.println("7");
            if (accountType == 1) {
                writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
            } else if (accountType == 2) {
                writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
            }

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
            bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

            bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });

            inventoryPage.getChildren().addAll(lInventory, bGoHome);
            backgroundPage.getChildren().add(inventoryPage);
            return backgroundPage;
        }
    } // code 7

    private Pane transaction() {//صورت حساب تراکنش ها

        if (countAccount == 0) {
            return noAccountAvailable();
        } else {
            writer.println("8");
            if (accountType == 1) {
                writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
            } else if (accountType == 2) {
                writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
            }

            String transaction = "";
            StringTokenizer answer = null;
            try {
                answer = new StringTokenizer(reader.readLine(), "*");

                String numberOfTransaction = answer.nextToken();

                for (int i = 0; i < Integer.parseInt(numberOfTransaction); i++) {
                    StringTokenizer newAnswer = new StringTokenizer(reader.readLine(), "*");
                    String source = newAnswer.nextToken();
                    String destination = newAnswer.nextToken();
                    String amount = newAnswer.nextToken();
                    transaction = transaction + "From:" + source + " / To: " + destination + " / Amount: " + amount + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pane backgroundPage = new Pane();
            backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Pane transactionPage = new Pane();
            transactionPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 457; -fx-max-height: 457; -fx-translate-x: 133; -fx-translate-y: 57");

            TextArea textArea = new TextArea();
            textArea.setText(transaction);
            textArea.setStyle(" -fx-min-width: 494; -fx-max-width: 494; -fx-min-height: 342; -fx-max-height: 342; -fx-translate-x: 19; -fx-translate-y: 19; -fx-font-family:" + Theme.font2 + "; -fx-font-size: 20;  -fx-text-fill: #000000;");
            textArea.editableProperty().asObject().set(false);

            Button bGoHome = new Button();
            bGoHome.setShape(new Circle(5));
            bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 228; -fx-translate-y: 370.5; ");

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
    }

    private Pane addInFavoriteAccount() {
        if (countAccount == 0) {
            return noAccountAvailable();
        } else {
            String answer = "";
            try {
                writer.println("9");
                if (accountType == 1) {
                    writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                } else if (accountType == 2) {
                    writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                }

                answer = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

            Button bSuccessful = new Button();
            bSuccessful.setShape(new Circle(5));
            bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");

            bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.setRoot(homePage());
                }
            });

            successfulAddInFavoriteAccount.getChildren().addAll(tFSuccessful, bSuccessful);
            backgroundPage.getChildren().add(successfulAddInFavoriteAccount);
            return backgroundPage;
        }
    }

    private Pane loan() {

        if (countAccount == 0) {
            return noAccountAvailable();
        } else {
            writer.println("10");
            if (accountType == 1) {
                writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
            } else if (accountType == 2) {
                writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
            }

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
            bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 361; -fx-translate-y: 456; ");

            bGetLoan.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!tFAmount.getText().equals("") && !tFTime.getText().equals("") && tFAmount.getText().matches(numberPattern) && tFTime.getText().matches(numberPattern) && Integer.parseInt(tFTime.getText()) > 0) {
                        writer.println("11");
                        if (accountType == 1) {
                            writer.println(accounts.get(checkAccount).getAccountNumber() + "*" + tFAmount.getText() + "*" + tFTime.getText() + "*");
                        } else if (accountType == 2) {
                            writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*" + tFAmount.getText() + "*" + tFTime.getText() + "*");
                        }

                        Pane newBackgroundPage = new Pane();
                        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                        Pane successfulGetLoan = new Pane();
                        successfulGetLoan.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                        Label tFSuccessful = new Label("Getting a loan is\ndone successfully");
                        tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-prompt-text-fill: " + Theme.text1 + "; -fx-alignment: center");

                        Button bSuccessful = new Button();
                        bSuccessful.setShape(new Circle(5));
                        bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");

                        successfulGetLoan.getChildren().addAll(tFSuccessful, bSuccessful);
                        newBackgroundPage.getChildren().add(successfulGetLoan);
                        backgroundPage.getChildren().add(newBackgroundPage);

                        bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                scene.setRoot(homePage());
                            }
                        });
                    } else {
                        backgroundPage.getChildren().add(invalidInput2());
                    }
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
    }

    private Pane bills() {
        if (countAccount == 0) {
            return noAccountAvailable();
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
            bPayingBill.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 76; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

            Button bGoHome = new Button();
            bGoHome.setShape(new Circle(5));
            bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 266; ");

            bPayingBill.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!tFBillId.getText().equals("") && !tFPaymentId.getText().equals("") && !tFAmount.getText().equals("") && tFBillId.getText().matches(numberPattern) && tFPaymentId.getText().matches(numberPattern) && tFAmount.getText().matches(numberPattern)) {
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

                        Button bSuccessful = new Button();
                        bSuccessful.setShape(new Circle(5));
                        if (answer.equals("0")) {
                            bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");
                        } else if (answer.equals("1")) {
                            bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");
                        }

                        successfulPayBill.getChildren().addAll(tFSuccessful, bSuccessful);
                        newBackgroundPage.getChildren().add(successfulPayBill);
                        backgroundPage.getChildren().add(newBackgroundPage);

                        bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                scene.setRoot(homePage());
                            }
                        });
                    } else {
                        backgroundPage.getChildren().add(invalidInput2());
                    }
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
        if (countAccount == 0) {
            return noAccountAvailable();
        } else {
            try {
                writer.println("13");
                if (accountType == 1) {
                    writer.println(accounts.get(checkAccount).getAccountNumber() + "*");
                } else if (accountType == 2) {
                    writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber() + "*");
                }

                String answer = reader.readLine();
                if (answer.equals("1")) {

                    Pane newBackgroundPage = new Pane();
                    newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane successfulCloseAccount = new Pane();
                    successfulCloseAccount.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

                    Label tFSuccessful = new Label();
                    tFSuccessful.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-prompt-text-fill: " + Theme.text1 + "; -fx-alignment: center");
                    tFSuccessful.setText("Account closed\n   successfully");

                    Button bSuccessful = new Button();
                    bSuccessful.setShape(new Circle(5));
                    bSuccessful.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home38.png\"); -fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 114; -fx-translate-y: 133; ");

                    helpCloseAnAccount();

                    bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    successfulCloseAccount.getChildren().addAll(tFSuccessful, bSuccessful);
                    newBackgroundPage.getChildren().add(successfulCloseAccount);
                    return newBackgroundPage;

                } else { // if answer = "0"

                    String balance = reader.readLine();

                    Pane backgroundPage = new Pane();// برای خانه
                    backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

                    Pane closeAnAccountPage = new Pane();
                    closeAnAccountPage.setStyle("-fx-background-color: " + Theme.back2 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 494; -fx-max-height: 494; -fx-translate-x: 266; -fx-translate-y: 38");

                    Label lInventory = new Label();
                    lInventory.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-text-fill: " + Theme.text2 + "; -fx-alignment: center");
                    lInventory.setText("Balance" + "\n" + balance);

                    TextField tFDestinationAccountNumber = new TextField();
                    tFDestinationAccountNumber.setStyle("-fx-font-family: '" + Theme.font2 + "'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 190; -fx-background-radius: 100px;-fx-background-color: " + Theme.field + " ; -fx-text-fill: " + Theme.text3 + "; ");
                    tFDestinationAccountNumber.setPromptText("Destination");

                    Button bCloseAccount = new Button("Close account");
                    bCloseAccount.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + Theme.button2 + "; -fx-text-fill: #FFFFFF; ");

                    Button bGoHome = new Button();
                    bGoHome.setShape(new Circle(5));
                    bGoHome.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 399; ;");

                    bCloseAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (!tFDestinationAccountNumber.getText().equals("") && tFDestinationAccountNumber.getText().matches(accountPattern)) {
                                String answer = "";
                                try {
                                    writer.println("14");
                                    if (accountType == 1) {
                                        writer.println(accounts.get(checkAccount).getAccountNumber());
                                    } else if (accountType == 2) {
                                        writer.println(favoriteAccounts.get(checkFavoriteAccount).getAccountNumber());
                                    }

                                    writer.println(tFDestinationAccountNumber.getText());

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
                                }

                                helpCloseAnAccount();

                                bSuccessful.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        scene.setRoot(homePage());
                                    }
                                });

                                successfulCloseAccount.getChildren().addAll(tFSuccessful, bSuccessful);
                                newBackgroundPage.getChildren().add(successfulCloseAccount);
                                backgroundPage.getChildren().add(newBackgroundPage);
                            } else {
                                backgroundPage.getChildren().add(invalidInput2());
                            }
                        }
                    });

                    bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            scene.setRoot(homePage());
                        }
                    });

                    closeAnAccountPage.getChildren().addAll(lInventory, tFDestinationAccountNumber, bCloseAccount, bGoHome);
                    backgroundPage.getChildren().add(closeAnAccountPage);
                    return backgroundPage;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void helpCloseAnAccount() {

        if (accountType == 1) {//پیدا کردن حساب در دسته مخالف
            for (int i = 1; i <= countFavoriteAccount; i++) {// ابتدا حذف در دسته مخالف
                if (accounts.get(checkAccount).getAccountNumber().equals(favoriteAccounts.get(i).getAccountNumber())) {
                    if (i == countFavoriteAccount) {
                        favoriteAccounts.remove(i);
                        checkFavoriteAccount = 1;
                        countFavoriteAccount--;
                    } else {
                        Account saveFavoriteAccount = favoriteAccounts.get(countFavoriteAccount);
                        favoriteAccounts.remove(countFavoriteAccount);
                        checkFavoriteAccount = 1;
                        countFavoriteAccount--;
                        favoriteAccounts.remove(i);
                        favoriteAccounts.put(i, saveFavoriteAccount);
                    }
                    break;
                }
            }

            if (checkAccount == countAccount) {// سپس حذف در همان دسته ای که بودیم
                accounts.remove(checkAccount);
                checkAccount = 1;
                countAccount--;
            } else {
                Account saveAccount = accounts.get(countAccount);
                accounts.remove(countAccount);
                countAccount--;
                accounts.remove(checkAccount);
                accounts.put(checkAccount, saveAccount);
            }

        } else if (accountType == 2) {//پیدا کردن حساب در دسته مخالف
            for (int i = 1; i <= countAccount; i++) {// ابتدا حذف در دسته مخالف
                if (favoriteAccounts.get(checkFavoriteAccount).getAccountNumber().equals(accounts.get(i).getAccountNumber())) {
                    if (i == countAccount) {
                        accounts.remove(i);
                        checkAccount = 1;
                        countAccount--;
                    } else {
                        Account saveAccount = accounts.get(countAccount);
                        accounts.remove(countAccount);
                        checkAccount = 1;
                        countAccount--;
                        accounts.remove(i);
                        accounts.put(i, saveAccount);
                    }
                    break;
                }
            }

            if (checkFavoriteAccount == countFavoriteAccount) {// سپس حذف در دسته ای که بودیم
                favoriteAccounts.remove(checkFavoriteAccount);
                checkFavoriteAccount = 1;
                countFavoriteAccount--;
            } else {
                Account saveFavoriteAccount = favoriteAccounts.get(countFavoriteAccount);
                favoriteAccounts.remove(countFavoriteAccount);
                countFavoriteAccount--;
                favoriteAccounts.remove(checkFavoriteAccount);
                favoriteAccounts.put(checkFavoriteAccount, saveFavoriteAccount);
            }

        }

    }

    private Pane noAccountAvailable() {

        Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
        //این تیکه رو گفتی نزنم برای بقیه تابعش میکنی
        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane errorTransaction = new Pane();
        errorTransaction.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label tFError = new Label("No account available");
        tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

        Button bError = new Button();
        bError.setShape(new Circle(5));
        bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

        bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        errorTransaction.getChildren().addAll(tFError, bError);
        newBackgroundPage.getChildren().add(errorTransaction);
        return newBackgroundPage;

    }

    private Pane invalidInput1() {
        Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
        //این تیکه رو گفتی نزنم برای بقیه تابعش میکنی
        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane errorInvalidInpute = new Pane();
        errorInvalidInpute.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label tFError = new Label("Invalid input");
        tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

        Button bError = new Button();
        bError.setShape(new Circle(5));
        bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

        bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(entrance());
            }
        });

        errorInvalidInpute.getChildren().addAll(tFError, bError);
        newBackgroundPage.getChildren().add(errorInvalidInpute);
        return newBackgroundPage;

    }

    private Pane invalidInput2() {
        Pane newBackgroundPage = new Pane();// برای اخطار نداشتن حساب
        //این تیکه رو گفتی نزنم برای بقیه تابعش میکنی
        newBackgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane errorInvalidInpute = new Pane();
        errorInvalidInpute.setStyle("-fx-background-color: " + Theme.back1 + "; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label tFError = new Label("Invalid input");
        tFError.setStyle("-fx-font-family: '" + Theme.font1 + "'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 95; -fx-max-height: 95; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: " + Theme.back1 + "; -fx-text-fill: " + Theme.text1 + "; -fx-alignment: center");

        Button bError = new Button();
        bError.setShape(new Circle(5));
        bError.setStyle("-fx-background-image: url(\"file:src/Client/picture/Home76.png\"); -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 95; ");

        bError.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        errorInvalidInpute.getChildren().addAll(tFError, bError);
        newBackgroundPage.getChildren().add(errorInvalidInpute);
        return newBackgroundPage;

    }

    private void emptyInformation() {
        for (int i = 1; i <= countAccount; i++) {
            accounts.remove(i);
        }
        for (int i = 1; i <= countFavoriteAccount; i++) {
            favoriteAccounts.remove(i);
        }
        countAccount = 0 ;
        checkAccount = 1 ;
        countFavoriteAccount = 0 ;
        checkFavoriteAccount = 1 ;
    }

    public static void main(String[] args) {
        launch(args);
    }
}