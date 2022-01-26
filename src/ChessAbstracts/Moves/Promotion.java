package ChessAbstracts.Moves;

import BoardElements.Pieces.*;
import BoardElements.Side;
import BoardElements.Square;

public class Promotion extends Move{


    public final PieceType promotionPieceType;

    public Promotion(Piece actorPiece, Square from, Square to, PieceType promotionPieceType) {
        super(actorPiece, from, to);
        this.promotionPieceType = promotionPieceType;
    }


    @Override
    public void undo() {
        Square promotionSquare = actorPiece.getSquare();
        promotionSquare.removePiece();
        super.undo();
    }

    private Piece createPromotedPiece(){

        Side side = actorPiece.getSide();
        int column = to.getCol();
        int row = to.getRow();


        switch (promotionPieceType){
            case KNIGHT -> {
                return new Knight(column,row,side);
            }
            case BISHOP -> {
                return new Bishop(column,row,side);
            }
            case ROOK -> {
                Rook rook = new Rook(column,row,side,side.getKing());
                rook.setLeftStartingSquare(true);
                return rook;
            }
            default -> {
                return new Queen(column,row,side);
            }
        }

    }

    @Override
    public void execute() {
        super.execute();

        Side side = actorPiece.getSide();

        Square promotionSquare = actorPiece.getSquare();
        promotionSquare.removePiece();

        Piece promotionPiece = createPromotedPiece();
        side.addPiece(promotionPiece);
        side.removePiece(actorPiece);
        promotionPiece.moveTo(promotionSquare);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + promotionPieceType.hashCode();
    }
}
