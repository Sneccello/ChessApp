package Figures;

import BoardElements.Tile;
import Views.FigureView;

import java.util.HashSet;

public class Pawn extends Figure{
    Pawn(FigureColor figCol,int posCol, int posRow) {
        super(FigureTypes.PAWN, figCol,posCol,posRow);

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        return new HashSet<Tile>();
    }


}
