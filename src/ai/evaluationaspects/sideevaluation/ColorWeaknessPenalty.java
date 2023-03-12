package ai.evaluationaspects.sideevaluation;

import boardelements.pieces.Piece;
import boardelements.Side;

import static boardelements.pieces.PieceColor.BLACK;
import static boardelements.pieces.PieceColor.WHITE;

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


        return Math.max(0,-Math.abs(whiteSquaresStrength - blackSquaresStrength)+6);
    }
}
