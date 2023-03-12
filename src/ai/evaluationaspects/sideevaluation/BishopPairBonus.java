package ai.evaluationaspects.sideevaluation;


import boardelements.pieces.Bishop;
import boardelements.pieces.Piece;
import boardelements.pieces.PieceType;
import boardelements.Side;

public class BishopPairBonus extends AbstractSideEvaluationAspect {

    Bishop bishop1 = null;
    Bishop bishop2 = null;

    private final int bonusValue = 100;

    public BishopPairBonus(Side side){
        this.side = side;

        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.BISHOP){
                if(bishop1 == null){
                    bishop1 = (Bishop) p;
                }
                else{
                    bishop2 = (Bishop) p;
                }
            }
        }

        this.aspectCoefficient = 1;
        isPenalty = false;
        adhocMax = aspectCoefficient*bonusValue;
        name = "Bishop Pair Bonus";

    }


    @Override
    public int calculateAspectValue() {
        return bishop1.isAlive() && bishop2.isAlive() ? bonusValue : 0;
    }
}
