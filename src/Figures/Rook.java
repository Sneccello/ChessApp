package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;

import java.util.HashSet;

public class Rook extends SliderPiece{
    public Rook(FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.ROOK, figCol,posCol,posRow);
    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();

        int[] iValues = {0,1,0,-1};
        int[] jValues = {1,0,-1,0};

        for(int c = 0; c < 4; c++) {

            calculateMovesInDir(tile.getCol(), tile.getRow() , iValues[c] ,jValues[c], moves);
        }
        moves.remove(ChessBoard.board.getTileAt(tile.getCol(),tile.getRow()));//moving to where we are is not a move;

        return moves;
    }


}
