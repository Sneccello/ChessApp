package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;


import java.util.HashSet;

public class Bishop extends SliderPiece{

    public Bishop(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.BISHOP, figCol,posCol,posRow,side);
    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Tile> availableTiles = new HashSet<>();
        for(int i = -1; i <=1; i+=2 ){
            for(int j = -1; j <= 1; j+=2){
                availableTiles.addAll(getPossibleMovesInDir(getCol(), getRow() , i, j));
            }
        }
        availableTiles.remove(ChessBoard.board.getTileAt(getCol(),getRow()));//moving to where we are is not a move;

        return convertTilesToMoves(this,availableTiles);

    }



}
