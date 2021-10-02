package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;


import java.util.HashSet;

public class Bishop extends SliderPiece{

    public Bishop(FigureColor figCol,int posCol, int posRow) {
        super(FigureTypes.BISHOP, figCol,posCol,posRow);
    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();
        for(int i = -1; i <=1; i+=2 ){
            for(int j = -1; j <= 1; j+=2){
                moves.addAll(getPossibleMovesInDir(getCol(), getRow() , i, j));
            }
        }
        moves.remove(ChessBoard.board.getTileAt(getCol(),getRow()));//moving to where we are is not a move;


        return moves;

    }



}
