package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;

public class PawnsInGame extends AbstractBaseEvaluationAspect {

    public PawnsInGame(boolean isPenalty){
        //For example its better for the knight if there are a lot of pawns but it is quite disadvantageous for a rook

        aspectCoefficient = 8;
        this.isPenalty = isPenalty;
        adhocMax = 16*aspectCoefficient;
        name = "Pawns Still In Game";
    }

    @Override
    protected int calculateAspectValue() {
        int nPawns = ChessBoard.board.getNumberOfPawnsInGame();

        return isPenalty ? -nPawns : nPawns;

    }
}
