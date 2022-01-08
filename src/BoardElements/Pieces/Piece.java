package BoardElements.Pieces;

import BoardElements.*;
import ChessAbstracts.Moves.Move;
import ChessAbstracts.Pin;
import DataBases.PieceSquareTableDB;
import Views.PieceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

abstract public class Piece {

    protected PieceType type;
    protected PieceColor color;
    protected PieceView view;
    protected HashSet<Move> possibleMoves ;
    protected ArrayList<Pin> pins = new ArrayList<>();
    protected int rowIncrementTowardsCenter;
    protected double relativeValue;


    protected final static HashMap<PieceType,Double> basePieceValues = new HashMap<>(); //~ 2020, AlphaZero + arbitrary king value
    static{
        basePieceValues.put(PieceType.ROOK,5.63);
        basePieceValues.put(PieceType.PAWN,1.0);
        basePieceValues.put(PieceType.BISHOP,3.33);
        basePieceValues.put(PieceType.KNIGHT,3.05);
        basePieceValues.put(PieceType.QUEEN,9.5);
        basePieceValues.put(PieceType.KING,3.7);

    }
    protected final static PieceSquareTableDB pieceSquareTableDB = new PieceSquareTableDB();

    protected double baseValue;


    protected static Piece selectedPiece;

    protected Square square;
    Side mySide;

    Piece(PieceType type, PieceColor color, int posCol, int posRow,Side side){
        this.type = type;
        this.color = color;
        possibleMoves = new HashSet<>();
        mySide = side;
        String imageName = getStringForColor(color) + getStringForType(type)+".png";
        view = new PieceView(imageName, this);

        square = ChessBoard.board.getSquareAt(posCol,posRow);
        square.addPiece(this);

        baseValue = basePieceValues.get(type);

        rowIncrementTowardsCenter = ( color == PieceColor.WHITE ? 1 : -1 ) ;

    }

    public double getBaseValue() {
        return baseValue;
    }

    public double euclideanDistanceToSquare(Square s){
        double colDiff = s.getCol()-getCol();
        double rowDiff = s.getRow()-getRow();
        return Math.sqrt( colDiff*colDiff + rowDiff*rowDiff );
    }

    public int countPins(){
        return pins.size();
    }

    public void setSquare(Square s){
        square = s;
    }

    public Square getSquare(){
        return square;
    }

    protected boolean areSameColorComplex(Square t1, Square t2){

        return (t1.getCol()+t1.getRow())%2 == (t2.getCol()+t2.getRow())%2;

    }


    protected boolean isProtecting(Square s){
        for(Move m : possibleMoves){
            if(m.getTo() == s){
                return true;
            }
        }
        return false;
    }





    public boolean isDifferentColorAs(Piece p){
        if(p == null){
            return false;
        }
        return color != p.color;
    }

    public boolean isSameColorAs(Piece p){
        return p != null && !isDifferentColorAs(p);
    }

    public boolean isPinned() {
        return pins.isEmpty();
    }

    public void addPin(Pin p){
        pins.add(p);
    }

    public void clearPins(){
        pins.clear();
    }




    public abstract double calculateRelativeValue();

    public double getRelativeValue(){
        return relativeValue;
    }


    public void checkLegalMovesBeingPinned(){
        if(pins.isEmpty()){
            return;
        }
        if(pins.size() == 1){
            Iterator<Move> SquaresAvailableInPinIterator = pins.get(0).getMovesAvailable().iterator();
            HashSet<Move> mutualSet = new HashSet<>();//Squares that are legal in term of the piece's own moves and what the pin allows
            while(SquaresAvailableInPinIterator.hasNext()){
                Move m = SquaresAvailableInPinIterator.next();

                if(possibleMoves.contains(m)){
                    mutualSet.add(m);
                }
            }
            possibleMoves.clear();
            possibleMoves.addAll(mutualSet);
        }
        else{ //if a piece is in 2 different pins, it for sure, cannot move, it would break at least one of its pins
            possibleMoves.clear();
        }

    }


    protected boolean isTheEnemyKingForMe(Piece requester, Piece potentialKing){
        return potentialKing != null && potentialKing.getType() == PieceType.KING && requester.isDifferentColorAs(potentialKing);
    }

    public Side getSide(){
        return mySide;
    }

    public void limitMovesToEndCheck(HashSet<Square> targetSquaresToEndCheck){
        HashSet<Move> bin = new HashSet<>(); //avoid concurrent modification

        for(Move move : possibleMoves){//searching if the piece has a move which's target Square can end the check. The other moves are illegal
            if( ! targetSquaresToEndCheck.contains(move.getTo())){
                bin.add(move);
            }
        }
        possibleMoves.removeAll(bin);
    }


    public PieceView getView(){
        return view;
    }

    public int getCol(){
        return square.getCol();
    }

    public int getRow(){
        return square.getRow();
    }

    public PieceColor getColor(){
        return color;
    }

    protected String getStringForColor(PieceColor col){
        return col == PieceColor.WHITE ? "white" : "black";
    }
    protected String getStringForType(PieceType type){
        switch(type) {
            case BISHOP:{
                return "Bishop";
            }
            case KING :{
                return "King";
            }
            case ROOK:{
                return "Rook";
            }
            case QUEEN :{
                return "Queen";
            }
            case KNIGHT :{
                return "Knight";
            }
            case PAWN :{
                return "Pawn";
            }
            default:
                return "Unknown";
        }
    }

    public static void selectPiece(Piece p){
        selectedPiece = p;
    }

    protected abstract HashSet<Square> calculateControlledSquares();

    protected boolean isValidSquare(int col, int row){
        return (col < 8 && col >= 0 && row < 8 && row >= 0);
    }

    /**
     * drops the Squares where the piece cannot move to because its ally-occupied
     * @param controlledSquares the Squares that the piece sees
     * @return filtered Squares
     */
    protected HashSet<Square> filterIllegalMovesFromControlledSquares(HashSet<Square> controlledSquares){
        controlledSquares.removeIf(s -> ! s.isEmpty() && s.getPieceOnThisSquare().isSameColorAs(this));
        return controlledSquares;
    }

    public HashSet<Move> calculatePossibleMoves(){

        HashSet<Square> controlledSquares = calculateControlledSquares();


        ChessBoard.board.addIllegalKingSquaresForOpponent(this,controlledSquares);

        HashSet<Square> SquaresWhereICanMove = filterIllegalMovesFromControlledSquares(controlledSquares);

        return convertSquaresToMoves(this,SquaresWhereICanMove);
    }


    public void updatePossibleMoves(){
        possibleMoves = calculatePossibleMoves();

    }

    public void revive(Square tile){
        moveTo(tile);
        mySide.addPiece(this);
    }



    public boolean canStepTo(Square s){
        for(Move move : possibleMoves){
            if(move.getTo() == s){
                return true;
            }
        }
        return false;

    }



    protected HashSet<Move> convertSquaresToMoves(Piece p,HashSet<Square> Squares){

        HashSet<Move> moves = new HashSet<>();
        for(Square t: Squares){
            moves.add(new Move(p,p.getSquare(),t));
        }
        return moves;
    }


    public void clearPossibleMoves(){
        possibleMoves.clear();
    }

    public void moveTo(Square newSquare){
        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;
    }


    public void beCaptured(){
        mySide.removePiece(this);
    }


    public static Piece getSelectedPiece(){
        return selectedPiece;
    }

    public HashSet<Move> getPossibleMoves(){
        return possibleMoves;
    }



    public PieceType getType(){
        return type;
    }




    @Override
    public String toString() {
        return String.format("%s %s",color,type);
    }
}
