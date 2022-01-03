package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;

import javax.swing.*;
import java.util.HashSet;

public class Knight extends Piece {
    public Knight(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.KNIGHT, figCol,posCol,posRow,side);

    }


    @Override
    public double calculateRelativeValue() {
        int nPawnsInGame = ChessBoard.board.getNumberOfPawnsInGame();
        HashSet<Tile> mobilityTiles = calculateControlledTiles();
        HashSet<Tile> pawnControlledTilesByEnemy = mySide.getOpponent().getPawnControlledTiles();

        mobilityTiles.removeIf(pawnControlledTilesByEnemy::contains);

        int mobility = mobilityTiles.size();


        relativeValue =  - 1.0/16 * nPawnsInGame + 0.2 * mobility + pieceSquareTableDB.getTableValue(this);

        return  relativeValue;
    }

    protected HashSet<Tile> calculateControlledTiles() {
        HashSet<Tile> controlledTiles = new HashSet<>();

        for(int absRowOffset = 1; absRowOffset <= 2; absRowOffset++) {
            int absColOffset = (absRowOffset == 1 ? 2 : 1);
            for (int colSign = -1; colSign <= 1; colSign += 2) {
                for (int rowSign = -1; rowSign <= 1; rowSign += 2) {
                    int rowOffset = absRowOffset * rowSign;
                    int colOffset = absColOffset * colSign;

                    int candidateCol = getCol() + colOffset;
                    int candidateRow = getRow() + rowOffset;

                    if (candidateCol >= 0 && candidateCol < 8 && candidateRow >= 0 && candidateRow < 8) {
                        Tile candidateTile = ChessBoard.board.getTileAt(candidateCol, candidateRow);

                        controlledTiles.add(candidateTile);
                        if( ! candidateTile.isEmpty()  && candidateTile.getPieceOnThisTile().isTheEnemyKingFor(this)){
                            ChessBoard.board.registerCheckEnemyKing(this,null); //knight check cannot be blocked
                        }

                    }
                }
            }
        }
        return controlledTiles;

    }
}
