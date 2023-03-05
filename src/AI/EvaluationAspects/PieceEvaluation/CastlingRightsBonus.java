package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.King;

public class CastlingRightsBonus extends AbstractBaseEvaluationAspect {


    King king;
    private final int castlingRightValue = 40;

    public CastlingRightsBonus(King king){

        this.king = king;
        aspectCoefficient = 1;
        isPenalty =false;
        adhocMax = castlingRightValue*2*aspectCoefficient;
        name = "Castling Rights";
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

        return sidesWhereKingCanCastle*castlingRightValue;
    }
}
