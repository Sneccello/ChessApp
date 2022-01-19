package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Pawn;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Side;

public class PassedPawnsBonus extends AbstractSideEvaluationAspect {

    public PassedPawnsBonus(Side side){
        this.side = side;
        aspectCoefficient = 75;
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
