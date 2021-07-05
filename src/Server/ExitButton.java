package Server;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExitButton extends Application implements Runnable {
    private String[] args;

    public ExitButton() {

    }

    public ExitButton(String[] args) {
        this.args = args;
    }

    @Override
    public void run() {
        launch(this.args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane p = new Pane();
        p.setStyle("-fx-background-color: #000000");
        Button exit = new Button("Exit");
        exit.setStyle("-fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 40; -fx-font-weight: bold; -fx-min-width: 150; -fx-max-width: 150; -fx-min-height: 75; -fx-max-height: 75; -fx-translate-x: 50; -fx-translate-y: 50; -fx-background-radius: 15px; -fx-background-color: #FFFFFF; -fx-text-fill: #000000; ");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Information.sendInformation();
                System.exit(0);
            }
        });
        p.getChildren().add(exit);
        Scene s = new Scene(p, 250, 185);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Server-e-bank");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
