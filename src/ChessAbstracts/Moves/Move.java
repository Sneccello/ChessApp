package ChessAbstracts.Moves;

import BoardElements.Square;
import BoardElements.Pieces.Piece;
import ChessAbstracts.BinaryFlag;

import java.util.ArrayList;

public class Move {


    protected final Square from;
    protected final Square to;
    protected final Piece actorPiece;
    protected final Piece capturedPiece;
    protected final ArrayList<BinaryFlag> flagsToResetWhenUndone = new ArrayList<>();

    public Move(Piece actorPiece, Square from, Square to){
        this.from = from;
        this.to = to;
        this.actorPiece = actorPiece;
        this.capturedPiece = to.getPieceOnThisSquare();
    }

    //TODO reset king and rook flags for example castling
    public Move(Piece actorPiece, Square from, Square to, Piece capturedPiece){
        this.to = to;
        this.actorPiece = actorPiece;
        this.capturedPiece = capturedPiece;
        this.from = from;
    }


    public void addFlagToResetWhenUndone(BinaryFlag b){
        flagsToResetWhenUndone.add(b);
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

    public Piece getActorPiece() {
        return actorPiece;
    }


    public void execute(){
        actorPiece.moveTo(to);
        if(capturedPiece != null){
            capturedPiece.beCaptured();
        }
    }

    public void undo(){
        actorPiece.moveTo(from);
        if(capturedPiece != null){ //TODO refactor new class NullPiece
            capturedPiece.reviveAt(to);
        }
        resetFlags();
    }

    protected void resetFlags(){
        for(BinaryFlag fo : flagsToResetWhenUndone){
           fo.negate();
        }

    }

    public double getCaptureValue(){
        if(capturedPiece == null){
            return 0;
        }
        return capturedPiece.getBasePieceValue();
    }

    @Override
    public int hashCode() {
        return from.hashCode()*to.hashCode()* actorPiece.hashCode() * (capturedPiece == null? 1 : capturedPiece.hashCode());
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
        return m.getTo() == to && m.getFrom() == from && m.getActorPiece() == actorPiece && m.getCapturedPiece() == capturedPiece;
    }

    @Override
    public String toString() {
        return String.format("Move from %s to %s piece: %s %s capturing %s", from, to, actorPiece.getColor(), actorPiece.getType(), capturedPiece);
    }
}
