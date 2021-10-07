package Figures;

import BoardElements.*;

import java.util.HashSet;

public abstract class SliderPiece extends Piece {

    public SliderPiece(PieceType figType, PieceColor figCol, int posCol, int posRow, Side side) {
        super(figType, figCol,posCol,posRow,side);
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
        HashSet<Tile> availableMovesInPin = new HashSet<>();

        Tile t = discoverDirection(posCol,posRow,dx,dy, availableMovesInPin);
        //if t != null then a piece was found in this direction
        if(t != null && t.getFig().getType() == PieceType.KING && t.getFig().isDifferentColorAs(this)){//enemy king found
            return availableMovesInPin; //successful pin in this direction
        }
        return null; //could not pin in this direction
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

        HashSet<Tile> tilesInDir = new HashSet<>();
        Tile t = discoverDirection(posCol,posRow,dx,dy,tilesInDir);
        if(t !=  null){//if the last tile in the direction is not a wall

            //if an enemy piece that is not the king is found then try to pin it to the king
            if(t.getFig().isDifferentColorAs(this) && t.getFig().type != PieceType.KING){

                tilesInDir.add(t);//we can capture this piece so we can step on that tile

                HashSet<Tile> tilesAvailableForPinnedPiece = tryPinningPieceInDir(t.getCol(),t.getRow(),dx,dy); //and we will try to pin is to the enemy king

                if(tilesAvailableForPinnedPiece != null){ //=successful pin
                    Piece pinnedPiece = t.getPieceOnThisTile();

                    tilesAvailableForPinnedPiece.addAll(tilesInDir); //we add the tiles between the pinned piece and the current pinner piece as available tiles for the pinned piece
                    tilesAvailableForPinnedPiece.add(tile); //we add ourselves as possible tiles for the pinned piece. It can capture us, if it can move towards us
                    // because:
                    // if i pin somebody to the king, then their possible moves will be reduced to
                    // 1: Tiles between the pinned piece and its king
                    // 2 :Tiles between the pinner and the pinned piece (capture included)

                    //creating pin object for this particular pin
                    Pin p = new Pin(convertTilesToMoves(t.getFig(), tilesAvailableForPinnedPiece), this);
                    pinnedPiece.addPin(p);
                }
            }
            else if( ! t.getFig().isDifferentColorAs(this)){//allied piece, then its protected and cannot be captured by enemy king
                ChessBoard.board.addIllegalKingTileForOpponent(this,t);
            }
            //if the enemy king is found then look behind it to additionally disable those tiles as legal moves for the enemy king
            //regarding the direction from where this piece sees the enemy king is already added as illegal moves when this function returns
            else if(t.getFig().isTheEnemyKingFor(this)){

                ChessBoard.board.registerCheckEnemyKing(this,tilesInDir);//first of all register the check

                //make tiles behind the king illegal
                HashSet<Tile> illegalTilesForEnemyKing = new HashSet<>();
                Tile unimportantTile = discoverDirection(t.getCol(),t.getRow(),dx,dy,illegalTilesForEnemyKing);
                //we discard the return value, because we do not care if we cancelled the search because we hit a wall or another piece
                //the main thing is that the tiles between that and the king should be illegal moves for the king
                ChessBoard.board.addIllegalKingTilesForOpponent(this,illegalTilesForEnemyKing);
            }
        }
        return tilesInDir;
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
