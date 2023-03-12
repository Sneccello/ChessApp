package ai.evaluationaspects.pieceevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.ChessBoard;

public class PawnsInGame extends AbstractBaseEvaluationAspect {

    public PawnsInGame(boolean isPenalty){
        //For example its better for the knight if there are a lot of pawns but it is quite disadvantageous for a rook

        aspectCoefficient = 8;
        this.isPenalty = isPenalty;
        adhocMax = 16*aspectCoefficient * (isPenalty? -1:1);
        name = "Pawns Still In Game";
    }

    @Override
    protected int calculateAspectValue() {
        int nPawns = ChessBoard.board.getNumberOfPawnsInGame();

        return isPenalty ? -nPawns : nPawns;

    }
}
