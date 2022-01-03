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
    public double calculateRelativeValue() {
        int penaltyForEarlyMovement = Math.max(5 - ChessBoard.board.getMoveCount(),0); //penalize for example the first 5 moves
        int mobility = possibleMoves.size();

        relativeValue =  0.2 * penaltyForEarlyMovement + 1.0/10 * mobility + pieceSquareTableDB.getTableValue(this);
        return  relativeValue;
    }

    @Override
    protected HashSet<Tile> calculateControlledTiles() {
        HashSet<Tile> controlledTiles = new HashSet<>();
        for(int i = -1; i <=1; i+=1 ){
            for(int j = -1; j <= 1; j+=1) {
                if (!(i == 0 && j == 0)) {
                    controlledTiles.addAll(getControlledTilesInDir(tile, i, j));
                }
            }
        }
        return controlledTiles;
    }



}
