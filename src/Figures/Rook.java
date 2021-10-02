package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;

import java.util.HashSet;

public class Rook extends SliderPiece{

    King myKing;//need to store so that castling rights are looked after
    private boolean leftStartingPosition = false;
    public Rook(FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.ROOK, figCol,posCol,posRow);

        myKing = (King)ChessBoard.board.getTileAt(4,getRow()).getFigureOnThisTile();

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();

        int[] iValues = {0,1,0,-1};
        int[] jValues = {1,0,-1,0};

        for(int c = 0; c < 4; c++) {

            moves.addAll(getPossibleMovesInDir(tile.getCol(), tile.getRow() , iValues[c] ,jValues[c]));
        }
        moves.remove(ChessBoard.board.getTileAt(tile.getCol(),tile.getRow()));//moving to where we are is not a move;

        return moves;
    }

    @Override
    public void moveTo(Tile newTile){
        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;

        if(!leftStartingPosition){

            if(getCol() == 7){
                myKing.disableCastleShort();
            }
            else{
                myKing.disableCastleLong();
            }

            leftStartingPosition = true;
        }

    }



}
