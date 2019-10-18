package sample.pieces;

import sample.Board.chessboard;
import sample.Board.position;

public class knight extends piece{
    private String WhiteLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_nlt60.png";
    private String BlackLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_ndt60.png";
    private int [][]pieceSquareValue;

    private sample.Board.chessboard chessboard;
    public knight(chessboard chess)
    {
        this.chessboard = chess;
        super.setChessboard(chess);
    }

    @Override
    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[][]{
                {-50,-40,-30,-30,-30,-30,-40,-50},
                {-40,-20,  0,  0,  0,  0,-20,-40},
                {-30,  0, 10, 15, 15, 10,  0,-30},
                {-30,  5, 15, 20, 20, 15,  5,-30},
                {-30,  0, 15, 20, 20, 15,  0,-30},
                {-30,  5, 10, 15, 15, 10,  5,-30},
                {-40,-20,  0,  5,  5,  0,-20,-40},
                {-50,-40,-30,-30,-30,-30,-40,-50},
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
            if (Math.abs(newpos.getX() - this.getPos().getX()) == 2 && Math.abs(newpos.getY() - this.getPos().getY()) == 1)
            {
                return true;
            }
            else if (Math.abs(newpos.getX() - this.getPos().getX()) == 1 && Math.abs(newpos.getY() - this.getPos().getY()) == 2)
            {
                return true;
            }
        }
        return false;
    }
}
