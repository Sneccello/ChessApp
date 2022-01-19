package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;

public class EarlyDevelopmentPenalty extends AbstractBaseEvaluationAspect {

    int firstNMovesToPenalize;

    public EarlyDevelopmentPenalty(int firstNMovesToPenalize){
        aspectCoefficient = -25;
        this.firstNMovesToPenalize = firstNMovesToPenalize;
    }

    @Override
    protected int calculateAspectValue() {
        return Math.max(firstNMovesToPenalize - ChessBoard.board.getMoveCount(), 0);
    }
}
