package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;

public class EarlyDevelopmentPenalty extends AbstractBaseEvaluationAspect {

    int firstNMovesToPenalize;
    int penalty = -25;
    public EarlyDevelopmentPenalty(int firstNMovesToPenalize){
        aspectCoefficient = 1;
        this.firstNMovesToPenalize = firstNMovesToPenalize;
        isPenalty =true;
        adhocMax = firstNMovesToPenalize*penalty*aspectCoefficient;
        name = "Early Development";

    }

    @Override
    protected int calculateAspectValue() {
        return Math.max(firstNMovesToPenalize - ChessBoard.board.getMoveCount(), 0) * penalty;
    }
}
