package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.pieces.King;

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
        if( ! king.getCastled().value() && ! king.getRookLongSide().hasLeftStartingSquare()){
            sidesWhereKingCanCastle++;

        }
        if( ! king.getCastled().value() && ! king.getRookShortSide().hasLeftStartingSquare()){
            sidesWhereKingCanCastle++;
        }
        return sidesWhereKingCanCastle*castlingRightValue;
    }
}
