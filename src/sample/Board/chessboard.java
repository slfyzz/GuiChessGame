package sample.Board;

import sample.Gui.ChessApp;
import sample.pieces.*;

import java.util.LinkedList;
import java.util.List;

public class chessboard {
    public square board[][];
    public List<piece>DeathWhite;
    public List<piece>DeathBlack;
    public List<piece>activeWhite;
    public List<piece>activeBlack;
    private int turn;
    private position WhiteKing;
    private position BlackKing;
    private  int debug = 0;
    public chessboard()
    {
        board = new square[8][8];
        DeathBlack = new LinkedList<>();
        DeathWhite = new LinkedList<>();
        activeBlack = new LinkedList<>();
        activeWhite = new LinkedList<>();
        turn = -1;
        BlackKing = new position(0, 4);
        WhiteKing = new position(7, 4);
    }

    public  position getBlackKing() {
        return BlackKing;
    }

    public  position getWhiteKing() {
        return WhiteKing;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public  void reverse() {
        this.turn *= -1;
    }

    public  void setBlackKing(position blackKing) {
        BlackKing = blackKing;
    }

    public  void setWhiteKing(position whiteKing) {
        WhiteKing = whiteKing;
    }

    public  int getTurn() {
        return turn;
    }

    public  boolean checkAlert()
    {
       /* debug++;
        if (debug == 3)
        {
            System.out.println("hey");
        }*/
        position kingspos;
        if (getTurn() == 1)
            kingspos = WhiteKing;
        else
            kingspos = BlackKing;

        for (int i = 0; i < ChessApp.WIDTH; i++)
        {
            for (int j = 0; j < ChessApp.HEIGHT; j++)
            {
                if (board[i][j].getP() != null && board[i][j].getP().getColor() != getTurn())
                {
                    turn *= -1;
                    if (board[i][j].getP().validMove(kingspos) && board[i][j].getP().getClass() != pawn.class)
                    {
                        turn *= -1;
                        return true;
                    }
                    else if (board[i][j].getP().getClass() == pawn.class && board[i][j].getP().validAttack(kingspos))
                    {
                        turn *= -1;
                        return true;
                    }
                    turn *= -1;
                }
            }
        }
        return false;
    }
    //HERE IS THE PROBLEEMMMM
    public  boolean canTheKingMove()
    {
        position kingspos;
        if (getTurn() == 1)
            kingspos = WhiteKing;
        else
            kingspos = BlackKing;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j && i == 0) {
                    continue;
                }
                if (this.board[kingspos.getX()][kingspos.getY()].getP().virtualMove(new position(kingspos.getX() + i, kingspos.getY() + j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public  boolean canTheKillerbeKilled(position killer, boolean pawnMove)
    {
        for (int i = 0; i < ChessApp.WIDTH; i++)
        {
            for (int j = 0; j < ChessApp.HEIGHT; j++)
            {
                if (this.board[i][j].getP() != null && this.board[i][j].getP().getColor() == this.getTurn())
                {
                    if (this.board[i][j].getP().getClass() != pawn.class &&
                           this.board[i][j].getP().getClass() != king.class &&
                    this.board[i][j].getP().validMove(killer))
                    {
                        return true;
                    }
                    else if (this.board[i][j].getP().getClass() == pawn.class &&
                            ((this.board[i][j].getP().validMove(killer) && pawnMove )||
                                    this.board[i][j].getP().validAttack(killer)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public  boolean canWeBlockTheThreat(position killer)
    {
        if (this.board[killer.getX()][killer.getY()].getP().getClass() == pawn.class ||
            this.board[killer.getX()][killer.getY()].getP().getClass() == knight.class ||
                this.board[killer.getX()][killer.getY()].getP().getClass() == king.class)
            return false;

        position kingspos;
        if (getTurn() == 1)
            kingspos = WhiteKing;
        else
            kingspos = BlackKing;

        int coord[] = {0,0};
        if (kingspos.getX() > killer.getX())
            coord[0] = -1;

        else if (kingspos.getX() < killer.getX())
            coord[0] = 1;

        if (kingspos.getY() > killer.getY())
            coord[1] = -1;

        else if (kingspos.getY() < killer.getY())
            coord[1] = 1;

        int x = kingspos.getX();
        int y = kingspos.getY();
        while (x + coord[0] != killer.getX() || y + coord[1] != killer.getY())
        {
            x += coord[0];
            y += coord[1];
            if (canTheKillerbeKilled(new position(x, y), true))
            {
                return true;
            }
        }
        return false;
    }
    public  boolean checkMate(position killer)
    {

        if (canTheKillerbeKilled(killer, false) || canTheKingMove() || canWeBlockTheThreat(killer))
            return false;

        else
            return true;


    }

}

