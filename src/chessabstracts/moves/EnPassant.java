package chessabstracts.moves;

import boardelements.ChessBoard;
import boardelements.Square;
import boardelements.pieces.Piece;


public class EnPassant extends Move {

    Square originalCapturedPieceSquare;

    public EnPassant(Piece actorPiece, Square from, Square to, Piece capturedPiece) {
        super(actorPiece, from, to, capturedPiece);
        originalCapturedPieceSquare = ChessBoard.board.getSquareAt(to.getCol(),from.getRow());
    }

    @Override
    public void undo(){
        super.undo();
        Square originalSquareOfCapturedPiece = ChessBoard.board.getSquareAt(capturedPiece.getCol(), actorPiece.getRow());
        capturedPiece.moveTo(originalSquareOfCapturedPiece);
    }

}
