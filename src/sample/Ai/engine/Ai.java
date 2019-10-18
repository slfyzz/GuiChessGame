package sample.Ai.engine;

import sample.Board.chessboard;
import sample.Board.position;
import sample.Gui.ChessApp;
import sample.pieces.king;
import sample.pieces.pawn;
import sample.pieces.piece;
import java.util.LinkedList;
import java.util.List;

public class Ai {
    private sample.Board.chessboard chessboard;
    private List<ValidMoves> []validMoves;
    private int color;
    private int MAXDEPTH;
    private ValidMoves bestPosition;
    private boolean whiteCheckMate;
    private boolean blackCheckMate;
    private boolean whiteCheck;
    private boolean blackCheck;

    public Ai(chessboard chess, int color, int depth)
    {
        this.chessboard = chess;
        this.color = color;
        this.MAXDEPTH = depth;
        validMoves = new LinkedList[MAXDEPTH + 1];
        whiteCheckMate = false;
        blackCheckMate = false;
        whiteCheck = false;
        blackCheck = false;
    }
    public ValidMoves executeAi()
    {
        if(this.color == 1)
        {
            max(MAXDEPTH, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        else
            mini(MAXDEPTH, Integer.MAX_VALUE, Integer.MIN_VALUE);

        chessboard.setTurn(this.color);
        return getBestPosition();
    }

    public ValidMoves getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(ValidMoves bestPosition) {
        this.bestPosition = bestPosition;
    }

    private void generateValidMoves(int ind, int color)
    {
        List<piece> list;
        if (color == 1)
            list = chessboard.activeWhite;
        else
            list = chessboard.activeBlack;

        for(int z = 0; z < list.size(); z++) {
            int i = list.get(z).getPos().getX();
            int j = list.get(z).getPos().getY();
            for (int x = 0; x < ChessApp.WIDTH; x++) {
                for (int y = 0; y < ChessApp.HEIGHT; y++) {
                    if (x == i && y == j)
                        continue;
                    if (this.chessboard.board[i][j].getP() != null) {
                        if (this.chessboard.board[i][j].getP().getClass() != pawn.class &&
                                this.chessboard.board[i][j].getP().validMove(new position(x, y)) &&
                                this.chessboard.board[i][j].getP().virtualMove(new position(x, y))) {
                            if (chessboard.board[i][j].getP().getColor() == 1)
                                validMoves[ind].add(new ValidMoves(new position(i, j), new position(x, y)));
                            else
                                validMoves[ind].add(new ValidMoves(new position(i, j), new position(x, y)));
                        } else if (this.chessboard.board[i][j].getP().getClass() == pawn.class &&
                                ((this.chessboard.board[i][j].getP().validMove(new position(x, y)) &&
                                        this.chessboard.board[i][j].getP().virtualMove(new position(x, y)) &&
                                        this.chessboard.board[x][y].getP() == null) ||
                                        this.chessboard.board[i][j].getP().validAttack(new position(x, y)))) {
                            if (chessboard.board[i][j].getP().getColor() == 1)
                                validMoves[ind].add(new ValidMoves(new position(i, j), new position(x, y)));
                            else
                                validMoves[ind].add(new ValidMoves(new position(i, j), new position(x, y)));
                        }
                    }
                }
            }
        }
    }
    private void Aimove(position oldpos, position newpos)
    {
        if (chessboard.board[oldpos.getX()][oldpos.getY()].getP().getClass() == king.class)
        {
            if (chessboard.board[oldpos.getX()][oldpos.getY()].getP().getColor() == 1)

                chessboard.setWhiteKing(newpos);

            else
                chessboard.setBlackKing(newpos);

        }
        chessboard.board[newpos.getX()][newpos.getY()].setP(chessboard.board[oldpos.getX()][oldpos.getY()].getP());
        chessboard.board[oldpos.getX()][oldpos.getY()].setP(null);
        chessboard.board[newpos.getX()][newpos.getY()].getP().setPos(newpos);
    }

    public int mini(int depth, int beta, int alpha)
    {
        if (depth == 0)
        {
            Evaluation evaluation = new Evaluation(this.chessboard);
            evaluation.setBlackCheck(blackCheck);
            evaluation.setBlackCheckMate(blackCheckMate);
            evaluation.setWhiteCheck(whiteCheck);
            evaluation.setWhiteCheckMate(whiteCheckMate);
            return evaluation.getEvaluation();
        }
        else
        {
            validMoves[depth] = new LinkedList<>();
            chessboard.setTurn(-1);
            generateValidMoves(depth, -1);
            int minVal = Integer.MAX_VALUE;
            for (int i = 0; i < validMoves[depth].size(); i++)
            {
                position oldpos = validMoves[depth].get(i).getOldPos();
                position newpos = validMoves[depth].get(i).getNewPos();
                piece oldpiece = chessboard.board[newpos.getX()][newpos.getY()].getP();
                this.Aimove(oldpos,newpos);
                if (chessboard.checkAlert())
                {
                    if (chessboard.checkMate(newpos))
                    {
                        whiteCheckMate = true;
                    }
                    whiteCheck = true;
                }
                int evel = max(depth - 1, beta, alpha);
                if (depth == MAXDEPTH && evel < minVal)
                {
                    this.bestPosition = validMoves[depth].get(i);
                }
                minVal = Math.min(evel, minVal);
                beta = Math.min(beta, evel);
                this.Aimove(newpos,oldpos);
                chessboard.board[newpos.getX()][newpos.getY()].setP(oldpiece);
                whiteCheck = false;
                whiteCheckMate = false;
                if (beta <= alpha)
                {
                    break;
                }
                /*System.out.println( "depth is "+ depth);
                System.out.println("beta is " + beta);
                System.out.println("alpha is " + alpha);
                System.out.println("MinVal is " + minVal);*/
            }
            return minVal;
        }
    }

    public int max(int depth, int beta, int alpha)
    {
        if (depth == 0)
        {
            Evaluation evaluation = new Evaluation(this.chessboard);
            return evaluation.getEvaluation();
        }
        else
        {
            validMoves[depth] = new LinkedList<>();
            chessboard.setTurn(1);
            generateValidMoves(depth, 1);
            int maxVal = Integer.MIN_VALUE;
            for (int i = 0; i < validMoves[depth].size(); i++)
            {
                position oldpos = validMoves[depth].get(i).getOldPos();
                position newpos = validMoves[depth].get(i).getNewPos();
                piece oldpiece = chessboard.board[newpos.getX()][newpos.getY()].getP();
                this.Aimove(oldpos,newpos);
                if (chessboard.checkAlert())
                {
                    if (chessboard.checkMate(newpos))
                    {
                        blackCheckMate = true;
                    }
                    blackCheck = true;
                }

                int evel = mini(depth - 1,beta, alpha);
                if (depth == MAXDEPTH && evel > maxVal)
                {
                    this.bestPosition = validMoves[depth].get(i);
                }
                maxVal = Math.max(evel, maxVal);
                alpha = Math.max(alpha, evel);
                this.Aimove(newpos,oldpos);
                chessboard.board[newpos.getX()][newpos.getY()].setP(oldpiece);
                blackCheckMate = false;
                blackCheck = false;
                if (beta <= alpha)
                {
                    break;
                }
                /*System.out.println( "depth is "+ depth);
                System.out.println("beta is " + beta);
                System.out.println("alpha is " + alpha);
                System.out.println("MinVal is " + maxVal);*/
            }
            return maxVal;
        }
    }

}
