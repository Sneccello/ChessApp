package Pieces;

import BoardElements.*;

import java.util.HashSet;
import java.util.LinkedList;

public class King extends Piece {

    private boolean stillCanCastleShort = true;
    private boolean stillCanCastleLong = true;
    private boolean castled = false;


    private final LinkedList<Check> checks = new LinkedList<>();

    public King(PieceColor PiececCol, int posCol, int posRow, Side mySide) {
        super(PieceType.KING, PiececCol,posCol,posRow,mySide);
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
        nCastleRights += stillCanCastleShort ? 1 : 0;
        nCastleRights += stillCanCastleLong ? 1 : 0;


        double tropism = mySide.getOpponent().evaluateKingTropism(Square);

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

        //checking for castle, adding them to possible moves if can
        if(canCastleShort()){
            possibleSquares.add(ChessBoard.board.getSquareAt(getCol()+2, getRow()));
        }
        if(canCastleLong()){
            possibleSquares.add(ChessBoard.board.getSquareAt(getCol()-2, getRow()));
        }

        possibleSquares = filterIllegalMovesFromControlledSquares(possibleSquares);

        return convertSquaresToMoves(this,possibleSquares);
    }

//    public void banNeighbouringSquaresForEnemyKing(){
//        for(int i = -1 ; i <=1; i++) { //get neighbouring Squares
//            for (int j = -1; j <= 1; j++) {
//                if (!(j == 0 && i == 0)) {
//                    int iCandidate = getCol() + i;
//                    int jCandidate = getRow() + j;
//                    if (isValidSquare(iCandidate,jCandidate)) {
//                        Square s = ChessBoard.board.getSquareAt(iCandidate, jCandidate);
//                        ChessBoard.board.addIllegalKingSquareForOpponent(this,t);
//                    }
//                }
//            }
//        }
//    }


    private boolean canCastleShort(){
        if(!stillCanCastleShort){
            return false;
        }

        HashSet<Square> illegalSquares = ChessBoard.board.getIllegalKingSquaresFor(color);
        boolean canShortCastle = true;
        for(int i = 1; i <=2 ; i++){
            Square s = ChessBoard.board.getSquareAt(getCol()+i, getRow());
            if(!s.isEmpty() || illegalSquares.contains(s)){
                canShortCastle = false;
            }
        }

        if(illegalSquares.contains(Square) || illegalSquares.contains(ChessBoard.board.getSquareAt(getCol()+3,getRow()))){
            canShortCastle = false;
        }

        return canShortCastle;
    }



    public void disableCastleShort(){
        stillCanCastleShort = false;
    }

    public void disableCastleLong(){
        stillCanCastleLong = false;
    }

    private boolean canCastleLong(){
        if(!stillCanCastleLong){
            return false;
        }
        ChessBoard board = ChessBoard.board;
        HashSet<Square> illegalSquares = board.getIllegalKingSquaresFor(color);
        boolean canLongCastle = true;
        for(int i = 1; i <=3 ; i++){
            Square s = board.getSquareAt(getCol()-i, getRow());
            if(!s.isEmpty() || illegalSquares.contains(s)){
                canLongCastle = false;
            }
        }

        if(illegalSquares.contains(Square) || illegalSquares.contains(board.getSquareAt(getCol()-4,getRow()))){
            canLongCastle = false;
        }

        return canLongCastle;
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
        if(stillCanCastleShort && newSquare.getCol() == 6){//short castle
            Square rookDest = ChessBoard.board.getSquareAt(5, getRow());
            ChessBoard.board.getSquareAt(7,getRow()).getPieceOnThisSquare().moveTo(rookDest);
            castled = true;
        }
        else if(stillCanCastleLong && newSquare.getCol() == 2){ //long castle
            Square rookDest = ChessBoard.board.getSquareAt(3, getRow());
            ChessBoard.board.getSquareAt(0,getRow()).getPieceOnThisSquare().moveTo(rookDest);
            castled = true;
        }

        Square.removePiece();
        newSquare.addPiece(this);
        Square = newSquare;
        stillCanCastleShort = false;
        stillCanCastleLong = false;
    }

}
