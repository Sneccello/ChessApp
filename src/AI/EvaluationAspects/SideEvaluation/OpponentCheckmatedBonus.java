package AI.EvaluationAspects.SideEvaluation;

import BoardElements.Side;

public class OpponentCheckmatedBonus extends AbstractSideEvaluationAspect{

    Side opponent;
    private final int checkmateValue = 5000;

    public OpponentCheckmatedBonus(Side side){
        this.opponent = side.getOpponent();
        aspectCoefficient = 1;
        adhocMax=checkmateValue*aspectCoefficient;
        name = "Given Checkmate";
        isPenalty=false;
    }

    @Override
    protected int calculateAspectValue() {


        return ( opponent.getNumberOfPossibleMoves() == 0 ? checkmateValue : 0 ) ;

    }
}
