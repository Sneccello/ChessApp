package chessabstracts;

import boardelements.Square;
import boardelements.pieces.Piece;

import java.util.HashSet;

public class Check {
    //TODO knight check is not steppable-aside somehow
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
