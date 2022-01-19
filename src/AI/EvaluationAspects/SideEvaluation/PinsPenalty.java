package AI.EvaluationAspects.SideEvaluation;

import BoardElements.Pieces.Piece;
import BoardElements.Side;

public class PinsPenalty extends AbstractSideEvaluationAspect {

    Side side;
    public PinsPenalty(Side side){
        this.side = side;
        aspectCoefficient = 20;
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
