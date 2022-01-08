package BoardElements.Pieces;

import BoardElements.*;
import ChessAbstracts.Check;
import ChessAbstracts.Moves.Castle;
import ChessAbstracts.Moves.Move;

import java.util.HashSet;
import java.util.LinkedList;

public class King extends Piece {

    private boolean leftStartingPosition = false;

    private boolean castled = false;
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
        castled = castled;
    }

    @Override
    public double calculateRelativeValue() {
        relativeValue =  calculateSafetyAndActivity() + + pieceSquareTableDB.getTableValue(this);
        return  relativeValue;
    }

    @Override
    protected HashSet<Square> calculateControlledSquares() {
        HashSet<Square>  controlledSquares = new HashSet<>();

        for(int i = -1 ; i <=1; i++){
            for(int j = -1; j <= 1; j++){
                if( ! (j == 0 && i == 0)) {
                    int iCandidate = getCol() + i;
                    int jCandidate = getRow() + j;
                    if (isValidSquare(iCandidate,jCandidate)) {
                        Square s = ChessBoard.board.getSquareAt(iCandidate,jCandidate);
                        controlledSquares.add(s);
                    }
                }
            }
        }

        return controlledSquares;
    }

    public int countPawnShield(){
        int rowAheadIdx = getRow() + rowIncrementTowardsCenter;
        int pawnShield = 0;

        for(int i = -1; i <= 1 ; i++) {
            int colToCheck = getCol() + i;
            if (isValidSquare(colToCheck,rowAheadIdx)) {
                Square t1 = ChessBoard.board.getSquareAt(getCol(), rowAheadIdx);
                if( ! t1.isEmpty() && t1.getPieceOnThisSquare().getType() == PieceType.PAWN){
                    pawnShield++;
                }
            }
        }
        return pawnShield;

    }

    private double calculateSafetyAndActivity(){


        int nPinnedPiecesToTheKing = mySide.sumPins();

        int nCastleRights = 0;
        nCastleRights += rookShortSide.canCastle() && ! leftStartingPosition ? 1 : 0;
        nCastleRights += rookLongSide.canCastle() && ! leftStartingPosition ? 1 : 0;


        double tropism = mySide.getOpponent().evaluateKingTropism(square);

        double safety = 0.5 * nCastleRights - 1.0 * nPinnedPiecesToTheKing - 0.8 * tropism;
        if(castled && ! ChessBoard.board.inEndGame()){
            safety += 0.75 * countPawnShield();
        }

        return safety;
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
            possibleMoves.add(new Castle(this,square,ChessBoard.board.getSquareAt(getCol()+2, getRow()), rookShortSide));
        }
        //    public Castle(King king, Square from, Square to, Rook rook) {
        if(canCastleLong()){
            possibleMoves.add(new Castle(this,square,ChessBoard.board.getSquareAt(getCol()-2, getRow()), rookLongSide));
        }


        return possibleMoves;
    }

    public void leftStartingSquare(boolean b){
        leftStartingPosition = b;
    }


    private boolean canCastleShort(){
        if( ! rookShortSide.canCastle() || leftStartingPosition){
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

        System.out.println(castlingIsLegal);
        return castlingIsLegal;
    }





    private boolean canCastleLong(){
        if( ! rookLongSide.canCastle() || leftStartingPosition){
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

        leftStartingPosition = true;

        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;

    }

}