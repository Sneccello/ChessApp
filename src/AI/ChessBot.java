package AI;

import BoardElements.Move;
import BoardElements.Side;
import Control.Player;
import Pieces.Piece;

import java.util.HashSet;

public class ChessBot implements Player {
    private final Side mySide;
    private final Side opponent;
    private final int MAX_DEPTH = 5;

    public ChessBot(Side s){
        this.mySide = s;
        this.opponent = mySide.getOpponent();
    }


    public Move getBestMove(){

        HashSet<Move> moves = mySide.getPossibleMoves();

        double maxScore = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for(Move move : moves){

            double moveScore = alphaBetaMax(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY, MAX_DEPTH);
            if(moveScore > maxScore){
                maxScore = moveScore;
                bestMove = move;
            }
        }

        System.out.println(bestMove);
        return bestMove;

    }

    private double alphaBetaMax(double alpha, double beta, int depthLeft){


        if(depthLeft == 0){
            return evaluateBoard(mySide) - evaluateBoard(opponent);
        }

        for(Move move : mySide.getPossibleMoves()){
            move.execute();
            double score  = alphaBetaMin(alpha,beta,depthLeft-1);
            move.undo();
            if(score >= beta){
                return beta;
            }
            if(score > alpha){
                alpha = score;
            }
        }
        return alpha;

    }

    private double alphaBetaMin(double alpha, double beta, int depthLeft){

        if(depthLeft == 0){
            return  -evaluateBoard(mySide) + evaluateBoard(opponent);
        }

        for(Move move : opponent.getPossibleMoves()){
            move.execute();
            double score  = alphaBetaMax(alpha,beta,depthLeft-1);
            move.undo();
            if(score <= alpha){
                return beta;
            }
            if(score < beta){
                alpha = score;
            }
        }
        return beta;
    }


    public double evaluateBoard(Side side){

        double relativeValueSum = 0;

        for(Piece p : side.getRegularPieces()){
            relativeValueSum += p.getRelativeValue();
        }
        relativeValueSum += side.getKing().getRelativeValue();

        int pawnIslands = side.countPawnIslands();
        return relativeValueSum - pawnIslands/4.0;


    }


}
