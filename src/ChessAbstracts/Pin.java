package ChessAbstracts;

import BoardElements.Pieces.Piece;
import ChessAbstracts.Moves.Move;

import java.util.HashSet;

public class Pin {

    HashSet<Move> movesAvailableInThisPin;
    Piece pinnerPiece;

    public Pin(HashSet<Move> SquareAvailableInThisPin, Piece pinnerPiece){
        this.movesAvailableInThisPin = SquareAvailableInThisPin;
        this.pinnerPiece = pinnerPiece;
    }

    public HashSet<Move> getMovesAvailable() {
        return movesAvailableInThisPin;
    }

    public Piece getPinnerPiece() {
        return pinnerPiece;
    }

}
