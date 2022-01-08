package ChessAbstracts.Moves;

import BoardElements.ChessBoard;
import BoardElements.Square;
import BoardElements.Pieces.Piece;


public class EnPassant extends Move {

    Square originalCapturedPieceSquare;

    public EnPassant(Piece piece, Square from, Square to, Piece capturedPiece) {
        super(piece, from, to, capturedPiece);
        originalCapturedPieceSquare = ChessBoard.board.getSquareAt(to.getCol(),from.getRow());
    }

    @Override
    public void undo(){
        super.undo();
        Square originalSquareOfCapturedPiece = ChessBoard.board.getSquareAt(capturedPiece.getCol(), piece.getRow());
        capturedPiece.moveTo(originalSquareOfCapturedPiece);
    }

}
