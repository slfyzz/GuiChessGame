package sample.Gui;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import sample.Ai.engine.Ai;
import sample.Ai.engine.ValidMoves;
import sample.Board.chessboard;
import sample.Board.menuOperations;
import sample.Board.square;
import sample.Board.position;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.pieces.king;
import sample.pieces.piece;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChessApp extends Application {

    //those variables are so necessary in the gui program so sorry for that messy looking but ITS SO NECESSARY
    public static final int HEIGHT = 8;
    public static final int WIDTH = 8;
    public static final int MAXSIZE = 80;
    private Group GroupSquares = new Group();
    public static Group GroupPieces = new Group();
    public static ImageView img = null;
    public static ImageView attackedImg = null;
    private static chessboard chessboard;
    static public Group Whites,Blacks;
    static public Label Check;
    static public int w,b;
    private static boolean Ai;
    private static menuOperations menuOperations;
    private static starter starter;



    @Override
    public  void start(Stage primaryStage) throws Exception{

        //set up a title
        primaryStage.setTitle("CHESS");

        Scene scene = StartGame(true);
        starter = new starter(chessboard,this, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene StartGame(boolean Ai) throws FileNotFoundException {

        // SinglePlayer or Multiplayer
        this.Ai = Ai;
        GroupPieces.getChildren().clear();
        GroupSquares.getChildren().clear();
        chessboard = null;
        img = attackedImg = null;
        w = b = 0;


        //make a Pane
        Pane Game = (Pane) getContent();
        // initialize a space for the white/black deaths
        Whites = new Group();
        Blacks = new Group();
        w = b = 0;
        VBox WhiteDeaths,BlackDeathes;
        WhiteDeaths = new VBox();
        BlackDeathes = new VBox();
        Label WhiteDeath = new Label("White Armies :");
        Label BlackDeath = new Label("Black Armies :");
        WhiteDeaths.getChildren().add(WhiteDeath);
        BlackDeathes.getChildren().add(BlackDeath);

        //handle some Graphics issues as i can
        WhiteDeath.setWrapText(true);
        BlackDeath.setWrapText(true);
        WhiteDeath.setFont(Font.font("Cambria", 32));
        BlackDeath.setFont(Font.font("Cambria", 32));
        WhiteDeaths.getChildren().add(Whites);
        BlackDeathes.getChildren().add(Blacks);
        WhiteDeaths.setPadding(new Insets(10,10,10,10));
        BlackDeathes.setPadding(new Insets(10,10,10,10));
        // BorderPane.setAlignment(Game, Pos.CENTER);

        //make the parent root of all the panes
        // BorderPane root = new BorderPane(Game);
        GridPane root = new GridPane();
        GridPane.setConstraints(Game, 0, 0);
        GridPane.setConstraints(WhiteDeaths, 1, 0);
        GridPane.setConstraints(BlackDeathes, 2, 0);
        WhiteDeaths.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        BlackDeathes.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        //some Gui issues AGAINNN
        //root.setLeft(WhiteDeaths);
        //root.setRight(BlackDeathes);
        root.setPrefSize(1200,700);
        root.getColumnConstraints().add(new ColumnConstraints(MAXSIZE * 8));
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");


        //make a label for saying if there is a warning in the game
        Check = new Label();
        HBox TopH = new HBox();
        //TopH.setAlignment(Pos.CENTER);
        //root.setTop(TopH);
        GridPane.setConstraints(TopH, 3, 0);

        ImageView menu = new ImageView(new Image(new FileInputStream("C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\Board\\icons\\menu.png")));
        menu.setPreserveRatio(true);
        menu.setFitWidth(45);
        menu.setFitHeight(45);
        ImageView redo = new ImageView(new Image(new FileInputStream("C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\Board\\icons\\redo.png")));
        redo.setPreserveRatio(true);
        redo.setFitWidth(45);
        redo.setFitHeight(45);
        ImageView undo = new ImageView(new Image(new FileInputStream("C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\Board\\icons\\undo.png")));
        undo.setPreserveRatio(true);
        undo.setFitWidth(45);
        undo.setFitHeight(45);
        GridPane.setConstraints(menu, 3, 1);
        GridPane.setConstraints(redo, 3, 2);
        GridPane.setConstraints(undo, 3, 3);

        undo.setOnMouseClicked(e ->
        {
            if (menuOperations.undoSize() > 0) {
                menuOperations.undo();
                if (Ai)
                    menuOperations.undo();
            }
        });

        redo.setOnMouseClicked(e -> {
            menuOperations.redo();
            if (Ai)
                menuOperations.popRedo();
        });

        menu.setOnMouseClicked(e -> {
           /* try {
                menuOperations.load();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }*/
           starter.StartingWindow();

        });

        // make hint button
        Button hintButton = new Button("hint");
        hintButton.setOnAction(e -> {
            // we using a class called Ai in thr Ai.engine package that use minimax algorithm, Game theory
            helpAi();
        });
        TopH.getChildren().addAll(Check, hintButton);
        root.getChildren().addAll(Game, WhiteDeaths, BlackDeathes, TopH, menu, redo, undo);
        Scene scene = new Scene(root);
        return scene;
    }

    private Parent getContent()
    {
        // make a new pane
        Pane root = new Pane();
        root.setPrefSize(WIDTH * MAXSIZE , HEIGHT * MAXSIZE);
        root.getChildren().addAll(GroupSquares,GroupPieces);
        chessboard chessboard = new chessboard();
        this.chessboard = chessboard;
        int color = 1;
        menuOperations = new menuOperations(chessboard);

        // initialize every square in the board
        for (int i = 0; i < HEIGHT ; i++)
        {
            for(int j = 0; j < WIDTH; j++)
            {
                chessboard.board[j][i] = new square(color, new position(j, i), chessboard);
                chessboard.board[j][i].initialization();
                if (chessboard.board[j][i].getP() != null)
                {
                    if(chessboard.board[j][i].getP().getColor() == 1)
                    {
                        chessboard.activeWhite.add(chessboard.board[j][i].getP());
                    }
                    else
                        chessboard.activeBlack.add(chessboard.board[j][i].getP());
                }
                color *= -1;

                // if its clicked !!
                int finalJ = j;
                int finalI = i;
                chessboard.board[j][i].setOnMouseClicked(e ->
                {
                    if (moveImg(new position(finalJ, finalI)))
                    {
                        menuOperations.clearRedo();
                    }
                });
                GroupSquares.getChildren().add(chessboard.board[j][i]);
                if (chessboard.board[j][i].getP() != null)
                {
                    try {
                        // make an image and set it up
                        ImageView imageView = new ImageView(new Image(new FileInputStream(chessboard.board[j][i].getP().getLocation())));
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(MAXSIZE);
                        imageView.setFitWidth(MAXSIZE);
                        imageView.setX(i * MAXSIZE);
                        imageView.setY(j * ChessApp.MAXSIZE);
                        chessboard.board[j][i].getP().setImageView(imageView);
                        GroupPieces.getChildren().add(imageView);
                        imageView.setOnMouseClicked(e ->{
                            if(AttackImg(imageView))
                            {
                                menuOperations.clearRedo();
                            }
                        });
                    }
                    catch (Exception e){
                    }

                }
            }
            color *= -1;
        }

        return root;
    }


    private static void helpAi()
    {
       /* Evaluation evaluation = new Evaluation(chessboard);
        System.out.println(evaluation.getEvaluation());*/
        Ai ai = new Ai(chessboard, chessboard.getTurn(), 3);
        ValidMoves validMoves = ai.executeAi();
        if (validMoves != null) {
            int x = validMoves.getNewPos().getX();
            int y = validMoves.getNewPos().getY();
            int oldx = validMoves.getOldPos().getX();
            int oldy = validMoves.getOldPos().getY();
            System.out.println("old x : " + oldx);
            System.out.println("old y : " + oldy);
            System.out.println("x : " + x);
            System.out.println("y : " + y);
            img = chessboard.board[oldx][oldy].getP().getImageView();
            if (chessboard.board[x][y].getP() != null) {
                AttackImg(chessboard.board[x][y].getP().getImageView());
            } else
                moveImg(new position(y, x));
        }
        else
            System.out.println("THE GAME IS ENDED");
    }
    public static boolean AttackImg(ImageView imageView)
    {
        if (img == null) {
            int y = (int) imageView.getY() / ChessApp.MAXSIZE;
            int x = (int) imageView.getX() / ChessApp.MAXSIZE;
            if (chessboard.board[y][x].getP().getColor() == chessboard.getTurn())
                img = imageView;
        }
        else if (img != imageView)
        {
            // determine some final values
            attackedImg = imageView;
            int y = (int) img.getY() / ChessApp.MAXSIZE;
            int x = (int) img.getX() / ChessApp.MAXSIZE;
            int attackedy = (int) attackedImg.getY() / ChessApp.MAXSIZE;
            int attackedx = (int) attackedImg.getX() / ChessApp.MAXSIZE;
            piece attackedpiece = chessboard.board[attackedy][attackedx].getP();

            // check if the move will cause problems or not
            if (chessboard.board[y][x].getP().virtualMove(new position(attackedy, attackedx)) &&
                    chessboard.board[y][x].getP().attack(new position(attackedy, attackedx))){

                // remove the attacked piece
                attackedImg.setVisible(false);


                ValidMoves validMove = new ValidMoves(new position(y ,x), new position(attackedy, attackedx));
                validMove.setAttackedPiece(attackedpiece);
                menuOperations.push(validMove);
               // menuOperations.clearRedo();
               // menuOperations.saveTheGame();

                //Change the position of the current Piece
                img.setX(attackedx * MAXSIZE);
                img.setY(attackedy * MAXSIZE);
                helperfunc(attackedy, attackedx);
                //reset the values
                img = null;
                attackedImg = null;
                if(Ai &&chessboard.getTurn() == 1)
                {
                    helpAi();
                }
                return true;
            }
            else {
                img = attackedImg;
                attackedImg = null;
            }
        }
        else
            img = imageView;

        return false;
    }
    public static  boolean moveImg(position pos)
    {
        if (img != null) {

            // img is not null means that we have chosen a piece and we need to move it in that square
            // those some variables needed
            int y = (int) img.getY() / ChessApp.MAXSIZE;
            int x = (int) img.getX() / ChessApp.MAXSIZE;

            // check if that move is valid or not
            if (chessboard.board[y][x].getP() != null && chessboard.board[y][x].getP().getColor() == chessboard.getTurn()) {
                if (chessboard.board[y][x].getP().virtualMove(new position(pos.getY(), pos.getX())) &&
                        chessboard.board[y][x].getP().validMove(new position(pos.getY(), pos.getX())) &&
                        chessboard.board[pos.getY()][pos.getX()].getP() == null) {
                    chessboard.board[y][x].getP().Move(new position(pos.getY(), pos.getX()));

                    // changing the position of the image

                    img.setX(pos.getX() * MAXSIZE);
                    img.setY(pos.getY() * MAXSIZE);

                    menuOperations.push(new ValidMoves(new position(y ,x), new position(pos.getY(),pos.getX())));
                 //   menuOperations.clearRedo();
                 //   menuOperations.saveTheGame();

                    helperfunc(pos.getY(), pos.getX());

                    img = null;
                    attackedImg = null;
                    if(Ai && chessboard.getTurn() == 1)
                    {
                        helpAi();
                    }
                    return true;
                }
            }
        }
        return false;

    }

    private static void helperfunc(int attackedy, int attackedx)
    {
        // reverse the turn
        chessboard.reverse();

        System.out.println("shi"); //for debug


        //keep track of the kings position
        if (chessboard.board[attackedy][attackedx].getP().getClass() == king.class)
        {
            if (chessboard.board[attackedy][attackedx].getP().getColor() == 1)
            {
                chessboard.setWhiteKing(new position(attackedy, attackedx));
            }
            else
            {
                chessboard.setBlackKing(new position(attackedy, attackedx));
            }
        }

        //check if the game is ended
        if(chessboard.checkAlert() && chessboard.checkMate(new position(attackedy, attackedx)))
        {
            System.out.println("THE KING IS DEAD");
        }
        //check if it was a checkmate
        else if (chessboard.checkAlert())
        {
            if(chessboard.getTurn() == 1)
            {
                Check.setText("White king is checked!!!!!!!");
            }
            else
            {
                Check.setText("Black king is checked!!!!!!!");
            }
            Check.setVisible(true);
        }
        else
            Check.setVisible(false);

    }


}
