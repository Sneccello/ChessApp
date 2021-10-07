package BoardElements;

import Figures.Piece;

import java.util.HashSet;

public class Check {

    private final Piece checker;
    private final HashSet<Tile> possibleEndingTiles = new HashSet<>();
    Check(Piece checker, HashSet<Tile> possibleBlockingTiles) {
        this.checker = checker;
        if (possibleBlockingTiles != null) {
            this.possibleEndingTiles.addAll(possibleBlockingTiles);
        }
        possibleEndingTiles.add(checker.getTile());
    }


    public Piece getChecker() {
        return checker;
    }

    public HashSet<Tile> getPossibleEndingTiles() {
        return possibleEndingTiles;
    }



}
