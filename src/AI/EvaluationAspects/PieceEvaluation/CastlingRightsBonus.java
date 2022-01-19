package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.SideEvaluation.AbstractSideEvaluationAspect;
import BoardElements.Pieces.King;
import BoardElements.Side;

public class CastlingRightsBonus extends AbstractBaseEvaluationAspect {


    King king;
    public CastlingRightsBonus(King king){

        this.king = king;
        aspectCoefficient = 40;
    }


    @Override
    protected int calculateAspectValue() {
        int sidesWhereKingCanCastle = 0;
        if(king.canCastleLong()){
            sidesWhereKingCanCastle++;
        }
        if(king.canCastleShort()){
            sidesWhereKingCanCastle++;
        }

        return sidesWhereKingCanCastle;
    }
}
