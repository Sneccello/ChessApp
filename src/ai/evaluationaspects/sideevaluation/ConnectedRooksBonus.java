package ai.evaluationaspects.sideevaluation;

import boardelements.pieces.Piece;
import boardelements.pieces.PieceType;
import boardelements.pieces.Rook;
import boardelements.Side;

public class ConnectedRooksBonus extends AbstractSideEvaluationAspect {


    Rook rook1 = null;
    Rook rook2 = null;
    private final int bonusValue = 100;
    public ConnectedRooksBonus(Side side){
        this.side = side;

        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.ROOK ){
                if(rook1 == null){
                    rook1 = (Rook)p;
                }
                else{
                    rook2 = (Rook)p;
                }
            }
        }

        aspectCoefficient = 1;
        isPenalty = false;
        adhocMax = bonusValue*aspectCoefficient;
        name = "Connected Rooks";
    }

    @Override
    protected int calculateAspectValue() {

        if( ! rook1.isAlive() || ! rook2.isAlive()){
            return 0;
        }
        return rook1.isProtecting(rook2.getSquare()) ? bonusValue : 0;

    }
}
