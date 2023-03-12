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

        aspectCoefficient = 5;
        isPenalty = false;
        adhocMax = aspectCoefficient*8;
        name = "Pawn Mobility";
    }
    protected ArrayList<Pawn> getOwnPawns(){
        ArrayList<Pawn> pawns = new ArrayList<>();
        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.PAWN && p.isAlive()){

                pawns.add( (Pawn) p );

            }
        }

        return pawns;
    }
    @Override
    protected int calculateAspectValue() {
        pawns = getOwnPawns();
        int nPawnMoves = 0;
        for(Pawn p : pawns){
            if(! p.getPossibleMoves().isEmpty()){
                nPawnMoves++;
            }
        }

        return nPawnMoves;
    }
}
