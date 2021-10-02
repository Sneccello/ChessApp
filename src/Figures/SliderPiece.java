package Figures;

import BoardElements.ChessBoard;
import BoardElements.Pin;
import BoardElements.Tile;

import java.util.HashSet;

public abstract class SliderPiece extends Figure{

    public SliderPiece(FigureTypes figType,FigureColor figCol,int posCol, int posRow) {
        super(figType, figCol,posCol,posRow);
    }


    /**
     *
     * @param posCol to-be pinned piece col
     * @param posRow to-be pinned piece row
     * @param dx pin dir x
     * @param dy pin dir y
     * @return a Pin object or null if did not succeed to pin
     */
    protected HashSet<Tile> tryPinningPieceInDir(int posCol, int posRow, int dx, int dy){
        HashSet<Tile> availableTilesInPin = new HashSet<>();

        Tile t = discoverDirection(posCol,posRow,dx,dy, availableTilesInPin);
        if(t != null && t.getFig().getType() == FigureTypes.KING && t.getFig().isDifferentColorAs(this)){//enemy king found
            return availableTilesInPin;
        }
        return null;
    }

    /**
     * gets the possible moves in a direction for a slide rpiece
     * @param posCol start col
     * @param posRow start row
     * @param dx x increment for slide. its -1 or 1
     * @param dy y increment for a slide. same
     * @return the moves in a direction
     */
    protected HashSet<Tile> getPossibleMovesInDir(int posCol, int posRow, int dx, int dy){

        HashSet<Tile> movesInDir = new HashSet<>();
        Tile t = discoverDirection(posCol,posRow,dx,dy,movesInDir);
        if(t != null){//if the last tile in the direction is not a wall
            //if an enemy piece that is  not the king is found then try to pin it to the king
            if(t.getFig().isDifferentColorAs(this) && t.getFig().type != FigureTypes.KING){
                movesInDir.add(t);
                HashSet<Tile> tilesAvailableForPinnedPiece = tryPinningPieceInDir(t.getCol(),t.getRow(),dx,dy);
                if(tilesAvailableForPinnedPiece != null){
                    tilesAvailableForPinnedPiece.addAll(movesInDir);
                    tilesAvailableForPinnedPiece.add(tile);
                    // if i pin somebody to the king, then their possible moves will be reduced to
                    // 1: Tiles between the pinned piece and its king
                    // 2 :Tiles between the pinner and the pinned piece
                    Pin p = new Pin(tilesAvailableForPinnedPiece, this);
                    t.getFig().addPin(p);
                    System.out.println("added pin to " + t.getFig().getTile());
                }
            }
            //if the enemy king is found then look behind it to disable those tiles as legal moves for the enemy king
            else if(t.getFig().isDifferentColorAs(this) && t.getFig().type == FigureTypes.KING){
                HashSet<Tile> illegalMovesForEnemyKing = new HashSet<>();
                Tile tt = discoverDirection(t.getCol(),t.getRow(),dx,dy,illegalMovesForEnemyKing);
                ChessBoard.board.addIllegalKingTilesForOpponent(this,illegalMovesForEnemyKing);
            }
        }
        return movesInDir;
    }


    /**
     * Goes out in a direction. It can return the empty tiles until meeting a piece or the wall;
     * @param posCol from col
     * @param posRow from row
     * @param dx col increment
     * @param dy row increment
     * @param bag if not null this hashset will store the empty tiles in the direction which was discovered
     * @return The first tile in the direction which is not empty, or null if hit a wall before meeting a non-empty tile
     */
    public Tile discoverDirection(int posCol, int posRow,int dx, int dy, HashSet<Tile> bag) {

        while (true) {
            posCol += dx;
            posRow += dy;
            if (posCol < 0 || posCol >= 8 || posRow < 0 || posRow >= 8) {
                return null;
            } else {
                Tile t = ChessBoard.board.getTileAt(posCol, posRow);
                if (bag != null && t.isEmpty()) {
                    bag.add(t);
                } else if(! t.isEmpty()) {
                    return t;
                }
            }
        }
    }


}
