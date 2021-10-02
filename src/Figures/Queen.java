package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashSet;

public class Queen extends SliderPiece{
    public Queen( FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.QUEEN, figCol,posCol,posRow);

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();
        for(int i = -1; i <=1; i+=1 ){
            for(int j = -1; j <= 1; j+=1) {
                if (!(i == 0 && j == 0)) {
                    moves.addAll(getPossibleMovesInDir(getCol(), getRow(), i, j));
                }
            }

        }
        moves.remove(ChessBoard.board.getTileAt(getCol(),getRow()));//moving to where we are is not a move;
        return moves;
    }



}
