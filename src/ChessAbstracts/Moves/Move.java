package ChessAbstracts.Moves;

import BoardElements.Square;
import BoardElements.Pieces.Piece;

public class Move {


    protected final Square from;
    protected final Square to;
    protected final Piece piece;
    protected final Piece capturedPiece;

    public Move(Piece piece, Square from, Square to){
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPiece = to.getPieceOnThisSquare();

    }

    //TODO reset king and rook flags for example castling
    public Move(Piece piece,Square from, Square to, Piece capturedPiece){
        this.to = to;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.from = from;
    }


    public Piece getCapturedPiece(){
        return capturedPiece;
    }


    public Square getTo() {
        return to;
    }

    public Square getFrom() {
        return from;
    }

    public Piece getPiece() {
        return piece;
    }


    public void execute(){
        piece.moveTo(to);
        if(capturedPiece != null){
            capturedPiece.beCaptured();
        }
    }

    public void undo(){
        piece.moveTo(from);

        if(capturedPiece != null){ //TODO refactor new class NullPiece
            capturedPiece.revive(to);
        }

    }


    @Override
    public int hashCode() {
        return from.hashCode()*to.hashCode()*piece.hashCode() * (capturedPiece == null? 1 : capturedPiece.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj.getClass() != getClass()){
            return false;
        }

        Move m = (Move) obj;
        return m.getTo() == to && m.getFrom() == from && m.getPiece() == piece && m.getCapturedPiece() == capturedPiece;
    }

    @Override
    public String toString() {
        return String.format("Move from %s to %s piece: %s %s capturing %s", from, to, piece.getColor(), piece.getType(), capturedPiece);
    }
}
