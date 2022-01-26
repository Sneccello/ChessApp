package BoardElements.Pieces;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.PieceEvaluation.BasePieceValueBonus;
import AI.EvaluationAspects.PieceEvaluation.UndefendedPiecePenalty;
import ChessAbstracts.Moves.Move;
import BoardElements.Side;
import BoardElements.Square;


import java.util.ArrayList;
import java.util.HashSet;

public class Bishop extends SliderPiece{



    public Bishop(int posCol, int posRow, Side side) {
        super(PieceType.BISHOP,posCol,posRow,side);


    }


    @Override
    protected void initializeEvaluationAspects() {

        evaluationAspects.add(new UndefendedPiecePenalty(this));
    }

    @Override
    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> controlledSquares = new HashSet<>();
        for(int i = -1; i <=1; i+=2 ){
            for(int j = -1; j <= 1; j+=2){
                controlledSquares.addAll(getControlledSquaresInDir(square, i, j));
            }
        }
        return controlledSquares;
    }

}
