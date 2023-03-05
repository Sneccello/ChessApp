package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Side;

public class PawnIslandsPenalty extends AbstractSideEvaluationAspect {

    public PawnIslandsPenalty(Side side){
        this.side = side;
        aspectCoefficient = -20;
        isPenalty = true;
        adhocMax = aspectCoefficient*4;
        name = "Pawn Islands Penalty";
    }

    @Override
    protected int calculateAspectValue() {
        boolean[] pawns = {false,false,false,false,false,false,false,false};
        for(Piece p : side.getRegularPieces()){
            if(p.getType() == PieceType.PAWN){
                pawns[p.getCol()] = true;
            }
        }
        int islands = 0;
        boolean currentlySeeingIsland = pawns[0];

        for(int i = 1; i < pawns.length; i++){
            if( ! pawns[i] && currentlySeeingIsland ){
                currentlySeeingIsland = false;
                islands+=1;
            }
            else if(pawns[i] && ! currentlySeeingIsland){
                currentlySeeingIsland = true;
            }

        }

        if(pawns[pawns.length-1]){
            islands++;
        }

        return islands;

    }
}
