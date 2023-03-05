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

        int checkmateValue = 5000;
        return ( opponent.getNumberOfPossibleMoves() == 0 ? checkmateValue : 0 ) ;

    }
}
