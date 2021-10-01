package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashMap;
import java.util.HashSet;

public class Knight extends Figure{
    public Knight(FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.KNIGHT, figCol,posCol,posRow);

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();

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
                            moves.add(candidateTile);
                        }
                    }
                }
            }
        }
        return moves;

    }
}
