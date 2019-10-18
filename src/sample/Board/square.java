package sample.Board;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Ai.engine.Ai;
import sample.Ai.engine.ValidMoves;
import sample.Gui.ChessApp;
import sample.pieces.*;

import static sample.Gui.ChessApp.*;

public class square extends Rectangle {
    private position pos;
    private piece p;
    chessboard chessboard;
    public square(int color,position newpos, chessboard chess)
    {
        //initialize the square
        chessboard = chess;
        this.pos = newpos;
        setWidth(ChessApp.MAXSIZE);
        setHeight(ChessApp.MAXSIZE );
        setX(newpos.getX() * ChessApp.MAXSIZE);
        setY(newpos.getY() * ChessApp.MAXSIZE);
        setFill(color == 1 ? Color.valueOf("#ffffff") : Color.valueOf("#CD853F"));
        this.p = null;
    }
    public piece getP() {
        return p;
    }

    public void setP(piece p) {
        this.p = p;
    }
    public void initialization()
    {
        if (this.pos.getX() == 1)
        {
            this.p = new pawn(this.chessboard);
            this.p.setColor(-1);
            this.p.setPos(this.pos);
            this.p.setCost(-100);
            this.p.setPieceSquareValue();
        }
        else if(this.pos.getX() == 6)
        {
            this.p = new pawn(this.chessboard);
            this.p.setColor(1);
            this.p.setPos(this.pos);
            this.p.setCost(100);
            this.p.setPieceSquareValue();
        }
        else if(this.pos.getX() == 0 || this.pos.getX() == 7)
        {
            if(this.pos.getY() == 0 || this.pos.getY() == 7)
            {
                this.p = new rook(this.chessboard);
                this.p.setCost(500);
            }
            else if(this.pos.getY() == 1 || this.pos.getY() == 6)
            {
                this.p = new knight(this.chessboard);
                this.p.setCost(320);
            }
            else if(this.pos.getY() == 2 || this.pos.getY() == 5)
            {
                this.p = new bishop(this.chessboard);
                this.p.setCost(330);
            }
            else if(this.pos.getY() == 3)
            {
                this.p = new queen(this.chessboard);
                this.p.setCost(900);
            }
            else if(this.pos.getY() == 4)
            {
                this.p = new king(this.chessboard);
                this.p.setCost(600000);
            }
            if(this.pos.getX() == 0) {
                p.setColor(-1);
                p.setCost(p.getCost() * -1);
            }
            else
                p.setColor(1);

            this.p.setPieceSquareValue();
            this.p.setPos(this.pos);
        }
        else
            this.setP(null);
    }

}

