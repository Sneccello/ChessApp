package BoardElements.Pieces;

import AI.EvaluationAspects.PieceEvaluation.EarlyDevelopmentPenalty;
import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Square;

import java.util.HashSet;

public class Queen extends SliderPiece{
    public Queen(PieceColor PiececCol, int posCol, int posRow, Side side) {
        super(PieceType.QUEEN, PiececCol,posCol,posRow,side);

    }


    @Override
    protected void initializeEvaluationAspects() {
        evaluationAspects.add(new EarlyDevelopmentPenalty(6));
    }

    @Override
    protected HashSet<Square> calculateControlledSquares() {
        HashSet<Square> controlledSquares = new HashSet<>();
        for(int i = -1; i <=1; i+=1 ){
            for(int j = -1; j <= 1; j+=1) {
                if (!(i == 0 && j == 0)) {
                    controlledSquares.addAll(getControlledSquaresInDir(square, i, j));
                }
            }
        }
        return controlledSquares;
    }



}
