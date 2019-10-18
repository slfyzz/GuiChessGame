package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Gui.ChessApp;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ChessApp newGame = new ChessApp();
        newGame.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
