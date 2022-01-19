package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Pawn;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Side;

import java.util.ArrayList;
import java.util.HashSet;

public class PawnMobilityBonus extends AbstractSideEvaluationAspect {

    ArrayList<Pawn> pawns;
    public PawnMobilityBonus(Side side){
        this.side = side;
        pawns = getOwnPawns();
        aspectCoefficient = 5;
    }

    @Override
    protected int calculateAspectValue() {

        int nPawnMoves = 0;
        Pawn recentlyCapturedPawn = null;
        for(Pawn p : pawns){
            if(p.isAlive() && ! p.getPossibleMoves().isEmpty()){
                nPawnMoves++;
            }
            else{
                recentlyCapturedPawn = p;
            }
        }
        if(recentlyCapturedPawn != null) {
            pawns.remove(recentlyCapturedPawn);
        }

        return nPawnMoves;
    }
}
