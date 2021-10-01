package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashMap;
import java.util.HashSet;

public class King extends Figure{
    public King( FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.KING, figCol,posCol,posRow);

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<Tile>();


        HashMap<Tile, HashSet<Figure>> illegalTiles = ChessBoard.board.getTilesInCheckFor(color);

        for(int i = -1 ; i <=1; i++){
            for(int j = -1; j <= 1; j++){
                if( ! (j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (iCandidate < 8 && iCandidate >= 0 && jCandidate < 8 && jCandidate >= 0) {
                        Tile t = ChessBoard.board.getTileAt(iCandidate,jCandidate);
                        if (!illegalTiles.containsKey(t) && (t.isEmpty() || t.getFig().isDifferentColorAs(this))) {
                            moves.add(t);
                        }
                    }
                }
            }
        }
        return moves;
    }

}
