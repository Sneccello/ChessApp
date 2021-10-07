package BoardElements;

import Figures.Piece;

import java.util.HashSet;

public class Pin {

    HashSet<Move> movesAvailableInThisPin;
    Piece pinnerPiece;

    public Pin(HashSet<Move> tileAvailableInThisPin, Piece pinnerPiece){
        this.movesAvailableInThisPin = tileAvailableInThisPin;
        this.pinnerPiece = pinnerPiece;
    }

    public HashSet<Move> getMovesAvailable() {
        return movesAvailableInThisPin;
    }

    public Piece getPinnerPiece() {
        return pinnerPiece;
    }

}
