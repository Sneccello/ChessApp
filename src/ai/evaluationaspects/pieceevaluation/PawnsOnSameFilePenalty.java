package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.ChessBoard;
import boardelements.pieces.Piece;

public class PawnsOnSameFilePenalty extends AbstractBaseEvaluationAspect {

    Piece piece;

    public PawnsOnSameFilePenalty(Piece piece){
        this.piece = piece;
        aspectCoefficient = -15;
        isPenalty=true;
        adhocMax=2*aspectCoefficient;
        name = "Pawns On The Same File" + (isPenalty() ? " Penalty"  : "");
    }

    @Override
    protected int calculateAspectValue() {
        return ChessBoard.board.countPawnsInFile(piece.getCol());
    }
}
