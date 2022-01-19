package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;

public class PawnsOnSameFilePenalty extends AbstractBaseEvaluationAspect {

    Piece piece;

    PawnsOnSameFilePenalty(Piece piece){
        this.piece = piece;
        aspectCoefficient = -15;
    }

    @Override
    protected int calculateAspectValue() {
        int nPawnsInFile =  ChessBoard.board.countPawnsInFile(piece.getCol());
        return nPawnsInFile;
    }
}
