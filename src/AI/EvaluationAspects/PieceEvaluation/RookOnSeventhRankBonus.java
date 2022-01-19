package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.PieceColor;
import BoardElements.Pieces.Rook;

public class RookOnSeventhRankBonus extends AbstractBaseEvaluationAspect {

    Rook rook;

    public RookOnSeventhRankBonus(Rook rook){
        this.rook = rook;
        aspectCoefficient = 1;
    }

    @Override
    protected int calculateAspectValue() {
        int seventhRankIdx = ( rook.getColor() == PieceColor.WHITE ? 6 : 1 );

        return seventhRankIdx == rook.getCol() ? 100 : 0;

    }
}
