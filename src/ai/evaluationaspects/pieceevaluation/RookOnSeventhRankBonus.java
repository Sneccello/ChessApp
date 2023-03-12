package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.pieces.PieceColor;
import boardelements.pieces.Rook;

public class RookOnSeventhRankBonus extends AbstractBaseEvaluationAspect {

    Rook rook;
    private final int aspectValue = 100;
    public RookOnSeventhRankBonus(Rook rook){
        this.rook = rook;
        aspectCoefficient = 1;
        isPenalty = false;
        adhocMax = aspectValue*aspectCoefficient;
        name = "Rook On The 7th Rank";
    }

    @Override
    protected int calculateAspectValue() {
        int seventhRankIdx = ( rook.getColor() == PieceColor.WHITE ? 6 : 1 );

        return seventhRankIdx == rook.getCol() ? aspectValue : 0;

    }
}
