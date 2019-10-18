package sample.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import sample.Board.chessboard;
import sample.Board.position;
import sample.Gui.ChessApp;

import java.io.FileInputStream;

import static sample.Gui.ChessApp.MAXSIZE;

public class pawn extends piece {
    private String WhiteLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_plt60.png";
    private String BlackLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_pdt60.png";
    private boolean firstMove;
    private chessboard chessboard;
    private int [][]pieceSquareValue;

    public pawn(chessboard chess)
    {
        this.chessboard = chess;
        firstMove = true;
        super.setChessboard(chess);
    }

    @Override
    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[][]{
                {0,  0,  0,  0,  0,  0,  0,  0},
                {50, 50, 50, 50, 50, 50, 50, 50},
                {10, 10, 20, 30, 30, 20, 10, 10},
                {5,  5, 10, 25, 25, 10,  5,  5},
                {0,  0,  0, 20, 20,  0,  0,  0},
                {5, -5,-10,  0,  0,-10, -5,  5},
                {5, 10, 10,-20,-20, 10, 10,  5},
                {0,  0,  0,  0,  0,  0,  0,  0}
        };
    }
    @Override
    public int getTheValueAtThatPosition(position pos) {
        if (this.getColor() == black)
        {
            return this.pieceSquareValue[7 - pos.getX()][pos.getY()];
        }
        else
            return this.pieceSquareValue[pos.getX()][pos.getY()];
    }

    @Override
    public String getLocation() {
        if(getColor() == 1)
            return WhiteLoc;
        else
            return BlackLoc;
    }



    @Override
    public boolean validMove(position newpos) {
        if (super.validMove(newpos))
        {
            if ((newpos.getX()-getPos().getX() == 2 && getColor() == black  ||
                    newpos.getX()-getPos().getX() == -2 && getColor() == white) &&
                    newpos.getY() == getPos().getY())
            {
                if(firstMove) {
                    if ((getColor() == black && chessboard.board[getPos().getX()+1][getPos().getY()].getP() != null) ||
                            (getColor() == white && chessboard.board[getPos().getX()-1][getPos().getY()].getP() != null))
                        return false;

                    return true;
                }
            }
            if (((newpos.getX() - getPos().getX() == 1 && getColor() == black)  ||
                    (newpos.getX() - getPos().getX() == -1 && getColor() == white)) &&
                    newpos.getY() == getPos().getY())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean Move(position newpos) {
        if (validMove(newpos) && super.Move(newpos))
        {
            firstMove = false;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean virtualMove(position newPos) {
        if ((this.getPos().getX() == 1 && this.getColor()== -1) || (this.getPos().getX() == 7 && this.getColor()==1))
        {
            this.firstMove = true;
        }
        if (this.validMove(newPos) ||
                ((newPos.getX()-getPos().getX() == 1 && Math.abs(newPos.getY() - getPos().getY()) == 1 && getColor() == black)||
                (newPos.getX()-getPos().getX() == -1 && Math.abs(newPos.getY() - getPos().getY()) == 1 && getColor() == white)))
        {
            position oldOne = this.getPos();
            boolean isItProblem = false;
            piece attackedOne = chessboard.board[newPos.getX()][newPos.getY()].getP();
            chessboard.board[newPos.getX()][newPos.getY()].setP(chessboard.board[this.getPos().getX()][this.getPos().getY()].getP());
            chessboard.board[this.getPos().getX()][this.getPos().getY()].setP(null);
            this.setPos(newPos);
            if (chessboard.checkAlert())
            {
                isItProblem = true;
            }
            chessboard.board[oldOne.getX()][oldOne.getY()].setP(chessboard.board[this.getPos().getX()][this.getPos().getY()].getP());
            chessboard.board[this.getPos().getX()][this.getPos().getY()].setP(attackedOne);
            this.setPos(oldOne);
            return !isItProblem;

        }
        else
            return false;
    }

    @Override
    public boolean attack(position newpos) {
        if (super.validMove(newpos))
        {
            if ((newpos.getX()-getPos().getX() == 1 && Math.abs(newpos.getY() - getPos().getY()) == 1 && getColor() == black)||
                    (newpos.getX()-getPos().getX() == -1 && Math.abs(newpos.getY() - getPos().getY()) == 1 && getColor() == white))
            {
                if (chessboard.board[newpos.getX()][newpos.getY()].getP().getColor() != this.getColor())
                {
                    firstMove = false;
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
                    chessboard.board[getPos().getX()][getPos().getY()].setP(null);
                    this.setPos(newpos);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean validAttack(position newpos)
    {
        if (((newpos.getX() - getPos().getX() == 1 && getColor() == black)  ||
                (newpos.getX() - getPos().getX() == -1 && getColor() == white)) &&
                Math.abs(newpos.getY() - getPos().getY()) == 1 &&
                chessboard.board[newpos.getX()][newpos.getY()].getP() != null &&
                chessboard.board[newpos.getX()][newpos.getY()].getP().getColor() != this.getColor() &&
                super.validMove(newpos) &&
                this.virtualMove(newpos))
        {
            return true;
        }
        return false;
    }


}

