package Figures;

import BoardElements.*;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class SliderPiece extends Piece {

    public SliderPiece(PieceType figType, PieceColor figCol, int posCol, int posRow, Side side) {
        super(figType, figCol,posCol,posRow,side);
    }



    protected HashSet<Tile> tryPinningPieceInDir(Tile toBePinnedTile, int dx, int dy){

        ArrayList<Tile> tilesToTheNextPiece = discoverDirection(toBePinnedTile,dx,dy);

        if(tilesToTheNextPiece.isEmpty()){
            return null;
        }
        Tile last = tilesToTheNextPiece.get(tilesToTheNextPiece.size()-1);

        if( ! last.isEmpty() && last.getPieceOnThisTile().isTheEnemyKingFor(this)){// if enemy king found then a pin is sucessful
            tilesToTheNextPiece.remove(last); //remove the king's tile as a possible move
            return new HashSet<>(tilesToTheNextPiece);
        }
        return null; //could not pin in this direction
    }




    protected HashSet<Tile> getControlledTilesInDir(Tile currentTile, int dx, int dy){

        ArrayList<Tile> controlledTilesInDir = discoverDirection(currentTile,dx,dy);

        if( ! controlledTilesInDir.isEmpty()){

            Tile lastFoundTile = controlledTilesInDir.get(controlledTilesInDir.size() - 1 );

            if( lastFoundTile.isEmpty() || lastFoundTile.getPieceOnThisTile().isSameColorAs(this)){
                return new HashSet<>(controlledTilesInDir);
            }


            //if an enemy piece that is not the king is found then let's try to pin it to the king
            if(lastFoundTile.getPieceOnThisTile().isDifferentColorAs(this) && lastFoundTile.getPieceOnThisTile().type != PieceType.KING){

                HashSet<Tile> tilesAvailableForPinnedPiece = tryPinningPieceInDir(lastFoundTile,dx,dy); //and we will try to pin is to the enemy king

                if(tilesAvailableForPinnedPiece != null){ //=successful pin
                    Piece pinnedPiece = lastFoundTile.getPieceOnThisTile();

                    tilesAvailableForPinnedPiece.addAll(controlledTilesInDir); //we add the tiles between the pinned piece and the current pinner piece as available tiles for the pinned piece
                    tilesAvailableForPinnedPiece.add(tile); //we add ourselves as possible tiles for the pinned piece. It can capture us, if it can move towards us

                    Pin p = new Pin(convertTilesToMoves(pinnedPiece, tilesAvailableForPinnedPiece), this);
                    pinnedPiece.addPin(p);
                }
            }

            //if the enemy king is found then look behind him for additional controlled Tiles
            else if(lastFoundTile.getPieceOnThisTile().isTheEnemyKingFor(this)){
                controlledTilesInDir.remove(lastFoundTile); //remove the king's tile as a possible blocking tile
                ChessBoard.board.registerCheckEnemyKing(this,new HashSet<>(controlledTilesInDir));


                ArrayList<Tile> tilesBehindKing = discoverDirection(lastFoundTile,dx,dy);
                controlledTilesInDir.addAll(tilesBehindKing);

            }
        }
        return new HashSet<>(controlledTilesInDir);
    }


    /**
     * goes out in a direction and returns the found tiles until it thits another piece or goes out of the board.
     * If an occupied tile is found in the direction that is also put in the returned set
     * @param startingTile current position of the piece
     * @param dx x increment for the discovery
     * @param dy y increment for the discovery
     * @return the discovered set of tiles
     */
    public ArrayList<Tile> discoverDirection(Tile startingTile, int dx, int dy) {

        int currentCol = startingTile.getCol();
        int currentRow = startingTile.getRow();

        ArrayList<Tile> discoveredTiles = new ArrayList<>();

        while (true) {
            currentCol += dx;
            currentRow += dy;
            if (currentCol < 0 || currentCol >= 8 || currentRow < 0 || currentRow >= 8) {
                return discoveredTiles;
            } else {
                Tile t = ChessBoard.board.getTileAt(currentCol, currentRow);
                discoveredTiles.add(t);

                if( ! t.isEmpty() ){
                    return discoveredTiles;
                }
            }
        }
    }


}
