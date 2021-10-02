package BoardElements;

import Figures.Figure;

import java.util.HashSet;

public class Pin {

    HashSet<Tile> tilesAvailable;
    Figure pinner;

    public Pin(HashSet<Tile> tilesInPin, Figure pinnerPiece){
        this.tilesAvailable = tilesInPin;
        this.pinner = pinnerPiece;
    }

    public HashSet<Tile> getTilesAvailable() {
        return tilesAvailable;
    }

    public Figure getPinner() {
        return pinner;
    }

}
