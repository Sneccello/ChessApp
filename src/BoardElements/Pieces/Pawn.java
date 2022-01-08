package BoardElements.Pieces;

import BoardElements.*;
import ChessAbstracts.Check;
import ChessAbstracts.Moves.EnPassant;
import ChessAbstracts.Moves.Lunge;
import ChessAbstracts.Moves.Move;

import java.util.HashSet;

import static BoardElements.ChessBoard.board;


public class Pawn extends Piece {

    int promotionRowIndex;
    boolean leftStartingSquare = false;

    public Pawn(PieceColor PiececCol, int posCol, int posRow, Side side) {
        super(PieceType.PAWN, PiececCol,posCol,posRow,side);
        rowIncrementTowardsCenter = (color == PieceColor.WHITE ? 1 : -1);
        promotionRowIndex = Math.max(0,rowIncrementTowardsCenter*7);

    }

    @Override
    public double calculateRelativeValue() {
        int isPassedPawn = board.checkPassedPawn(this) ? 1 : 0;
        int isBlocked = board.getSquareAt(getCol(),getRow()+rowIncrementTowardsCenter).isEmpty() ? 0 : 1;


        relativeValue =  baseValue + 0.15 * isPassedPawn - 0.1 * isBlocked + pieceSquareTableDB.getTableValue(this);
        return relativeValue;
    }

    @Override
    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> controlledSquares = new HashSet<>();

        for(int xOffset = -1 ; xOffset <= 1; xOffset+=2){//test capturing forward

            if(isValidSquare(getCol() + xOffset, getRow() + rowIncrementTowardsCenter)){
                Square Square = board.getSquareAt(getCol()+xOffset,getRow()+rowIncrementTowardsCenter);

                controlledSquares.add(Square);

            }
        }


        return controlledSquares;
    }

    public void setLeftStartingSquare(boolean b){
        leftStartingSquare = b;
    }

    private HashSet<Move> calculateForwardSquares(){
        HashSet<Move> forwardMoves = new HashSet<>();
        for(int step = 1; step <=2 ; step++){ //test moving ahead 1 or 2
            if(step == 1 || !leftStartingSquare) {
                Square targetSquare = testMoveForwardBy(step);
                if (targetSquare != null) {

                    if (step == 1) {
                        forwardMoves.add(new Move(this, square,targetSquare));
                    } else {
                        forwardMoves.add(new Lunge(this,square,targetSquare));
                    }
                }
            }
        }

        return forwardMoves;
    }



    private HashSet<Move> calculateEnPassantMoves(){
        HashSet<Move> enPassantMoves = new HashSet<>();

        int startingRowIdxForEnPassant = ( color == PieceColor.WHITE ? 4 : 3);
        if(getRow() == startingRowIdxForEnPassant){
            Move lastMove = ChessBoard.board.getLastMove();
            if(lastMove.getPiece().getType() == PieceType.PAWN){ //if pawn made the last move
                //if that pawn stepped 2 Squares forward from its starting position
                if(lastMove.getTo().getRow() == startingRowIdxForEnPassant && lastMove.getFrom().getRow() == startingRowIdxForEnPassant+rowIncrementTowardsCenter*2){

                    if(getCol()+1 < 8 && lastMove.getFrom().getCol() == getCol()+1){ //if the last move was made on one side (column-wise)
                        Square targetSquare = ChessBoard.board.getSquareAt(getCol()+1,getRow()+rowIncrementTowardsCenter);
                        enPassantMoves.add(new EnPassant(this,square,targetSquare, lastMove.getPiece()));

                    }
                    else if(getCol()-1 >= 0 && lastMove.getFrom().getCol() == getCol()-1){//if the last move was made on my other side (column-wise)
                        Square target = ChessBoard.board.getSquareAt(getCol()-1,getRow()+rowIncrementTowardsCenter);
                        enPassantMoves.add(new EnPassant(this,square,target,lastMove.getPiece()));
                    }
                }
            }
        }

        return enPassantMoves;
    }

    private HashSet<Square> calculateCaptureSquares(){
        HashSet<Square> controlledSquares = calculateControlledSquares();
        board.addIllegalKingSquaresForOpponent(this,controlledSquares);

        HashSet<Square> captures = new HashSet<>();
        for(Square target : controlledSquares){
            if( canCapture(target)) {
                captures.add(target);
                if (isTheEnemyKingForMe(this, target.getPieceOnThisSquare())) {
                    King enemyKing = (King) target.getPieceOnThisSquare();
                    enemyKing.addCheck(new Check(this, new HashSet<>()));

                }
            }

        }
        return captures;
    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Square> possibleSquares = new HashSet<>();


        HashSet<Square> captureSquares = calculateCaptureSquares();
        possibleSquares.addAll(captureSquares);

        HashSet<Move> possibleMoves = convertSquaresToMoves(this,possibleSquares);

        HashSet<Move> forwardMoves = calculateForwardSquares();
        possibleMoves.addAll( forwardMoves );

        HashSet<Move> enPassantMoves = calculateEnPassantMoves();
        possibleMoves.addAll(enPassantMoves);

        possibleMoves.removeIf(move -> ! move.getTo().isEmpty() && move.getTo().getPieceOnThisSquare().isSameColorAs(this));

        return possibleMoves;
    }



    private Square testMoveForwardBy(int amount){ //test moving forward 1 or 2
        if(amount > 2 || amount < 0){
            return null;
        }
        int rowAhead = getRow()+amount * rowIncrementTowardsCenter;
        if(rowAhead >= 0 && rowAhead < 8){
            Square s = board.getSquareAt(getCol(),rowAhead);
            boolean blocked = false;
            for(int i = 1; i <= amount ; i++){//checking if it can step ahead 1 or 2 (<=amount) steps being not blocked
                if( ! board.getSquareAt(getCol(),getRow()+i*rowIncrementTowardsCenter).isEmpty()){
                    blocked = true;
                }
            }
            if( ! blocked){
                return s;
            }
        }
        return null;
    }


    private boolean canCapture(Square s){
        return s.getPieceOnThisSquare() != null && s.getPieceOnThisSquare().isDifferentColorAs(this);
    }


    @Override
    public void moveTo(Square newSquare){
        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;
        leftStartingSquare = true;

        if(square.getRow() == promotionRowIndex){
            square.removePiece();
            mySide.removePiece(this);
            mySide.addPiece(new Queen(color,getCol(),promotionRowIndex,mySide));
            //ChessBoard.board.addPiece(new Queen(color,getCol(),promotionRowIndex)); //TODO autoqueen for now
        }

    }




}
