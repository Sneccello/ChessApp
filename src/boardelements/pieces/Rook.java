package boardelements.pieces;

import ai.evaluationaspects.pieceevaluation.PawnsInGame;
import ai.evaluationaspects.pieceevaluation.PawnsOnSameFilePenalty;
import ai.evaluationaspects.pieceevaluation.RookOnSeventhRankBonus;
import boardelements.Side;
import boardelements.Square;
import chessabstracts.BinaryFlag;
import chessabstracts.CastlingPiece;
import chessabstracts.moves.Move;

import java.util.HashSet;

public class Rook extends SliderPiece implements CastlingPiece {



    King myKing;//need to store so that castling rights are looked after
    private final BinaryFlag leftStartingSquareFlag = new BinaryFlag(false);



    public Rook( int posCol, int posRow,Side side ,King myKing ) {
        super(PieceType.ROOK, posCol, posRow, side);

        this.myKing = myKing;

    }

    public void setLeftStartingSquare(boolean b){
        leftStartingSquareFlag.setValue(b);
    }


    @Override
    protected void initializeEvaluationAspects() {
        evaluationAspects.add(new PawnsInGame(true));
        evaluationAspects.add(new RookOnSeventhRankBonus(this));
        evaluationAspects.add(new PawnsOnSameFilePenalty(this));
    }

    @Override
    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> availableSquares = new HashSet<>();

        int[] iValues = {0,1,0,-1};
        int[] jValues = {1,0,-1,0};

        for(int c = 0; c < 4; c++) {

            availableSquares.addAll(getControlledSquaresInDir(square , iValues[c] ,jValues[c]));
        }

        return availableSquares;
    }

    public boolean hasLeftStartingSquare(){
        return leftStartingSquareFlag.value();
    }



    @Override
    public BinaryFlag getLeftStartingSquareFlag() {
        return leftStartingSquareFlag;
    }

    @Override
    public void moveTo(Square newSquare){
        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;

        if( ! hasLeftStartingSquare() ){
            leftStartingSquareFlag.setValue(true);
        }

    }

    @Override
    public HashSet<Move> calculatePossibleMoves(){

        HashSet<Move> moves = super.calculatePossibleMoves();

        if( ! hasLeftStartingSquare() ){
            for(Move m :  moves){
                m.addFlagToResetWhenUndone(leftStartingSquareFlag);
            }
        }

        return moves;
    }


}
