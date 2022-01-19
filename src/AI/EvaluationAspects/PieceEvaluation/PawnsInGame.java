package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;

public class PawnsInGame extends AbstractBaseEvaluationAspect {

    private final boolean countAsPenalty;
    public PawnsInGame(boolean countAsPenalty){
        //For example its better for the knight if there are a lot of pawns but it is quite disadvantageous for a rook

        aspectCoefficient = 8;
        this.countAsPenalty = countAsPenalty;
    }

    @Override
    protected int calculateAspectValue() {
        int nPawns = ChessBoard.board.getNumberOfPawnsInGame();

        return countAsPenalty ? -nPawns : nPawns;

    }
}
