package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.pieces.Piece;

public class BasePieceValueBonus extends AbstractBaseEvaluationAspect {


    private final Piece piece;

    public BasePieceValueBonus(Piece piece){
        this.piece = piece;
        aspectCoefficient = 1;
        isPenalty = false;
        adhocMax = Piece.basePieceValues.get(piece.getType()) * aspectCoefficient;
        name = "Base value";
    }


    @Override
    protected int calculateAspectValue() {
        return Piece.basePieceValues.get(piece.getType());
    }

    public static int getBaseValueOf(Piece p){
        return Piece.basePieceValues.get(p.getType());
    }

}
