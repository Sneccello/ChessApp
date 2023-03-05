package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Knight;
import BoardElements.Pieces.Piece;
import BoardElements.Square;

import java.util.HashSet;

public class KnightMobilityBonus extends AbstractBaseEvaluationAspect {

    private Knight knight;

    public KnightMobilityBonus(Knight knight){
        this.knight = knight;
        aspectCoefficient = 10;
        isPenalty = false;
        adhocMax=aspectCoefficient*8;
        name = "Knight Mobility";
    }


    @Override
    protected int calculateAspectValue() {
        HashSet<Square> availableSquares = knight.calculateControlledSquares();
        HashSet<Square> pawnControlledSquaresByEnemy = knight.getSide().getOpponent().getPawnControlledSquares();

        availableSquares.removeIf(pawnControlledSquaresByEnemy::contains);

        return availableSquares.size();
    }
}
