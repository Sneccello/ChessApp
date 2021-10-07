package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;

import java.util.HashSet;

public class Knight extends Piece {
    public Knight(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.KNIGHT, figCol,posCol,posRow,side);

    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Tile> availableTiles = new HashSet<>();

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
                        if (candidateTile.isEmpty() || candidateTile.getFig().isDifferentColorAs(this)) {
                            availableTiles.add(candidateTile);
                            if(candidateTile.getFig() != null && candidateTile.getFig().isTheEnemyKingFor(this)){
                                ChessBoard.board.registerCheckEnemyKing(this,null); //knight check cannot be blocked
                            }
                        }
                        else if( ! candidateTile.getFig().isDifferentColorAs(this)){//this tile is protected and the enemy king cannot capture
                            ChessBoard.board.addIllegalKingTileForOpponent(this,candidateTile);
                        }

                    }
                }
            }
        }
        return convertTilesToMoves(this,availableTiles);

    }
}
