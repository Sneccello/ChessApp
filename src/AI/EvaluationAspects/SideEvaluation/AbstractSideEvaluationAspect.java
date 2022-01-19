package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Pawn;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Side;

import java.util.ArrayList;

public abstract class AbstractSideEvaluationAspect extends AbstractBaseEvaluationAspect {

    protected Side side;

    protected abstract int calculateAspectValue();

    protected ArrayList<Pawn> getOwnPawns(){
        ArrayList<Pawn> pawns = new ArrayList<>();
        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.PAWN){

                pawns.add( (Pawn) p );

            }
        }

        return pawns;
    }


}
