package ChessAbstracts;

import BoardElements.Square;
import BoardElements.Pieces.Piece;

import java.util.HashSet;

public class Check {

    private final Piece checker;
    private final HashSet<Square> possibleEndingSquares;
    public Check(Piece checker, HashSet<Square> possibleBlockingSquares) {
        this.checker = checker;

        this.possibleEndingSquares = possibleBlockingSquares;
        this.possibleEndingSquares.add(checker.getSquare());
    }


    public Piece getChecker() {
        return checker;
    }

    public HashSet<Square> getPossibleEndingSquares() {
        return possibleEndingSquares;
    }



}
