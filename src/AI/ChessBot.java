package AI;

import BoardElements.ChessBoard;
import BoardElements.Pieces.PieceColor;
import BoardElements.Pieces.PieceType;
import BoardElements.Square;
import ChessAbstracts.Moves.Move;
import BoardElements.Side;
import Control.Player;
import BoardElements.Pieces.Piece;
import com.sun.jdi.ArrayReference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class ChessBot implements Player {
    private final Side mySide;
    private final Side opponent;
    private final int MAX_SEARCH_DEPTH = 5;
    private final HashMap<String,Double> evaluationCache = new HashMap();
    private int checkmateValue = 5000;


    //This hashmap is for caching boardstates
    private static final HashMap<PieceType, Integer> numberCodesForPieces  = new HashMap<>();

    static {
        //since this is for only internal storage and cache, Im just using numbers as labels for the pieces so that the string is shorter
        //White Piece starting with 1
        //Black Piece starting with 2
        //Empty tile is 0
        //Rook : 1
        //Knight : 2
        //Bishop : 3
        //Queen : 4
        //King : 5
        //Pawn : 6
        //for example :  black knight = 24
        //               empty square : 0
        numberCodesForPieces.put(PieceType.ROOK,1);
        numberCodesForPieces.put(PieceType.KNIGHT,2);
        numberCodesForPieces.put(PieceType.BISHOP,3);
        numberCodesForPieces.put(PieceType.QUEEN,4);
        numberCodesForPieces.put(PieceType.KING,5);
        numberCodesForPieces.put(PieceType.PAWN,6);
    }

    public ChessBot(Side s){
        this.mySide = s;
        this.opponent = mySide.getOpponent();

    }

    private String getCodeForPiece(Piece p){

        int numberCodeForWhite = 1;
        int numberCodeForBlack = 2;


        if(p == null){
            return "00";
        }
        StringBuilder builder = new StringBuilder();

        int colorCode = p.getColor() == PieceColor.WHITE ? numberCodeForWhite : numberCodeForBlack;
        builder.append(colorCode);
        builder.append(numberCodesForPieces.get(p.getType()));

        return builder.toString();
    }


    private String stringifyBoard(){

        StringBuilder builder = new StringBuilder();

        for(int row = 0; row < 8 ; row++){
            for(int col = 0; col < 8;  col++){
                Piece p = ChessBoard.board.getPieceAt(col,row);
                builder.append(getCodeForPiece(p));
            }
        }
        return builder.toString();
    }

    private ArrayList<Move> orderMovesForAlphaBetaAlgorithm(HashSet<Move> moves){


        ArrayList<Move> listOfMoves = new ArrayList<>(moves);

        listOfMoves.sort(new Comparator<Move>() {
            @Override
            public int compare(Move m1, Move m2) {
                return (int) (m1.getCaptureValue() - m2.getCaptureValue()); //TODO should be improved
            }
        });

        return listOfMoves;
    }

    private ArrayList<Move> calculateAndOrderMoves(){
        ChessBoard.board.calculateMoves();
        HashSet<Move> moves = mySide.getPossibleMoves();
        return orderMovesForAlphaBetaAlgorithm(moves);
    }


    public Move getBestMove(){

        ArrayList<Move> moves = calculateAndOrderMoves();


        double maxScore = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for(Move move : moves){//acting as as an AlphaBetaMAX move/algorithm to get the best move in stead of just evaluating
            move.execute();

            double moveScore = alphaBetaMin(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY, MAX_SEARCH_DEPTH);
            if(moveScore > maxScore){
                maxScore = moveScore;
                bestMove = move;
            }

            move.undo();
        }

        return bestMove;

    }

    private double alphaBetaMax(double alpha, double beta, int depthLeft){

        if(depthLeft == 0){
            return evaluateBoard();
        }

        ArrayList<Move> moves = calculateAndOrderMoves();

        for(Move move : moves){
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
            return -evaluateBoard();
        }

        ArrayList<Move> moves = calculateAndOrderMoves();

        for(Move move : moves){
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


    private double evaluateSide(Side side){

        double relativeValueSum = 0;

        for(Piece p : side.getRegularPieces()){
            relativeValueSum += p.getRelativeValue();
        }
        relativeValueSum += side.getKing().getRelativeValue();

        int pawnIslands = side.countPawnIslands();

        double finalValue = relativeValueSum - pawnIslands/4.0;

        boolean isCheckmateForOpponent = ( opponent.getPossibleMoves().size() == 0 );

        if(isCheckmateForOpponent){
            finalValue += checkmateValue;
        }

        return finalValue;

    }

    public double evaluateBoard(){

        String codeForBoardState = stringifyBoard();

        if(evaluationCache.containsKey(codeForBoardState)){
            return evaluationCache.get(codeForBoardState);
        }

        double stateValue = evaluateSide(mySide) - evaluateSide(opponent);

        evaluationCache.put(codeForBoardState, stateValue);

        return stateValue;

    }


}
