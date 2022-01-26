package BoardElements.Pieces;

import BoardElements.*;
import ChessAbstracts.BinaryFlag;
import ChessAbstracts.Check;
import ChessAbstracts.Moves.EnPassant;
import ChessAbstracts.Moves.Move;
import ChessAbstracts.Moves.Promotion;

import javax.accessibility.AccessibleTextSequence;
import java.util.HashSet;

import static BoardElements.ChessBoard.board;

//TODO only the first pawn to make a move can lunge forward for some reason
public class Pawn extends Piece {

    int promotionRowIndex;
    BinaryFlag leftStartingSquareFlag = new BinaryFlag(false);

    private static PieceType promotionPieceType = PieceType.QUEEN;

    public Pawn( int posCol, int posRow, Side side) {
        super(PieceType.PAWN,posCol,posRow,side);
        rowIncrementTowardsOpponent = (color == PieceColor.WHITE ? 1 : -1);
        promotionRowIndex = Math.max(0, rowIncrementTowardsOpponent *7);

    }


    public static void setPromotionPieceType(PieceType pieceType){
        promotionPieceType = pieceType;
    }

    @Override
    protected void initializeEvaluationAspects() {
        //TODO
        //no explicit evaluation here, since for example the pawn structure and mobility are evaluated the side -level
    }

    @Override
    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> controlledSquares = new HashSet<>();

        for(int xOffset = -1 ; xOffset <= 1; xOffset+=2){//test capturing forward

            if(ChessBoard.board.checkIfCoordsAreOnTheBoard(getCol() + xOffset, getRow() + rowIncrementTowardsOpponent)){
                Square Square = board.getSquareAt(getCol()+xOffset,getRow()+ rowIncrementTowardsOpponent);

                controlledSquares.add(Square);

            }
        }


        return controlledSquares;
    }

    private boolean hasLeftStartingSquare(){
        return leftStartingSquareFlag.value();
    }


    private HashSet<Move> generateAllPromotionPossibilities(Square targetSquare){

        PieceType[] promotionPossibilities = {PieceType.ROOK,PieceType.QUEEN,PieceType.BISHOP,PieceType.KNIGHT};

        HashSet<Move> promotions = new HashSet<>();
        for(PieceType pt : promotionPossibilities){
            Promotion promotion = new Promotion(this,getSquare(),targetSquare,pt);
            promotions.add(promotion);
        }
        return promotions;
    }

    private HashSet<Move> generateForwardMoves(int stepSize, Square targetSquare){

        if(targetSquare == null){
            return new HashSet<>();
        }
        HashSet<Move> forwardMoves = new HashSet<>();
        if(stepSize == 2){
            Move lungeForward = new Move(this,square,targetSquare);
            lungeForward.addFlagToResetWhenUndone(leftStartingSquareFlag);

            forwardMoves.add(lungeForward);
        }
        else if (stepSize == 1) {

            if (targetSquare.getRow() == promotionRowIndex) {
                HashSet<Move> promotions = generateAllPromotionPossibilities(targetSquare);
                forwardMoves.addAll(promotions);
            } else {
                forwardMoves.add(new Move(this, square, targetSquare));
            }
        }

        return forwardMoves;
    }


    private HashSet<Move> calculateForwardSquares(){
        HashSet<Move> forwardMoves = new HashSet<>();
        for(int step = 1; step <=2 ; step++){ //test moving ahead 1 or 2
            if(step == 1 || ! hasLeftStartingSquare()) {

                Square targetSquare = testMoveForwardBy(step);

                HashSet<Move> generatedMoves = generateForwardMoves(step, targetSquare);
                forwardMoves.addAll(generatedMoves);
            }

        }

        return forwardMoves;
    }



    private HashSet<Move> calculateEnPassantMoves(){
        HashSet<Move> enPassantMoves = new HashSet<>();

        int startingRowIdxForEnPassant = ( color == PieceColor.WHITE ? 4 : 3);
        if(getRow() == startingRowIdxForEnPassant){
            Move lastMove = ChessBoard.board.getLastMove();
            if(lastMove.getActorPiece().getType() == PieceType.PAWN){ //if pawn made the last move
                //if that pawn stepped 2 Squares forward from its starting position
                if(lastMove.getTo().getRow() == startingRowIdxForEnPassant && lastMove.getFrom().getRow() == startingRowIdxForEnPassant+ rowIncrementTowardsOpponent *2){

                    if(getCol()+1 < 8 && lastMove.getFrom().getCol() == getCol()+1){ //if the last move was made on one side (column-wise)
                        Square targetSquare = ChessBoard.board.getSquareAt(getCol()+1,getRow()+ rowIncrementTowardsOpponent);
                        enPassantMoves.add(new EnPassant(this,square,targetSquare, lastMove.getActorPiece()));

                    }
                    else if(getCol()-1 >= 0 && lastMove.getFrom().getCol() == getCol()-1){//if the last move was made on my other side (column-wise)
                        Square target = ChessBoard.board.getSquareAt(getCol()-1,getRow()+ rowIncrementTowardsOpponent);
                        enPassantMoves.add(new EnPassant(this,square,target,lastMove.getActorPiece()));
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
    protected HashSet<Move> convertSquaresToMoves(Piece p, HashSet<Square> Squares) {


        HashSet<Move> moves = new HashSet<>();
        for(Square s: Squares) {

            if (s.getRow() == promotionRowIndex) {
                HashSet<Move> promotions = generateAllPromotionPossibilities(s);
                moves.addAll(promotions);
            } else {
                moves.add(new Move(p, p.getSquare(), s));
            }
        }
        return moves;

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
        int rowAhead = getRow()+amount * rowIncrementTowardsOpponent;
        if(rowAhead >= 0 && rowAhead < 8){
            Square s = board.getSquareAt(getCol(),rowAhead);
            boolean blocked = false;
            for(int i = 1; i <= amount ; i++){//checking if it can step ahead 1 or 2 (<=amount) steps being not blocked
                if( ! board.getSquareAt(getCol(),getRow()+i* rowIncrementTowardsOpponent).isEmpty()){
                    blocked = true;
                }
            }
            if( ! blocked){
                return s;
            }
        }
        return null;
    }

    public int getPromotionRowIndex() {
        return promotionRowIndex;
    }

    private boolean canCapture(Square s){
        return s.getPieceOnThisSquare() != null && s.getPieceOnThisSquare().isDifferentColorAs(this);
    }

    private boolean moveIsPromotion(Move move){

        return move.getTo().getRow() == promotionRowIndex;

    }


    private void promoteTo(PieceType wantedPromotionType){
        for(Move move: possibleMoves){
            if(moveIsPromotion(move)){
                Promotion promotion = (Promotion) move;
                if(promotion.promotionPieceType == wantedPromotionType){
                    promotion.execute();
                    board.moveWasMade(promotion);
                    break;
                }
            }
        }

    }

    @Override
    public void tryToMoveToSquare(Square destination) {

        for(Move move : possibleMoves){
            if(move.getTo() == destination){
                if(moveIsPromotion(move)){
                    promoteTo(promotionPieceType);
                }
                else{
                    move.execute();
                    board.moveWasMade(move);
                }
                break;
            }
        }

    }

    @Override
    public void moveTo(Square newSquare){
        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;
        leftStartingSquareFlag.setValue(true);


    }




}
