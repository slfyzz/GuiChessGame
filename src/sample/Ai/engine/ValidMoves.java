package sample.Ai.engine;

import sample.Board.position;
import sample.pieces.piece;

public class ValidMoves {
    private position oldPos;
    private position newPos;
    private piece attackedPiece;

    public ValidMoves(position oldPos, position newPos)
    {
        this.newPos = newPos;
        this.oldPos = oldPos;
    }

    public void setAttackedPiece(piece attackedPiece) {
        this.attackedPiece = attackedPiece;
    }

    public piece getAttackedPiece() {
        return attackedPiece;
    }

    public position getNewPos() {
        return newPos;
    }

    public position getOldPos() {
        return oldPos;
    }

    public void setNewPos(position newPos) {
        this.newPos = newPos;
    }

    public void setOldPos(position oldPos) {
        this.oldPos = oldPos;
    }
}
