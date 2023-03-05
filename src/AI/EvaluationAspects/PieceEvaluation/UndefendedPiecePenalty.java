package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Knight;
import BoardElements.Pieces.Piece;

public class UndefendedPiecePenalty extends AbstractBaseEvaluationAspect {

    private final Piece piece;
    private final int penaltyValue = -50;
    public UndefendedPiecePenalty(Piece piece){
        this.piece = piece;
        aspectCoefficient = 1;
        isPenalty = true;
        adhocMax = aspectCoefficient*penaltyValue;
        name = "Undefended Penalty";
    }

    @Override
    public int calculateAspectValue() {
        for(Piece p : piece.getSide().getRegularPieces()){
            if(p.isProtecting(piece.getSquare())){
                return 0;
            }
        }
        return penaltyValue;
    }
}
