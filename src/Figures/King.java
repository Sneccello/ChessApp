package Figures;

import BoardElements.*;

import java.util.HashSet;
import java.util.LinkedList;

public class King extends Piece {

    private boolean stillCanCastleShort = true;
    private boolean stillCanCastleLong = true;


    private final LinkedList<Check> checks = new LinkedList<>();

    public King(PieceColor figCol, int posCol, int posRow, Side mySide) {
        super(PieceType.KING, figCol,posCol,posRow,mySide);
    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Tile> availableTiles = new HashSet<>();

        HashSet<Tile> illegalTiles = ChessBoard.board.getIllegalKingTilesFor(color);

        for(int i = -1 ; i <=1; i++){ //checking neighbouring tiles
            for(int j = -1; j <= 1; j++){
                if( ! (j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (iCandidate < 8 && iCandidate >= 0 && jCandidate < 8 && jCandidate >= 0) {
                        Tile t = ChessBoard.board.getTileAt(iCandidate,jCandidate);
                        if(!illegalTiles.contains(t) && (t.isEmpty() || t.getPieceOnThisTile().isDifferentColorAs(this))) {
                            availableTiles.add(t);
                        }

                    }
                }
            }
        }

        //checking for castle, adding them to possible moves if can
        if(canCastleShort()){
            availableTiles.add(ChessBoard.board.getTileAt(getCol()+2, getRow()));
        }
        if(canCastleLong()){
            availableTiles.add(ChessBoard.board.getTileAt(getCol()-2, getRow()));
        }

        return convertTilesToMoves(this,availableTiles);
    }

    public void banNeighbouringTilesForEnemyKing(){
        for(int i = -1 ; i <=1; i++) { //get neighbouring tiles
            for (int j = -1; j <= 1; j++) {
                if (!(j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (iCandidate < 8 && iCandidate >= 0 && jCandidate < 8 && jCandidate >= 0) {
                        Tile t = ChessBoard.board.getTileAt(iCandidate, jCandidate);
                        ChessBoard.board.addIllegalKingTileForOpponent(this,t);
                    }
                }
            }
        }

    }


    private boolean canCastleShort(){
        if(!stillCanCastleShort){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Tile> illegalTiles = board.getIllegalKingTilesFor(color);
        boolean canShortCastle = true;
        for(int i = 1; i <=2 ; i++){
            Tile t = board.getTileAt(getCol()+i, getRow());
            if(!t.isEmpty() || illegalTiles.contains(t)){
                canShortCastle = false;
            }
        }

        if(illegalTiles.contains(tile) || illegalTiles.contains(board.getTileAt(getCol()+3,getRow()))){
            canShortCastle = false;
        }

        return canShortCastle;
    }



    public void disableCastleShort(){
        stillCanCastleShort = false;
    }

    public void disableCastleLong(){
        stillCanCastleLong = false;
    }

    private boolean canCastleLong(){
        if(!stillCanCastleLong){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Tile> illegalTiles = board.getIllegalKingTilesFor(color);
        boolean canLongCastle = true;
        for(int i = 1; i <=3 ; i++){
            Tile t = board.getTileAt(getCol()-i, getRow());
            if(!t.isEmpty() || illegalTiles.contains(t)){
                canLongCastle = false;
            }
        }

        if(illegalTiles.contains(tile) || illegalTiles.contains(board.getTileAt(getCol()-4,getRow()))){
            canLongCastle = false;
        }

        return canLongCastle;
    }

    public boolean isInCheck(){
        return ! checks.isEmpty();
    }

    public boolean isInDoubleCheck(){
        return checks.size() > 1;
    }

    public void clearChecks(){
        checks.clear();
    }

    public HashSet<Tile> getTilesToEndCheck(){
        if(checks.size() != 1){
            return null;
        }
        return checks.get(0).getPossibleEndingTiles();
    }

    public void addCheck(Check check){
        checks.add(check);
    }

    public LinkedList<Check> getChecks(){
        return checks;
    }


    @Override
    public void moveTo(Tile newTile){
        if(stillCanCastleShort && newTile.getCol() == 6){//short castle
            Tile rookDest = ChessBoard.board.getTileAt(5, getRow());
            ChessBoard.board.getTileAt(7,getRow()).getPieceOnThisTile().moveTo(rookDest);
        }
        else if(stillCanCastleLong && newTile.getCol() == 2){ //long castle
            Tile rookDest = ChessBoard.board.getTileAt(3, getRow());
            ChessBoard.board.getTileAt(0,getRow()).getPieceOnThisTile().moveTo(rookDest);
        }

        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;
        stillCanCastleShort = false;
        stillCanCastleLong = false;
    }

}
