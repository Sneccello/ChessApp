package ai.evaluationaspects.sideevaluation;

import boardelements.pieces.Pawn;
import boardelements.pieces.Piece;
import boardelements.pieces.PieceType;
import boardelements.Side;

public class PassedPawnsBonus extends AbstractSideEvaluationAspect {

    public PassedPawnsBonus(Side side){
        this.side = side;
        aspectCoefficient = 75;
        isPenalty = false;
        adhocMax = aspectCoefficient*8;
        name = "Passed Pawns";
    }

    @Override
    protected int calculateAspectValue() {
        int nPassedPawns = 0;

        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.PAWN){
                boolean isPassed = side.getOpponent().checkPassedPawn((Pawn) p);
                if(isPassed){
                    nPassedPawns++;
                }
            }
        }

        return nPassedPawns;

    }
}
