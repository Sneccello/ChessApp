package AI.EvaluationAspects.SideEvaluation;

import BoardElements.Side;

public class OpponentCheckmatedBonus extends AbstractSideEvaluationAspect{

    Side opponent;

    public OpponentCheckmatedBonus(Side side){
        this.opponent = side.getOpponent();
        aspectCoefficient = 1;
    }

    @Override
    protected int calculateAspectValue() {

        return ( opponent.getNumberOfPossibleMoves() == 0 ? 10000 : 0 ) ;

    }
}
