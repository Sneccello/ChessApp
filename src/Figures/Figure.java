package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashSet;

abstract public class Figure {

    protected FigureTypes type;
    protected FigureColor color;
    protected FigureView view;
    protected HashSet<Tile> possibleMoves ;

    protected static Figure selectedFigure;

    protected Tile tile;

    Figure(FigureTypes type, FigureColor color, int posCol, int posRow){
        this.type = type;
        this.color = color;
        possibleMoves = new HashSet<>();

        String imageName = getStringForColor(color) + getStringForType(type)+".png";
        view = new FigureView(imageName, this);

        tile = ChessBoard.board.getTileAt(posCol,posRow);
        tile.moveHere(this);
        ChessBoard.board.addFigure(this);
    }

    public void setTile(Tile t){
        tile = t;
    }

    public Tile getTile(){
        return tile;
    }

    public boolean isTheSameColorAs(Figure f){
        return color == f.color;
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

    public abstract void calculatePossibleMoves();
    protected abstract void moveTo(int i, int j );

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
