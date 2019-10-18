package sample.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Board.chessboard;
import sample.Board.position;
import sample.Gui.ChessApp;

import java.io.FileInputStream;

import static sample.Gui.ChessApp.MAXSIZE;

public class piece {
    private position pos;
    private int color;
    final static int black = -1;
    final static int white = 1;
    private static int q = 0;
    private int cost;
    private chessboard chessboard;
    private int [][]pieceSquareValue;
    private ImageView imageView;

    public void setChessboard(sample.Board.chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[8][8];
    }

    public int getTheValueAtThatPosition(position pos)
    {
        if (this.getColor() == black)
        {
            return this.pieceSquareValue[7 - pos.getX()][pos.getY()];
        }
        else
            return this.pieceSquareValue[pos.getX()][pos.getY()];
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public String getLocation(){return null;}
    public boolean validMove(position newpos)
    {
        if(newpos.getX() >= 8 ||
                newpos.getX() < 0 ||
                newpos.getY() >= 8 ||
                newpos.getY() < 0 ||
                chessboard.getTurn() != this.color ||
                (chessboard.board[newpos.getX()][newpos.getY()].getP() != null &&
                        chessboard.board[newpos.getX()][newpos.getY()].getP().getColor() == this.getColor() ))
        {
            return false;
        }
        return  true;
    }
    public boolean Move(position newpos)
    {
        if(validMove(newpos))
        {
            chessboard.board[newpos.getX()][newpos.getY()].setP(chessboard.board[this.pos.getX()][this.pos.getY()].getP());
            System.out.println(chessboard.board[newpos.getX()][newpos.getY()].getP());
            chessboard.board[this.pos.getX()][this.pos.getY()].setP(null);
            System.out.println(chessboard.board[this.pos.getX()][this.pos.getY()].getP());
            this.pos = newpos;
            return true;
        }
        return false;
    }

    public boolean virtualMove(position newPos)
    {
       /* q++;
        if (q == 7)
        {
            System.out.println("h");
        }*/
        position kingpos = null;

        if (validMove(newPos) &&
                (chessboard.board[newPos.getX()][newPos.getY()].getP() == null ||
                        chessboard.board[newPos.getX()][newPos.getY()].getP().getColor() != this.getColor()))
        {
            if (chessboard.board[this.pos.getX()][this.pos.getY()].getP().getClass() == king.class)
            {
                if (chessboard.board[this.pos.getX()][this.pos.getY()].getP().getColor() == 1)
                {
                    kingpos = chessboard.getWhiteKing();
                    chessboard.setWhiteKing(newPos);
                }else
                {
                    kingpos = chessboard.getBlackKing();
                    chessboard.setBlackKing(newPos);
                }
            }
            position oldOne = this.pos;
            boolean isItProblem = false;
            piece attackedOne = chessboard.board[newPos.getX()][newPos.getY()].getP();
            chessboard.board[newPos.getX()][newPos.getY()].setP(chessboard.board[this.pos.getX()][this.pos.getY()].getP());
            chessboard.board[this.pos.getX()][this.pos.getY()].setP(null);
            this.pos = newPos;
            if (chessboard.checkAlert())
            {
                isItProblem = true;
            }
            chessboard.board[oldOne.getX()][oldOne.getY()].setP(chessboard.board[this.pos.getX()][this.pos.getY()].getP());
            chessboard.board[this.pos.getX()][this.pos.getY()].setP(attackedOne);
            this.pos = oldOne;
            if (chessboard.board[this.pos.getX()][this.pos.getY()].getP().getClass() == king.class)
            {
                if (chessboard.board[this.pos.getX()][this.pos.getY()].getP().getColor() == 1)
                {
                    chessboard.setWhiteKing(kingpos);
                }else
                {
                    chessboard.setBlackKing(kingpos);
                }
            }
            return !isItProblem;
        }
        else
            return false;
    }
    public boolean attack(position newpos)
    {
        if(validMove(newpos) && chessboard.board[newpos.getX()][newpos.getY()].getP().color != this.color)
        {
            if(chessboard.getTurn() == 1)
            {
                chessboard.DeathBlack.add(chessboard.board[newpos.getX()][newpos.getY()].getP());
                for(int i = 0; i < chessboard.activeBlack.size(); i++)
                {
                    if(chessboard.activeBlack.get(i) == chessboard.board[newpos.getX()][newpos.getY()].getP())
                    {
                        chessboard.activeBlack.remove(i);
                        break;
                    }
                }
                try {
                    ImageView img = new ImageView(new Image(new FileInputStream(chessboard.board[newpos.getX()][newpos.getY()].getP().getLocation())));
                    img.setY(ChessApp.b * MAXSIZE / 2);
                    ChessApp.b++;
                    ChessApp.Blacks.getChildren().add(img);
                    img.setPreserveRatio(true);
                    img.setFitHeight(MAXSIZE / 2);
                    img.setFitWidth(MAXSIZE / 2);
                }catch (Exception e)
                {
                    System.out.println("Error");
                }
            }
            else
            {
                chessboard.DeathWhite.add(chessboard.board[newpos.getX()][newpos.getY()].getP());
                for(int i = 0; i < chessboard.activeWhite.size(); i++)
                {
                    if(chessboard.activeWhite.get(i) == chessboard.board[newpos.getX()][newpos.getY()].getP())
                    {
                        chessboard.activeWhite.remove(i);
                        break;
                    }
                }
                try {
                    ImageView img = new ImageView(new Image(new FileInputStream(chessboard.board[newpos.getX()][newpos.getY()].getP().getLocation())));
                    img.setY(ChessApp.w * MAXSIZE / 2);
                    ChessApp.w++;
                    ChessApp.Whites.getChildren().add(img);
                    img.setPreserveRatio(true);
                    img.setFitHeight(MAXSIZE / 2);
                    img.setFitWidth(MAXSIZE / 2);
                }catch (Exception e)
                {
                    System.out.println("Error");
                }
            }
            chessboard.board[newpos.getX()][newpos.getY()].setP(this);
            chessboard.board[pos.getX()][pos.getY()].setP(null);
            this.pos = newpos;
            return true;
        }
        return false;
    }
    public boolean validAttack(position newpos)
    {
        return true;
    }
    public position getPos() {
        return pos;
    }

    public void setPos(position pos) {
        this.pos = pos;
    }

    public int getColor() {
        return color;
    }


}

