package sample.pieces;

import javafx.scene.paint.Paint;
import sample.Board.chessboard;
import sample.Board.position;

public class king extends piece {
    private String BlackLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_kdt60.png";
    private String WhiteLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_klt60.png";
    private int [][]pieceSquareValue;

    private chessboard chessboard;
    public king(chessboard chess)
    {
        this.chessboard = chess;
        super.setChessboard(chess);
    }

    @Override
    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[][]{
                {-30,-40,-40,-50,-50,-40,-40,-30},
                {-30,-40,-40,-50,-50,-40,-40,-30},
                {-30,-40,-40,-50,-50,-40,-40,-30},
                {-30,-40,-40,-50,-50,-40,-40,-30},
                {-20,-30,-30,-40,-40,-30,-30,-20},
                {-10,-20,-20,-20,-20,-20,-20,-10},
                {20, 20,  0,  0,  0,  0, 20, 20},
                {20, 30, 10,  0,  0, 10, 30, 20}
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
            if (Math.abs(newpos.getX() - getPos().getX()) <= 1 && Math.abs(newpos.getY() - getPos().getY()) <= 1)
            {
                if (Math.abs(newpos.getX() - getPos().getX()) == Math.abs(newpos.getY() - getPos().getY()) && Math.abs(newpos.getY() - getPos().getY()) == 0)
                    return false;

                return true;
            }
        }
        return false;
    }
}

