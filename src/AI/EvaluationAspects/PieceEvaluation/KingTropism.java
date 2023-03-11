package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.King;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceType;
import BoardElements.Side;
import BoardElements.Square;

public class KingTropism extends AbstractBaseEvaluationAspect {

    Side enemySide;
    King king;
    public KingTropism(King king, Side enemySide){
        this.enemySide = enemySide;
        aspectCoefficient = 0.5;
        this.king = king;
        isPenalty=false;
        adhocMax = aspectCoefficient*800*aspectCoefficient;
        name = "Enemy Distance from Pieces";
    }

    private double euclideanDistanceBetween(Square s1, Square s2){
        double colDiff = s1.getCol()-s2.getCol();
        double rowDiff = s1.getRow()-s2.getRow();
        return Math.sqrt( colDiff*colDiff + rowDiff*rowDiff );
    }

    @Override
    protected int calculateAspectValue() {
        double tropism = 0;
        for(Piece p : enemySide.getRegularPieces()) {
            if (p.getType() != PieceType.PAWN) {
                int pieceValue = BasePieceValueBonus.getBaseValueOf(p);
                tropism += pieceValue / euclideanDistanceBetween(king.getSquare(), p.getSquare());
            }
        }

        return (int)tropism;
    }
}
