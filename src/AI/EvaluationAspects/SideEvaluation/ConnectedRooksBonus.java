package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Pieces.Rook;
import BoardElements.Side;

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

        return bonusValue;

    }
}
