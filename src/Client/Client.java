package Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Client extends Application {
    Scene scene;
    private String name;
    private String nationalId;
    private String password;
    private String phoneNumber;
    private String email;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane r = new Pane();


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
            signIn.setStyle("-fx-background-color: #1D3557; -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Label welcome = new Label("Welcome to the e-bank app");
            welcome.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 34; -fx-font-weight: bold; -fx-min-width: 418; -fx-max-width: 418; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 342; -fx-translate-y: 38; -fx-text-fill: #E63946; ");

            Button bSignIn = new Button("ورود");
            bSignIn.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

            Button bSingUp = new Button("ثبت نام");
            bSingUp.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

            TextField tFNationalId = new TextField();
            tFNationalId.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFNationalId.setPromptText("کد ملی");

            TextField tFPassword = new TextField();
            tFPassword.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 380; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFPassword.setPromptText("رمز عبور");

            Button bSignInFinal = new Button("ورود");
            bSignInFinal.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 38; -fx-max-height: 38;-fx-translate-x: 475; -fx-translate-y: 494; -fx-background-radius: 15px; -fx-background-color: #A8DADC; ");

            bSignIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 1;
                }
            });
            bSingUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 2;
                    scene.setRoot(entrance());
                }
            });
            bSignInFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (1 == 1) {
                        scene.setRoot(homePage());
                    }
                }
            });

            signIn.getChildren().addAll(welcome, bSignIn, bSingUp, tFNationalId, tFPassword, bSignInFinal);
            return signIn;
        } else {
            Pane signUp = new Pane();
            signUp.setStyle("-fx-background-color: #1D3557; -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

            Label welcome = new Label("Welcome to the e-bank app");
            welcome.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 34; -fx-font-weight: bold; -fx-min-width: 418; -fx-max-width: 418; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 342; -fx-translate-y: 38; -fx-text-fill: #E63946; ");

            Button bSignIn = new Button("ورود");
            bSignIn.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

            Button bSingUp = new Button("ثبت نام");
            bSingUp.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 114; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

            TextField tFName = new TextField();
            tFName.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFName.setPromptText("کد ملی");

            TextField tFNationalId = new TextField();
            tFNationalId.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 570; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFNationalId.setPromptText("کد ملی");

            TextField tFPassword = new TextField();
            tFPassword.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFPassword.setPromptText("رمز عبور");

            TextField tFPhoneNumber = new TextField();
            tFPhoneNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 570; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFPhoneNumber.setPromptText("تلفن");

            TextField tFEmail = new TextField();
            tFEmail.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 380; -fx-translate-y: 380; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
            tFEmail.setPromptText("ایمیل");

            Button bSignInFinal = new Button("ورود");
            bSignInFinal.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 38; -fx-max-height: 38;-fx-translate-x: 475; -fx-translate-y: 494; -fx-background-radius: 15px; -fx-background-color: #A8DADC; ");

            bSignIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 1;
                    scene.setRoot(entrance());
                }
            });
            bSingUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    entrance = 2;
                }
            });
            bSignInFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (1 == 1) {
                        scene.setRoot(homePage());
                    }
                }
            });
            signUp.getChildren().addAll(welcome, bSignIn, bSingUp, tFName, tFNationalId, tFPassword, tFPhoneNumber, tFEmail, bSignInFinal);
            return signUp;
        }
    }

    private Pane homePage() {

        Pane homePage = new Pane();
        homePage.setStyle("-fx-background-color: #1D3557; -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Image image = new Image("file:src/picture/Inkedphoto_2021-06-29_03-11-07_LI.jpg");
        Circle circle = new Circle(95);
        circle.setStyle("-fx-translate-x: 133; -fx-translate-y: 114");
//            imageView.setStyle("-fx-translate-x: 38; -fx-translate-y: 19");
//            imageView.fitWidthProperty().set(190);
//            imageView.fitHeightProperty().set(190);

        Button bUserProfile = new Button("پروفایل کاربری");
        bUserProfile.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 247; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bSetting = new Button("تنظیمات");
        bSetting.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 323; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bAboutUs = new Button("درباره ما");
        bAboutUs.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bExit = new Button("خروج");
        bExit.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 57; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Label lName = new Label("محمدرضا راثی");
        lName.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 25; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 608; -fx-translate-y: 38; -fx-text-fill: #E63946; ");

        Button bFavoriteAccount = new Button("حساب های منتخب");
        bFavoriteAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 342; -fx-translate-y: 114; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bAllAccount = new Button("همه حساب ها");
        bAllAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 18; -fx-font-weight: bold; -fx-min-width: 152; -fx-max-width: 152; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 570; -fx-translate-y: 114; -fx-background-radius: 100px 100px 0px 0px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Rectangle rBox = new Rectangle(456, 228, Color.rgb(241, 250, 238));
        rBox.setStyle("-fx-translate-x: 304; -fx-translate-y: 152; -fx-border-radius: 15px");

        Button bGoLeft = new Button();
        bGoLeft.setShape(new Circle(5));
        bGoLeft.setStyle("-fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 323; -fx-translate-y: 247;");

        Label lInformationAccount = new Label("حساب کاری" +
                "\n" + " سرمایه گذاری بلند مدت" +
                "\n" + "6062 5611 96 2402");// اطلاعات از اری لیست حساب ها خوانده میشود
        lInformationAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 25; -fx-font-weight: bold;  -fx-min-width: 304; -fx-max-width: 304; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 380; -fx-translate-y: 190; -fx-background-radius: 15px; -fx-background-color: " + "#FFDDD2; -fx-text-fill: #000000; ");

        Button bGoRight = new Button();
        bGoRight.setShape(new Circle(5));
        bGoRight.setStyle("-fx-min-width: 38; -fx-max-width: 38; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 703; -fx-translate-y: 247;");

        Button bTransaction = new Button("صورت حساب");//تراکنش
        bTransaction.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 323; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bInventory = new Button("موجودی");
        bInventory.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 437; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bTransfer = new Button("انتقال وجه");
        bTransfer.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 551; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bOpenAnAccount = new Button("افتتاح حساب");
        bOpenAnAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 665; -fx-translate-y: 399; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bCloseAnAccount = new Button("بستن");
        bCloseAnAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 323; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bBills = new Button("قبض");
        bBills.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 437; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bLoan = new Button("وام");
        bLoan.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 551; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        Button bAddInFavoriteAccount = new Button("افزودن منتخب");
        bAddInFavoriteAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 11; -fx-font-weight: bold; -fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 665; -fx-translate-y: 475; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        bUserProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //go line  ...
                scene.setRoot(userProfilePage());
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
                homePage.getChildren().add(openAnAccountPage());
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
        });

        homePage.getChildren().addAll(circle, bUserProfile, bSetting, bAboutUs, bExit,
                lName, bFavoriteAccount, bAllAccount, rBox, bGoLeft, lInformationAccount, bGoRight,
                bTransaction, bInventory, bTransfer, bOpenAnAccount, bCloseAnAccount, bBills, bLoan, bAddInFavoriteAccount);
        return homePage;

    }

    private Pane userProfilePage() {


        Pane userProfilePage = new Pane();
        userProfilePage.setStyle("-fx-background-color: #1D3557; -fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570");

        Image image = new Image("file:src/picture/Inkedphoto_2021-06-29_03-11-07_LI.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-translate-x: 19; -fx-translate-y: 171");
        imageView.fitWidthProperty().set(228);
        imageView.fitHeightProperty().set(228);

        Button bChangeImage = new Button("تعویض عکس");
        bChangeImage.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 456; -fx-background-radius: 15px; -fx-background-color: " + "#A8DADC; -fx-text-fill: #000000; ");

        TextField tfName = new TextField();
        tfName.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 114; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tfName.setPromptText(name);

        TextField tfNationalId = new TextField();
        tfNationalId.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 551; -fx-translate-y: 114; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tfNationalId.setPromptText(nationalId);

        TextField tfPassword = new TextField();
        tfPassword.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 228; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tfPassword.setPromptText(password);

        TextField tfPhoneNumber = new TextField();
        tfPhoneNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 171; -fx-max-width: 171; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 551; -fx-translate-y: 228; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tfPhoneNumber.setPromptText(phoneNumber);

        TextField tfEmail = new TextField();
        tfEmail.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 30; -fx-font-weight: bold; -fx-min-width: 380; -fx-max-width: 380; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 342; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tfEmail.setPromptText(email);

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 494; -fx-translate-y: 456;");

        Button bSaveChanges = new Button("ذخیره تغییرات");
        bSaveChanges.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 114; -fx-max-width: 114; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 608; -fx-translate-y: 456; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");


        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });


        userProfilePage.getChildren().addAll(imageView, bChangeImage,
                tfName, tfNationalId, tfPassword, tfPhoneNumber, tfEmail, bGoHome, bSaveChanges);
        return userProfilePage;
    }

    private Pane openAnAccountPage() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");
        Pane openAnAccountPage = new Pane();
        openAnAccountPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

        TextField tFPassword = new TextField();
        tFPassword.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFPassword.setPromptText("رمز ورود");

        TextField tFAccountNumber = new TextField();
        tFAccountNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFAccountNumber.setPromptText("شماره حساب");

        TextField tFAlias = new TextField();
        tFAlias.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFAlias.setPromptText("نام مستعار");

        Button bAccountType = new Button("نوع حساب");
        bAccountType.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        Button bOpenAnAccount = new Button("افتتاح حساب");
        bOpenAnAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        bAccountType.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane accountTypePage = new Pane();
                accountTypePage.setStyle("-fx-background-color: #1D3557; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 133; -fx-translate-y: 95");

                Pane backgroundPage = new Pane();
                backgroundPage.setStyle(" -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 0; -fx-translate-y: 0");

                TextArea text = new TextArea("نوع حساب خود را انتخاب کنید");
                text.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 0; -fx-background-color: #D3EDEE; -fx-text-fill: #000");
                text.editableProperty().asObject().set(false);

                Button s_b = new Button("سپرده بلند مدت");
                s_b.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 16; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 38; -fx-background-color: #D3EDEE; -fx-text-fill: #000");

                Button s_k = new Button("سپرده کوتاه مدت");
                s_k.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 16; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 76; -fx-background-color: #D3EDEE; -fx-text-fill: #000");

                Button gh_j = new Button("قرض الحسنه");
                gh_j.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 16; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 114; -fx-background-color: #D3EDEE; -fx-text-fill: #000");

                Button gh_p = new Button("قرض الحسنه سرمایه گذاری");
                gh_p.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 16; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 152; -fx-background-color: #D3EDEE; -fx-text-fill: #000");

                s_b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        bAccountType.setText("سپرده بلند مدت");
                        openAnAccountPage.getChildren().remove(backgroundPage);
                    }
                });

                s_k.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        bAccountType.setText("سپرده کوتاه مدت");
                        openAnAccountPage.getChildren().remove(backgroundPage);
                    }
                });

                gh_j.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        bAccountType.setText("قرض الحسنه");
                        openAnAccountPage.getChildren().remove(backgroundPage);
                    }
                });

                gh_p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        bAccountType.setText("قرض الحسنه سرمایه گذاری");
                        openAnAccountPage.getChildren().remove(backgroundPage);
                    }
                });


                accountTypePage.getChildren().addAll(text, s_b, s_k, gh_j, gh_p);
                backgroundPage.getChildren().add(accountTypePage);
                openAnAccountPage.getChildren().add(backgroundPage);
            }
        });

        bOpenAnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (1 == 1) {
                    scene.setRoot(homePage());
                }
            }
        });

        openAnAccountPage.getChildren().addAll(tFPassword, tFAccountNumber, tFAlias, bAccountType, bOpenAnAccount);
        backgroundPage.getChildren().addAll(openAnAccountPage);
        return backgroundPage;
    }

    private Pane transfer() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane transferPage = new Pane();
        transferPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

        TextField tFDestinationAccountNumber = new TextField();
        tFDestinationAccountNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFDestinationAccountNumber.setPromptText("شماره حساب مقصد");

        TextField tFSourceAccountNumber = new TextField();
        tFSourceAccountNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFSourceAccountNumber.setPromptText("شماره حساب مقصد");

        TextField tFAccountPassword = new TextField();
        tFAccountPassword.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFAccountPassword.setPromptText("رمز حساب");

        TextField tFTransferAmount = new TextField();
        tFTransferAmount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 152; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFTransferAmount.setPromptText("مبلغ انتقال");

        Button bTransfer = new Button("انتقال وجه");
        bTransfer.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        bTransfer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (1 == 1) {
                    scene.setRoot(homePage());
                }
            }
        });

        transferPage.getChildren().addAll(tFDestinationAccountNumber, tFSourceAccountNumber, tFAccountPassword, tFTransferAmount, bTransfer);
        backgroundPage.getChildren().add(transferPage);
        return backgroundPage;
    }

    private Pane inventory() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane inventoryPage = new Pane();
        inventoryPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 190; -fx-max-height: 190; -fx-translate-x: 266; -fx-translate-y: 190");

        Label lInventory = new Label("موجودی قابل برداشت");
        lInventory.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 38; -fx-max-height: 38; -fx-translate-x: 0; -fx-translate-y: 0; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

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
    }

    private Pane transaction() {//صورت حساب تراکنش ها
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane transactionPage = new Pane();
        transactionPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 456; -fx-max-height: 456; -fx-translate-x: 133; -fx-translate-y: 57");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #1D3557; -fx-min-width: 494; -fx-max-width: 494; -fx-min-height: 342; -fx-max-height: 342; -fx-translate-x: 19; -fx-translate-y: 19");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 228; -fx-translate-y: 370.5;");

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        transactionPage.getChildren().addAll(scrollPane, bGoHome);
        backgroundPage.getChildren().add(transactionPage);
        return backgroundPage;
    }

    private Pane loan() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane loanPage = new Pane();
        loanPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 570; -fx-max-height: 570; -fx-translate-x: 133; -fx-translate-y: 0");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #1D3557; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 570; -fx-max-height: 570; -fx-translate-x: 0; -fx-translate-y: 0");

        TextField tFAmount = new TextField();
        tFAmount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 76; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFAmount.setPromptText("مبلغ");

        TextField tFTime = new TextField();
        tFTime.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 190; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFTime.setPromptText("دوره");

        Button bGetLoan = new Button("درخواست وام");
        bGetLoan.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 342; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 342; -fx-translate-y: 456;");

        bGetLoan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (1 == 1) {
                    scene.setRoot(homePage());
                }
            }
        });

        bGoHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(homePage());
            }
        });

        loanPage.getChildren().addAll(scrollPane, tFAmount, tFTime, bGetLoan, bGoHome);
        backgroundPage.getChildren().add(loanPage);
        return backgroundPage;
    }

    private Pane bills() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane billsPage = new Pane();
        billsPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 532; -fx-max-width: 532; -fx-min-height: 380; -fx-max-height: 380; -fx-translate-x: 133; -fx-translate-y: 95");

        TextField tFBillId = new TextField();
        tFBillId.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 38; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFBillId.setPromptText("شناسه قبض");

        TextField tFPaymentId = new TextField();
        tFPaymentId.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 304; -fx-translate-y: 38; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFPaymentId.setPromptText("شناسه پرداخت");

        TextField tFAmount = new TextField();
        tFAmount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 152; -fx-translate-y: 152; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFAmount.setPromptText("مبلغ");

        Button bPayingBill = new Button("پرداخت قبض");
        bPayingBill.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 266; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 418; -fx-translate-y: 266;");

        bPayingBill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (1 == 1) {
                    scene.setRoot(homePage());
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

    private Pane closeAnAccount() {
        Pane backgroundPage = new Pane();
        backgroundPage.setStyle("-fx-min-width: 798; -fx-max-width: 798; -fx-min-height: 570; -fx-max-height: 570;");

        Pane closeAnAccountPage = new Pane();
        closeAnAccountPage.setStyle("-fx-background-color: #FFDDD2; -fx-min-width: 266; -fx-max-width: 266; -fx-min-height: 494; -fx-max-height: 494; -fx-translate-x: 266; -fx-translate-y: 38");

        TextArea tAInventory = new TextArea("موجودی باقی مانده" +
                "\n" +
                " حساب شما");
        tAInventory.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 20; -fx-font-weight: bold; -fx-min-width: 228; -fx-max-width: 228; -fx-min-height: 152; -fx-max-height: 152; -fx-translate-x: 19; -fx-translate-y: 19; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        TextField tFDestinationAccountNumber = new TextField();
        tFDestinationAccountNumber.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 22; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 190; -fx-background-radius: 100px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");
        tFDestinationAccountNumber.setPromptText("شماره حساب مقصد");

        Button bCloseAccount = new Button("بستن حساب");
        bCloseAccount.setStyle("-fx-font-family: 'B Nazanin'; -fx-font-size: 17; -fx-font-weight: bold; -fx-min-width: 190; -fx-max-width: 190; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 38; -fx-translate-y: 304; -fx-background-radius: 15px; -fx-background-color: #D3EDEE; -fx-prompt-text-fill: #50514F; ");

        Button bGoHome = new Button();
        bGoHome.setShape(new Circle(5));
        bGoHome.setStyle("-fx-min-width: 76; -fx-max-width: 76; -fx-min-height: 76; -fx-max-height: 76; -fx-translate-x: 95; -fx-translate-y: 399;");

        bCloseAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (1 == 1) {
                    scene.setRoot(homePage());
                }
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
