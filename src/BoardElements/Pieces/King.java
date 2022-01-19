package BoardElements.Pieces;

import AI.EvaluationAspects.PieceEvaluation.CastlingRightsBonus;
import AI.EvaluationAspects.PieceEvaluation.KingTropism;
import AI.EvaluationAspects.PieceEvaluation.PawnShield;
import BoardElements.*;
import ChessAbstracts.BinaryFlag;
import ChessAbstracts.CastlingPiece;
import ChessAbstracts.Check;
import ChessAbstracts.Moves.Castle;
import ChessAbstracts.Moves.Move;

import java.util.HashSet;
import java.util.LinkedList;

public class King extends Piece implements CastlingPiece {

    private BinaryFlag leftStartingPositionFlag = new BinaryFlag(false);

    private BinaryFlag castled = new BinaryFlag(false);
    private Rook rookShortSide;
    private Rook rookLongSide;


    private final LinkedList<Check> checks = new LinkedList<>();

    public King(PieceColor PiececCol, int posCol, int posRow, Side mySide) {
        super(PieceType.KING, PiececCol,posCol,posRow,mySide);


    }

    public void setRooks(Rook longSideRook, Rook shortSideRook){
        rookShortSide = shortSideRook;
        rookLongSide = longSideRook;
    }


    public void castled(boolean b){
        castled.setValue(b);
    }


    @Override
    protected void initializeEvaluationAspects() {
        evaluationAspects.add(new KingTropism(this, mySide.getOpponent()));
        evaluationAspects.add(new PawnShield(this));
        evaluationAspects.add(new CastlingRightsBonus(this));
    }

    @Override
    protected HashSet<Square> calculateControlledSquares() {
        HashSet<Square>  controlledSquares = new HashSet<>();

        for(int i = -1 ; i <=1; i++){
            for(int j = -1; j <= 1; j++){
                if( ! (j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (ChessBoard.board.checkIfCoordsAreOnTheBoard(iCandidate,jCandidate)) {
                        Square s = ChessBoard.board.getSquareAt(iCandidate,jCandidate);
                        controlledSquares.add(s);
                    }
                }
            }
        }

        return controlledSquares;
    }




    @Override
    public HashSet<Move> calculatePossibleMoves() {

        HashSet<Square> illegalSquares = ChessBoard.board.getIllegalKingSquaresFor(color);



        HashSet<Square> controlledSquares = calculateControlledSquares();
        ChessBoard.board.addIllegalKingSquaresForOpponent(this,controlledSquares);

        controlledSquares.removeIf(illegalSquares::contains);

        HashSet<Square> possibleSquares = controlledSquares;

        possibleSquares = filterIllegalMovesFromControlledSquares(possibleSquares);

        HashSet<Move> possibleMoves = convertSquaresToMoves(this,possibleSquares);

        //checking for castle, adding them to possible moves if can


        if(canCastleShort()){
            Castle castle = new Castle(this,square,ChessBoard.board.getSquareAt(getCol()+2, getRow()), rookShortSide);
            castle.addFlagToResetWhenUndone(castled);
            castle.addFlagToResetWhenUndone(leftStartingPositionFlag);
            castle.addFlagToResetWhenUndone(rookShortSide.getLeftStartingSquareFlag());

            possibleMoves.add(castle);
        }

        if(canCastleLong()){
            Castle castle = new Castle(this,square,ChessBoard.board.getSquareAt(getCol()-2, getRow()), rookLongSide);
            castle.addFlagToResetWhenUndone(castled);
            castle.addFlagToResetWhenUndone(leftStartingPositionFlag);
            castle.addFlagToResetWhenUndone(rookLongSide.getLeftStartingSquareFlag());

            possibleMoves.add(castle);
        }

        return possibleMoves;
    }


    public boolean hasLeftStartingSquare(){
        return leftStartingPositionFlag.value();
    }

    public void setLeftStartingSquareFlag(boolean b){
        leftStartingPositionFlag.setValue(b);
    }

    @Override
    public BinaryFlag getLeftStartingSquareFlag() {
        return leftStartingPositionFlag;
    }

    public boolean canCastleShort(){
        if( rookShortSide.hasLeftStartingSquare() || hasLeftStartingSquare()){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Square> illegalSquares = board.getIllegalKingSquaresFor(color);
        boolean castlingIsLegal = true;

        Square castlingTargetSquare = ChessBoard.board.getSquareAt(getCol()+2, getRow());

        int targetDistance = castlingTargetSquare.getCol() - getCol();

        for(int i = 1; i <= targetDistance ; i++){
            Square s = board.getSquareAt(getCol() + i, getRow());
            if(!s.isEmpty() || illegalSquares.contains(s)){
                castlingIsLegal = false;
            }
        }

        if(illegalSquares.contains(square)){ //=in check lol
            castlingIsLegal = false;
        }


        return castlingIsLegal;
    }





    public boolean canCastleLong(){
        if( rookLongSide.hasLeftStartingSquare() || hasLeftStartingSquare()){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Square> illegalSquares = board.getIllegalKingSquaresFor(color);
        boolean castlingIsLegal = true;

        Square castlingTargetSquare = ChessBoard.board.getSquareAt(getCol()-2, getRow());

        int targetDistance = getCol() - castlingTargetSquare.getCol();

        for(int i = 1; i <= targetDistance ; i++){
            Square s = board.getSquareAt(getCol()-i, getRow());
            if(!s.isEmpty() || illegalSquares.contains(s)){
                castlingIsLegal = false;
            }
        }

        if(illegalSquares.contains(square)){ //=in check lol
            castlingIsLegal = false;
        }

        return castlingIsLegal;
    }

    public boolean isInCheck(){
        return ! checks.isEmpty();
    }

    public boolean isInDoubleCheck(){
        return checks.size() > 1;
    }

    public void clearChecks(){
        checks.clear();
    }

    public HashSet<Square> getSquaresToEndCheck(){
        if(checks.size() > 1){
            return new HashSet<>();//cannot be blocked, empty set
        }
        return checks.get(0).getPossibleEndingSquares();
    }

    public void addCheck(Check newCheck){

        for(Check check : checks){
            if(check.getChecker() == newCheck.getChecker()){
                return;
            }
        }

        checks.add(newCheck);
    }

    public LinkedList<Check> getChecks(){
        return checks;
    }


    @Override
    public void moveTo(Square newSquare){

        leftStartingPositionFlag.setValue(true);

        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;

    }

}
