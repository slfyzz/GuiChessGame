package sample.Ai.engine;

import sample.Board.chessboard;
import sample.Board.position;
import sample.Gui.ChessApp;

public class Evaluation {
    private final int checkBonus = 1000;
    private final int checkMateBonus = 100000;
    private int WhiteEvaluation;
    private int BlackEvaluation;
    private boolean whiteCheckMate;
    private boolean blackCheckMate;
    private boolean whiteCheck;
    private boolean blackCheck;
    private chessboard chessboard;
    public Evaluation(chessboard chess)
    {
        this.chessboard = chess;
        WhiteEvaluation = 0;
        BlackEvaluation = 0;
    }

    public void setWhiteCheckMate(boolean whiteCheckMate) {
        this.whiteCheckMate = whiteCheckMate;
    }

    public void setBlackCheck(boolean blackCheck) {
        this.blackCheck = blackCheck;
    }

    public void setBlackCheckMate(boolean blackCheckMate) {
        this.blackCheckMate = blackCheckMate;
    }

    public void setWhiteCheck(boolean whiteCheck) {
        this.whiteCheck = whiteCheck;
    }
    public int getCheckBonus() {
        return checkBonus;
    }

    public int getCheckMateBonus() {
        return checkMateBonus;
    }
    private void boardEvaluation()
    {

        for (int x = 0; x < chessboard.activeWhite.size(); x++)
        {
            int i = chessboard.activeWhite.get(x).getPos().getX();
            int j = chessboard.activeWhite.get(x).getPos().getY();
            if (chessboard.board[i][j].getP() != null)
            {
                WhiteEvaluation += chessboard.board[i][j].getP().getCost();
                WhiteEvaluation += chessboard.board[i][j].getP().getTheValueAtThatPosition(new position(i,j));

            }
        }
        for (int x = 0; x < chessboard.activeBlack.size(); x++)
        {
            int i = chessboard.activeBlack.get(x).getPos().getX();
            int j = chessboard.activeBlack.get(x).getPos().getY();
            if (chessboard.board[i][j].getP() != null)
            {
                BlackEvaluation += chessboard.board[i][j].getP().getCost();
                BlackEvaluation -= chessboard.board[i][j].getP().getTheValueAtThatPosition(new position(i,j));

            }
        }
    }


    public int getEvaluation()
    {
        boardEvaluation();

       /* if (whiteCheck)
            BlackEvaluation -= getCheckBonus();

        if(whiteCheckMate)
            BlackEvaluation -= getCheckMateBonus();

        if (blackCheck)
            WhiteEvaluation += getCheckBonus();

        if(blackCheckMate)
            WhiteEvaluation += getCheckMateBonus();*/

        return BlackEvaluation + WhiteEvaluation;
    }

}
