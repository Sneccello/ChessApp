package ChessAbstracts.Moves;

import BoardElements.ChessBoard;
import BoardElements.Pieces.King;
import BoardElements.Pieces.Rook;
import BoardElements.Square;

public class Castle extends Move{

    Square rookOriginalSquare;
    Square rookCastleTargetSquare;
    Rook rook;
    public Castle(King king, Square from, Square to, Rook rook) {
        super(king, from, to);
        this.rookOriginalSquare = rook.getSquare();

        int rookCastleTargetCol = ( to.getCol() + from.getCol() ) / 2; //the rook's destination is between the king's dest and start on both sides

        this.rookCastleTargetSquare = ChessBoard.board.getSquareAt(rookCastleTargetCol,rook.getRow());
        this.rook = rook;
    }

    @Override
    public void undo(){
        super.undo();
        rook.moveTo(rookOriginalSquare);
        ((King) piece ).castled(false);
        ((King) piece ).leftStartingSquare(false);
        rook.leftStartingSquare(false);

    }

    @Override
    public void execute(){
        super.execute();
        ((King) piece ).castled(true);
        rook.moveTo(rookCastleTargetSquare);
    }


}
