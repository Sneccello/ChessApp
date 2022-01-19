package ChessAbstracts.Moves;

import BoardElements.ChessBoard;
import BoardElements.Pieces.King;
import BoardElements.Pieces.Rook;
import BoardElements.Square;

import java.util.ArrayList;

public class Castle extends Move{

    Square rookOriginalSquare;
    Square rookCastleTargetSquare;
    Rook rook;
    King king;
    public Castle(King king, Square from, Square to, Rook rook) {
        super(king, from, to);
        this.rookOriginalSquare = rook.getSquare();

        int rookCastleTargetCol = ( to.getCol() + from.getCol() ) / 2; //the rook's destination is between the king's dest and start on both sides

        this.rookCastleTargetSquare = ChessBoard.board.getSquareAt(rookCastleTargetCol,rook.getRow());
        this.rook = rook;
        this.king = king;
    }

    @Override
    public void undo(){

        rook.moveTo(rookOriginalSquare);
        super.undo();



    }

    @Override
    public void execute(){
        super.execute();
        king.castled(true);
        rook.moveTo(rookCastleTargetSquare);
    }


}
