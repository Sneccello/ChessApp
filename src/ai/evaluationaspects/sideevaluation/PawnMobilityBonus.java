package ai.evaluationaspects.sideevaluation;

import boardelements.pieces.Pawn;
import boardelements.pieces.Piece;
import boardelements.pieces.PieceType;
import boardelements.Side;

import java.util.ArrayList;

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
