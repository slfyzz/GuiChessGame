package sample.pieces;
import sample.Board.chessboard;
import sample.Board.position;

public class rook extends piece {
    private String WhiteLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_rlt60.png";
    private String BlackLoc = "C:\\Users\\3arrows\\IdeaProjects\\cheesGamePlay\\src\\sample\\pieces\\pics\\Chess_rdt60.png";
    private chessboard chessboard;
    private int [][]pieceSquareValue;

    public rook(chessboard chess)
    {
        this.chessboard = chess;
        super.setChessboard(chess);
    }

    @Override
    public void setPieceSquareValue() {
        this.pieceSquareValue = new int[][]{
                {0,  0,  0,  0,  0,  0,  0,  0},
                {5, 10, 10, 10, 10, 10, 10,  5},
                {-5,  0,  0,  0,  0,  0,  0, -5},
                {-5,  0,  0,  0,  0,  0,  0, -5},
                {-5,  0,  0,  0,  0,  0,  0, -5},
                {-5,  0,  0,  0,  0,  0,  0, -5},
                {-5,  0,  0,  0,  0,  0,  0, -5},
                {0,  0,  0,  5,  5,  0,  0,  0}
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
        if(super.validMove(newpos))
        {
            if(newpos.getY() != getPos().getY() && newpos.getX() == getPos().getX()) {
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
            else
                return false;
        }
        else
            return false;
    }
}

