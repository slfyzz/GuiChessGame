package sample.Board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Ai.engine.ValidMoves;
import sample.Gui.ChessApp;
import sample.pieces.*;

import java.io.*;
import java.util.Stack;

import static sample.Gui.ChessApp.*;

public class menuOperations {
    private Stack<ValidMoves> undo;
    private Stack<ValidMoves> redo;
    private chessboard chessboard;
    private String file;

    public menuOperations(chessboard chessboard)
    {
        undo = new Stack<>();
        redo = new Stack<>();
        this.chessboard = chessboard;
        file = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\Gui\\chessSave\\chess.txt";

    }

    public void push(ValidMoves validMoves)
    {
        undo.push(validMoves);
    }
    public int undoSize()
    {
        return undo.size();
    }
    public void undo()
    {
        redo.push(undo.pop());
        position oldpos = redo.peek().getOldPos();
        position newpos = redo.peek().getNewPos();
        chessboard.board[oldpos.getX()][oldpos.getY()].setP(chessboard.board[newpos.getX()][newpos.getY()].getP());
        chessboard.board[newpos.getX()][newpos.getY()].setP(redo.peek().getAttackedPiece());
        chessboard.board[oldpos.getX()][oldpos.getY()].getP().setPos(oldpos);
        chessboard.board[oldpos.getX()][oldpos.getY()].getP().getImageView().setX(oldpos.getY() * MAXSIZE);
        chessboard.board[oldpos.getX()][oldpos.getY()].getP().getImageView().setY(oldpos.getX() * MAXSIZE);
        chessboard.board[oldpos.getX()][oldpos.getY()].getP().getImageView().setVisible(true);

        if (chessboard.board[newpos.getX()][newpos.getY()].getP() != null) {
            chessboard.board[newpos.getX()][newpos.getY()].getP().setPos(newpos);
            chessboard.board[newpos.getX()][newpos.getY()].getP().getImageView().setX(newpos.getY() * MAXSIZE);
            chessboard.board[newpos.getX()][newpos.getY()].getP().getImageView().setY(newpos.getX() * MAXSIZE);
            chessboard.board[newpos.getX()][newpos.getY()].getP().getImageView().setVisible(true);
            if (chessboard.board[newpos.getX()][newpos.getY()].getP().getColor() == 1)
            {
                chessboard.activeWhite.add(chessboard.board[newpos.getX()][newpos.getY()].getP());
                chessboard.DeathWhite.remove(chessboard.DeathWhite.size()-1);
                ChessApp.w--;
                ChessApp.Whites.getChildren().remove(ChessApp.Whites.getChildren().size()-1);
            }
            else
            {
                chessboard.activeBlack.add(chessboard.board[newpos.getX()][newpos.getY()].getP());
                chessboard.DeathBlack.remove(chessboard.DeathBlack.size()-1);
                ChessApp.b--;
                ChessApp.Blacks.getChildren().remove(ChessApp.Blacks.getChildren().size()-1);
            }
        }
        if (chessboard.board[oldpos.getX()][oldpos.getY()].getP().getClass() == king.class)
        {
            if (chessboard.board[oldpos.getX()][oldpos.getY()].getP().getColor() == 1)
            {
                chessboard.setWhiteKing(oldpos);
            }
            else
                chessboard.setBlackKing(oldpos);
        }


        chessboard.reverse();
    }
    public void redo()
    {
        if (redo.size() > 0)
        {
            position oldpos = redo.peek().getOldPos();
            position newpos = redo.peek().getNewPos();
            redo.pop();
            ChessApp.img = chessboard.board[oldpos.getX()][oldpos.getY()].getP().getImageView();
            if (chessboard.board[newpos.getX()][newpos.getY()].getP() != null)
            {
                ChessApp.AttackImg(chessboard.board[newpos.getX()][newpos.getY()].getP().getImageView());
            }
            else
            {
                ChessApp.moveImg(new position(newpos.getY(), newpos.getX()));
            }
        }
    }
    public void clearRedo()
    {
        redo.clear();
    }
    public void popRedo()
    {
        if(redo.size() > 0){
        redo.pop();
        }
    }
    public void saveTheGame()
    {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.file), "utf-8"))) {
            for (int i = 0; i < ChessApp.WIDTH; i++)
            {
                for (int j = 0; j < ChessApp.HEIGHT; j++)
                {
                    if (chessboard.board[i][j].getP() != null)
                    {
                        writer.write(String.valueOf(chessboard.board[i][j].getP().getCost()));
                        ((BufferedWriter) writer).newLine();
                    }
                    else
                    {
                        writer.write(String.valueOf(0));
                        ((BufferedWriter) writer).newLine();
                    }
                }
            }
            writer.write(String.valueOf(chessboard.DeathBlack.size()));
            ((BufferedWriter) writer).newLine();
            for (int i = 0; i < chessboard.DeathBlack.size(); i++)
            {
                writer.write(String.valueOf(chessboard.DeathBlack.get(i).getCost()));
                ((BufferedWriter) writer).newLine();
            }
            writer.write(String.valueOf(chessboard.DeathWhite.size()));
            ((BufferedWriter) writer).newLine();
            for (int i = 0; i < chessboard.DeathWhite.size(); i++)
            {
                writer.write(String.valueOf(chessboard.DeathWhite.get(i).getCost()));
                ((BufferedWriter) writer).newLine();
            }

        }
        catch (Exception e)
        {
            System.out.println("ERROR");
        }
    }
    public void load() throws FileNotFoundException {
        ChessApp.GroupPieces.getChildren().clear();
        chessboard.activeBlack.clear();
        chessboard.activeWhite.clear();
        chessboard.DeathWhite.clear();
        chessboard.DeathBlack.clear();
        Whites.getChildren().clear();
        Blacks.getChildren().clear();
        undo.clear();
        redo.clear();
        w = b = 0;
        FastReader fastReader = new FastReader();
        for (int i = 0 ; i < WIDTH; i++)
        {
            for (int j = 0; j < HEIGHT; j++)
            {
                initialize(i, j, fastReader.nextInt());
            }
        }
        int Death = fastReader.nextInt();
        for (int i = 0; i < Death; i++)
        {
            helpingfunc(fastReader.nextInt());
        }
        Death = fastReader.nextInt();
        for (int i = 0; i < Death; i++)
        {
            helpingfunc(fastReader.nextInt());
        }
    }
    private void helpingfunc(int val) throws FileNotFoundException {
        if (100 == Math.abs(val))
        {
            if(val > 0)
            {
                chessboard.DeathWhite.add(new pawn(chessboard));

            }
            else
            {

                chessboard.DeathBlack.add(new pawn(chessboard));
            }
        }
        else if (Math.abs(val) == 320)
        {
            if(val > 0)
            {
                chessboard.DeathWhite.add(new knight(chessboard));
            }
            else
            {

                chessboard.DeathBlack.add(new knight(chessboard));
            }
        }
        else if(Math.abs(val) == 330)
        {
            if(val > 0)
            {
                chessboard.DeathWhite.add(new bishop(chessboard));
            }
            else
            {

                chessboard.DeathBlack.add(new bishop(chessboard));
            }
        }
        else if(Math.abs(val) == 500)
        {
            if(val > 0)
            {
                chessboard.DeathWhite.add(new rook(chessboard));
            }
            else
            {

                chessboard.DeathBlack.add(new rook(chessboard));
            }
        }
        else if (Math.abs(val) == 900)
        {
            if(val > 0)
            {
                chessboard.DeathWhite.add(new queen(chessboard));
            }
            else
            {

                chessboard.DeathBlack.add(new queen(chessboard));
            }
        }
        if (val > 0)
        {
            chessboard.DeathWhite.get(chessboard.DeathWhite.size() - 1).setColor(1);
            chessboard.DeathWhite.get(chessboard.DeathWhite.size() - 1).setPieceSquareValue();
            chessboard.DeathWhite.get(chessboard.DeathWhite.size() - 1).setCost(val);
            ImageView imageView = new ImageView(new Image(new FileInputStream(chessboard.DeathWhite.get(chessboard.DeathWhite.size() - 1).getLocation())));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(MAXSIZE / 2);
            imageView.setFitWidth(MAXSIZE / 2);
            imageView.setY(w * MAXSIZE / 2);
            chessboard.DeathWhite.get(chessboard.DeathWhite.size() - 1).setImageView(imageView);
            Whites.getChildren().add(imageView);
            w++;

        }
        else
        {
            chessboard.DeathBlack.get(chessboard.DeathBlack.size() - 1).setColor(-1);
            chessboard.DeathBlack.get(chessboard.DeathBlack.size() - 1).setPieceSquareValue();
            chessboard.DeathBlack.get(chessboard.DeathBlack.size() - 1).setCost(val);
            ImageView imageView = new ImageView(new Image(new FileInputStream(chessboard.DeathBlack.get(chessboard.DeathBlack.size() - 1).getLocation())));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(MAXSIZE / 2);
            imageView.setFitWidth(MAXSIZE / 2);
            imageView.setY(b * MAXSIZE / 2);
            chessboard.DeathBlack.get(chessboard.DeathBlack.size() - 1).setImageView(imageView);
            Blacks.getChildren().add(imageView);
            b++;
        }

    }

    private void initialize(int i, int j, int val) throws FileNotFoundException {
        if (val == 0)
        {
            this.chessboard.board[i][j].setP(null);
        }
        else
        {
            if (100 == Math.abs(val))
            {
                chessboard.board[i][j].setP(new pawn(chessboard));
            }
            else if (Math.abs(val) == 320)
            {
                chessboard.board[i][j].setP(new knight(chessboard));
            }
            else if(Math.abs(val) == 330)
            {
                chessboard.board[i][j].setP(new bishop(chessboard));
            }
            else if(Math.abs(val) == 500)
            {
                chessboard.board[i][j].setP(new rook(chessboard));
            }
            else if (Math.abs(val) == 900)
            {
                chessboard.board[i][j].setP(new queen(chessboard));
            }
            else
            {
                chessboard.board[i][j].setP(new king(chessboard));
                if (val > 0)
                {
                    chessboard.setWhiteKing(new position(i, j));
                }
                else
                {
                    chessboard.setBlackKing(new position(i, j));
                }
            }
            chessboard.board[i][j].getP().setCost(val);
            chessboard.board[i][j].getP().setPieceSquareValue();
            chessboard.board[i][j].getP().setPos(new position(i, j));
            if (val > 0)
            {
                chessboard.board[i][j].getP().setColor(1);
                chessboard.activeWhite.add(chessboard.board[i][j].getP());
            }
            else
            {
                chessboard.board[i][j].getP().setColor(-1);
                chessboard.activeBlack.add(chessboard.board[i][j].getP());
            }
            ImageView imageView = new ImageView(new Image(new FileInputStream(chessboard.board[i][j].getP().getLocation())));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(MAXSIZE);
            imageView.setFitWidth(MAXSIZE);
            imageView.setX(j * MAXSIZE);
            imageView.setY(i * MAXSIZE);
            chessboard.board[i][j].getP().setImageView(imageView);
            ChessApp.GroupPieces.getChildren().add(imageView);
            imageView.setOnMouseClicked(e ->{
                if(AttackImg(imageView))
                {
                    this.clearRedo();
                }
            });
        }
    }
}

