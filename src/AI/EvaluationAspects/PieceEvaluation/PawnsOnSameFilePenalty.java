package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;

public class PawnsOnSameFilePenalty extends AbstractBaseEvaluationAspect {

    Piece piece;

    public PawnsOnSameFilePenalty(Piece piece){
        this.piece = piece;
        aspectCoefficient = -15;
        isPenalty=true;
        adhocMax=2*aspectCoefficient;
        name = "Pawns On The Same File";
    }

    @Override
    protected int calculateAspectValue() {
        return ChessBoard.board.countPawnsInFile(piece.getCol());
    }
}
