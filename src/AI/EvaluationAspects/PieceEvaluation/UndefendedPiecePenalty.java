package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Knight;
import BoardElements.Pieces.Piece;

public class UndefendedPiecePenalty extends AbstractBaseEvaluationAspect {

    private final Piece piece;
    public UndefendedPiecePenalty(Piece piece){
        this.piece = piece;
        aspectCoefficient = -1;
    }

    @Override
    public int calculateAspectValue() {
        for(Piece p : piece.getSide().getRegularPieces()){
            if(p.isProtecting(piece.getSquare())){
                return 0;
            }
        }
        return 50;
    }
}
