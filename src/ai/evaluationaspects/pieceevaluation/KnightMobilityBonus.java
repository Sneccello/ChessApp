package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.pieces.Knight;
import boardelements.Square;

import java.util.HashSet;

public class KnightMobilityBonus extends AbstractBaseEvaluationAspect {

    private Knight knight;

    public KnightMobilityBonus(Knight knight){
        this.knight = knight;
        aspectCoefficient = 10;
        isPenalty = false;
        adhocMax=aspectCoefficient*8;
        name = "Knight Mobility";
    }


    @Override
    protected int calculateAspectValue() {
        HashSet<Square> availableSquares = knight.calculateControlledSquares();
        HashSet<Square> pawnControlledSquaresByEnemy = knight.getSide().getOpponent().getPawnControlledSquares();

        availableSquares.removeIf(pawnControlledSquaresByEnemy::contains);

        return availableSquares.size();
    }
}
