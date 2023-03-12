package ai.evaluationaspects.sideevaluation;

import boardelements.pieces.Piece;
import boardelements.Side;

public class PinsPenalty extends AbstractSideEvaluationAspect {

    Side side;
    public PinsPenalty(Side side){
        this.side = side;
        aspectCoefficient = -20;
        isPenalty = true;
        adhocMax = aspectCoefficient*1;
        name = "Pinned Piece Penalty";
    }

    @Override
    protected int calculateAspectValue() {

        int sum = 0;
        for(Piece p : side.getRegularPieces()){
            sum += p.countPins();
        }
        return sum;


    }
}
