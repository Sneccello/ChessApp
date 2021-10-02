package Figures;

import BoardElements.ChessBoard;
import BoardElements.Pin;
import BoardElements.Tile;
import Views.FigureView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

abstract public class Figure {

    protected FigureTypes type;
    protected FigureColor color;
    protected FigureView view;
    protected HashSet<Tile> possibleMoves ;
    protected ArrayList<Pin> pins = new ArrayList<>();


    protected static Figure selectedFigure;

    protected Tile tile;

    Figure(FigureTypes type, FigureColor color, int posCol, int posRow){
        this.type = type;
        this.color = color;
        possibleMoves = new HashSet<>();

        String imageName = getStringForColor(color) + getStringForType(type)+".png";
        view = new FigureView(imageName, this);

        tile = ChessBoard.board.getTileAt(posCol,posRow);
        tile.addFigure(this);

    }

    public void setTile(Tile t){
        tile = t;
    }

    public Tile getTile(){
        return tile;
    }

    public boolean isDifferentColorAs(Figure f){
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

    public void clearPinnerPieces(){
        pins.clear();
    }

    public void checkLegalMovesBeingPinned(){
        if(pins.isEmpty()){
            return;
        }
        if(pins.size() == 1){
            Iterator<Tile> pinIterator = pins.get(0).getTilesAvailable().iterator();
            HashSet<Tile> tilesInPinAxis = pins.get(0).getTilesAvailable();
            HashSet<Tile> mutualSet = new HashSet<>();
            while(pinIterator.hasNext()){
                Tile t = pinIterator.next();
                if(possibleMoves.contains(t)){
                    mutualSet.add(t);
                }
            }
            possibleMoves.clear();
            possibleMoves.addAll(mutualSet);
        }
        else{
            possibleMoves.clear();
        }

    }

    public FigureView getView(){
        return view;
    }

    public int getCol(){
        return tile.getCol();
    }

    public int getRow(){
        return tile.getRow();
    }

    public FigureColor getColor(){
        return color;
    }

    protected String getStringForColor(FigureColor col){
        return col == FigureColor.WHITE ? "white" : "black";
    }
    protected String getStringForType(FigureTypes type){
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

    public static void selectFigure(Figure f){
        selectedFigure = f;
    }

    protected abstract HashSet<Tile> calculatePossibleMoves();

    public void updatePossibleMoves(){
        possibleMoves = calculatePossibleMoves();
        ChessBoard.board.addIllegalKingTilesForOpponent(this, possibleMoves);
    }


    public void clearPossibleMoves(){
        possibleMoves.clear();
    }

    public void moveTo(Tile newTile){
        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;
    }




    public static Figure getSelectedFigure(){
        return selectedFigure;
    }

    public HashSet<Tile> getPossibleMoves(){
        return possibleMoves;
    }


    public void capture(){
        ChessBoard.board.removeFigure(this);
        tile.removeFigure();
    }

    public FigureTypes getType(){
        return type;
    }


}
