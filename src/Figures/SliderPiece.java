package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;

import java.util.HashSet;

public abstract class SliderPiece extends Figure{

    public SliderPiece(FigureTypes figType,FigureColor figCol,int posCol, int posRow) {
        super(figType, figCol,posCol,posRow);
    }


    protected void calculateMovesInDir(int posCol, int posRow,int dx, int dy, HashSet<Tile> moves) {
        boolean endOfMoveSearch = false;
        while (!endOfMoveSearch) {

            posCol += dx;

            posRow += dy;

            if (posCol < 0 || posCol >= 8 || posRow < 0 || posRow >= 8) { //TODO külön megnézni melyik mezőket tudja checkolni
                endOfMoveSearch = true;
            } else {
                Tile tile = ChessBoard.board.getTileAt(posCol, posRow);
                if (tile.isEmpty() || tile.getFigureOnThisTile().isDifferentColorAs(this)) {
                    moves.add(tile);
                }
                if (!tile.isEmpty()) {
                    endOfMoveSearch = true;
                }
            }
        }
    }

}
