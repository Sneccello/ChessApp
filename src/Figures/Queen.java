package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;

import java.util.HashSet;

public class Queen extends SliderPiece{
    public Queen(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.QUEEN, figCol,posCol,posRow,side);

    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Tile> availableTiles = new HashSet<>();
        for(int i = -1; i <=1; i+=1 ){
            for(int j = -1; j <= 1; j+=1) {
                if (!(i == 0 && j == 0)) {
                    availableTiles.addAll(getPossibleMovesInDir(getCol(), getRow(), i, j));
                }
            }

        }
        availableTiles.remove(ChessBoard.board.getTileAt(getCol(),getRow()));//moving to where we are is not a move;

        return convertTilesToMoves(this,availableTiles);
    }



}
