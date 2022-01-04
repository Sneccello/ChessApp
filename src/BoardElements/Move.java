package BoardElements;

import Pieces.Piece;

public class Move {


    private final Square from;
    private final Square to;
    private final Piece piece;
    private final Piece capturedPiece;

    public Move(Piece actor, Square from, Square to){
        this.from = from;
        this.to = to;
        this.piece = actor;
        this.capturedPiece = to.getPieceOnThisSquare();

    }

    public Move(Piece actor,Square from, Square to, Piece capturedPiece){
        this.to = to;
        this.piece = actor;
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

    public void undo(){//TODO enpassant undo wrong placement
        piece.moveTo(from);
        if(capturedPiece != null){
            capturedPiece.moveTo(to);
            capturedPiece.getSide().addPiece(capturedPiece);

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
