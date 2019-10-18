package sample.pieces;

import sample.Board.chessboard;
import sample.Board.position;

public class queen extends piece {
    private String WhiteLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_qlt60.png";
    private String BlackLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_qdt60.png";
    private chessboard chessboard;
    private int [][]pieceSquareValue;

    public queen(chessboard chess)
    {
        this.chessboard = chess;
        super.setChessboard(chess);
    }

    @Override
    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[][]{
                {-20,-10,-10, -5, -5,-10,-10,-20},
                {-10,  0,  0,  0,  0,  0,  0,-10},
                {-10,  0,  5,  5,  5,  5,  0,-10},
                {-5,  0,  5,  5,  5,  5,  0, -5},
                {0,  0,  5,  5,  5,  5,  0, -5},
                {-10,  5,  5,  5,  5,  5,  0,-10},
                {-10,  0,  5,  0,  0,  0,  0,-10},
                {-20,-10,-10, -5, -5,-10,-10,-20}
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

    public String getLocation() {
        if(getColor() == 1)
            return WhiteLoc;
        else
            return BlackLoc;
    }

    @Override
    public boolean validMove(position newpos){
        if (super.validMove(newpos))
        {
            if ((double)Math.abs(newpos.getY() - getPos().getY()) / (double)Math.abs(newpos.getX() - getPos().getX()) == 1)
            {
                int i,j;
                int x = getPos().getX();
                int y = getPos().getY();
                if(newpos.getY() - getPos().getY() > 0){
                    j = 1;
                }
                else
                    j = -1;
                if (newpos.getX() - getPos().getX() > 0)
                    i = 1;
                else
                    i = -1;
                while (x + i != newpos.getX() && y + j != newpos.getY())
                {
                    x += i;
                    y += j;
                    if (chessboard.board[x][y].getP() != null)
                        return false;
                }
                return true;
            }

            else if (newpos.getY() != getPos().getY() && newpos.getX() == getPos().getX())
            {
                int min = Math.min(newpos.getY(),getPos().getY());
                int max = Math.max(newpos.getY(),getPos().getY());
                min++;
                while (min != max)
                {
                    if(chessboard.board[newpos.getX()][min].getP() != null)
                        return false;
                    min++;
                }
                return true;
            }
            else if(newpos.getY() == getPos().getY() && newpos.getX() != getPos().getX())
            {
                int min = Math.min(newpos.getX(),getPos().getX());
                int max = Math.max(newpos.getX(),getPos().getX());
                min++;
                while (min != max)
                {
                    if(chessboard.board[min][newpos.getY()].getP() != null)
                        return false;
                    min++;
                }
                return true;
            }
        }

        return false;
    }
}

