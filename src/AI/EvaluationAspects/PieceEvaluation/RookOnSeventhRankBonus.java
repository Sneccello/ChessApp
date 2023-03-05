package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.PieceColor;
import BoardElements.Pieces.Rook;

public class RookOnSeventhRankBonus extends AbstractBaseEvaluationAspect {

    Rook rook;
    private final int aspectValue = 100;
    public RookOnSeventhRankBonus(Rook rook){
        this.rook = rook;
        aspectCoefficient = 1;
        isPenalty = false;
        adhocMax = aspectValue*aspectValue;
        name = "Rook On The 7th Rank";
    }

    @Override
    protected int calculateAspectValue() {
        int seventhRankIdx = ( rook.getColor() == PieceColor.WHITE ? 6 : 1 );

        return seventhRankIdx == rook.getCol() ? aspectValue : 0;

    }
}
