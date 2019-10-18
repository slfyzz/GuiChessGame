package sample.Gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Board.chessboard;
import sample.Board.menuOperations;

import java.io.FileNotFoundException;

public class starter {
    private boolean loadingFile;
    private boolean resume;
    private chessboard chessboard;
    private ChessApp chessApp;
    private Stage primaryStage;
    private sample.Board.menuOperations menuOperations;

    starter(chessboard chess, ChessApp chessApp, Stage stage)
    {
        this.chessboard = chess;
        loadingFile = resume = false;
        this.chessApp = chessApp;
        this.primaryStage = stage;
        this.menuOperations = new menuOperations(chessboard);
    }

    public void SinglePlayer()
    {
        try {

            primaryStage.setScene(this.chessApp.StartGame(true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Multiplayer()
    {
        try {

            primaryStage.setScene(this.chessApp.StartGame(false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void save()
    {
        menuOperations.saveTheGame();
    }
    public void load()
    {
        try {
            menuOperations.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void StartingWindow()
    {
        Stage window = new Stage();
        window.setTitle("CHESS");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5));
        Button singlePlayer = new Button("SinglePlayer");
        Button multiplayer = new Button("Multiplayer");
        Button load = new Button("Load");
        Button save = new Button("Save");
        Button resume = new Button("Resume");
        vBox.getChildren().addAll(singlePlayer, multiplayer, load, save, resume);
        singlePlayer.setOnMouseClicked(e -> {
            this.SinglePlayer();
            window.close();
        });
        multiplayer.setOnMouseClicked(e ->
        {
            this.Multiplayer();
            window.close();
        });
        save.setOnMouseClicked(e -> {
            this.save();
            window.close();
        });
        load.setOnMouseClicked(e ->
        {
            this.load();
            window.close();

        });
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
        resume.setOnMouseClicked(e -> window.close());
    }

    
}
