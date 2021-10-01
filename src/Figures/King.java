package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashMap;
import java.util.HashSet;

public class King extends Figure{

    private boolean stillCanCastleShort = true;
    private boolean stillCanCastleLong = true;

    public King( FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.KING, figCol,posCol,posRow);
    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<Tile>();

        HashSet<Tile> illegalTiles = ChessBoard.board.getIllegalKingMovesFor(color);

        for(int i = -1 ; i <=1; i++){ //checking neighbouring tiles
            for(int j = -1; j <= 1; j++){
                if( ! (j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (iCandidate < 8 && iCandidate >= 0 && jCandidate < 8 && jCandidate >= 0) {
                        Tile t = ChessBoard.board.getTileAt(iCandidate,jCandidate);
                        if(!illegalTiles.contains(t) && (t.isEmpty() || t.getFigureOnThisTile().isDifferentColorAs(this))) {
                            moves.add(t);
                        }

                    }
                }
            }
        }

        //checking for castle

        if(canCastleShort()){
            moves.add(ChessBoard.board.getTileAt(getCol()+2, getRow()));
        }
        if(canCastleLong()){
            moves.add(ChessBoard.board.getTileAt(getCol()-2, getRow()));
        }

        return moves;
    }


    private boolean canCastleShort(){
        if(!stillCanCastleShort){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Tile> illegalMoves = board.getIllegalKingMovesFor(color);
        boolean canShortCastle = true;
        for(int i = 1; i <=2 ; i++){
            Tile t = board.getTileAt(getCol()+i, getRow());
            if(!t.isEmpty() || illegalMoves.contains(t)){
                canShortCastle = false;
            }
        }

        if(illegalMoves.contains(tile) || illegalMoves.contains(board.getTileAt(getCol()+3,getRow()))){
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
        HashSet<Tile> illegalMoves = board.getIllegalKingMovesFor(color);
        boolean canLongCastle = true;
        for(int i = 1; i <=3 ; i++){
            Tile t = board.getTileAt(getCol()-i, getRow());
            if(!t.isEmpty() || illegalMoves.contains(t)){
                canLongCastle = false;
            }
        }

        if(illegalMoves.contains(tile) || illegalMoves.contains(board.getTileAt(getCol()-4,getRow()))){
            canLongCastle = false;
        }

        return canLongCastle;
    }


    @Override
    public void moveTo(Tile newTile){
        if(stillCanCastleShort && newTile.getCol() == 6){//short castle
            Tile rookDest = ChessBoard.board.getTileAt(5, getRow());
            ChessBoard.board.getTileAt(7,getRow()).getFigureOnThisTile().moveTo(rookDest);
        }
        else if(stillCanCastleLong && newTile.getCol() == 2){ //long castle
            Tile rookDest = ChessBoard.board.getTileAt(3, getRow());
            ChessBoard.board.getTileAt(0,getRow()).getFigureOnThisTile().moveTo(rookDest);
        }


        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;
        stillCanCastleShort = false;
        stillCanCastleLong = false;
    }



}
