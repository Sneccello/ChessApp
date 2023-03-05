package AI.EvaluationAspects.SideEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Bishop;
import BoardElements.Pieces.Piece;
import BoardElements.Side;

import static BoardElements.Pieces.PieceColor.BLACK;
import static BoardElements.Pieces.PieceColor.WHITE;

public class ColorWeaknessPenalty extends AbstractSideEvaluationAspect {

    public ColorWeaknessPenalty(Side side){
        this.side = side;
        aspectCoefficient = -10;
        isPenalty = true;
        adhocMax = aspectCoefficient*10;
        name = "Color Weakness Penalty";
    }

    @Override
    public int calculateAspectValue() {

        int pawnsOnWhiteSquares = 0;
        int pawnsOnBlackSquares = 0;
        int minorPiecesOnBlackSquares = 0;
        int minorPiecesOnWhiteSquares = 0;
        for(Piece p : side.getRegularPieces()){
            switch (p.getType()){
                case BISHOP -> {
                    if(p.getColor() == WHITE){
                        minorPiecesOnWhiteSquares++;
                    }
                    else{
                        if(p.getColor() == BLACK){
                            minorPiecesOnBlackSquares++;
                        }
                    }
                }
                case PAWN -> {
                    if(p.getColor() == WHITE){
                        pawnsOnWhiteSquares++;
                    }
                    else{
                        if(p.getColor() == BLACK){
                            pawnsOnBlackSquares++;
                        }
                    }
                }

            }
        }

        int whiteSquaresStrength = pawnsOnWhiteSquares + minorPiecesOnWhiteSquares;
        int blackSquaresStrength = pawnsOnBlackSquares + minorPiecesOnBlackSquares;


        return Math.abs(whiteSquaresStrength - blackSquaresStrength);
    }
}
