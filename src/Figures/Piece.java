package Figures;

import BoardElements.*;
import Views.PieceView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

abstract public class Piece {

    protected PieceType type;
    protected PieceColor color;
    protected PieceView view;
    protected HashSet<Move> possibleMoves ;
    protected ArrayList<Pin> pins = new ArrayList<>();


    protected static Piece selectedPiece;

    protected Tile tile;
    Side mySide;

    Piece(PieceType type, PieceColor color, int posCol, int posRow,Side side){
        this.type = type;
        this.color = color;
        possibleMoves = new HashSet<>();
        mySide = side;
        String imageName = getStringForColor(color) + getStringForType(type)+".png";
        view = new PieceView(imageName, this);

        tile = ChessBoard.board.getTileAt(posCol,posRow);
        tile.addFigure(this);

    }

    public void setTile(Tile t){
        tile = t;
    }

    public Tile getTile(){
        return tile;
    }

    public boolean isDifferentColorAs(Piece f){
        if(f == null){
            return false;
        }
        return color != f.color;
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

    public void checkLegalMovesBeingPinned(){
        if(pins.isEmpty()){
            return;
        }
        if(pins.size() == 1){
            Iterator<Move> tilesAvailableInPinIterator = pins.get(0).getMovesAvailable().iterator();
            HashSet<Move> mutualSet = new HashSet<>();//tiles that are legal in respect of the piece own moves and what the pin allows
            while(tilesAvailableInPinIterator.hasNext()){
                Move m = tilesAvailableInPinIterator.next();

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

    public void limitMovesToEndCheck(HashSet<Tile> targetTilesToEndCheck){
        HashSet<Move> bin = new HashSet<>(); //avoid concurrent modification

        for(Move move : possibleMoves){//searching if the piece has a move which's target tile can end the check. The other moves are illegal
            if( ! targetTilesToEndCheck.contains(move.getTo())){
                bin.add(move);
            }
        }
        possibleMoves.removeAll(bin);
    }


    public PieceView getView(){
        return view;
    }

    public int getCol(){
        return tile.getCol();
    }

    public int getRow(){
        return tile.getRow();
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

    public static void selectFigure(Piece f){
        selectedPiece = f;
    }

    protected abstract HashSet<Move> calculatePossibleMoves();

    public void updatePossibleMoves(){
        possibleMoves = calculatePossibleMoves();

        for(Move m  : possibleMoves){
            ChessBoard.board.addIllegalKingTileForOpponent(this, m.getTo());
        }
    }


    public boolean canStepTo(Tile t){
        for(Move move : possibleMoves){
            if(move.getTo() == t){
                return true;
            }
        }
        return false;

    }


    public boolean isTheEnemyKingFor(Piece p){
        return p.isDifferentColorAs(this) && type == PieceType.KING;
    }


    protected HashSet<Move> convertTilesToMoves(Piece p,HashSet<Tile> tiles){

        HashSet<Move> moves = new HashSet<>();
        for(Tile t: tiles){
            moves.add(new Move(p,p.getTile(),t));
        }
        return moves;
    }


    public void clearPossibleMoves(){
        possibleMoves.clear();
    }

    public void moveTo(Tile newTile){
        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;
    }




    public static Piece getSelectedPiece(){
        return selectedPiece;
    }

    public HashSet<Move> getPossibleMoves(){
        return possibleMoves;
    }


    public void beCaptured(){
        mySide.removePiece(this);
    }

    public PieceType getType(){
        return type;
    }


    @Override
    public String toString() {
        return String.format("%s %s",color,type);
    }
}
