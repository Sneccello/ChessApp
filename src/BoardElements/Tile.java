package BoardElements;

import Figures.Figure;
import Figures.FigureColor;
import Views.TileView;

import java.util.HashSet;

public class Tile {

    private Figure figureOnThisTile = null;


    private final int col;
    private final int row;
    static private final Character[] colChars = {'a','b','c','d','e','f','g','h'};
    TileView observer;



    Tile(Figure f, int col,int row){
        figureOnThisTile = f;
        this.col = col;
        this.row = row;

        observer = new TileView(this);
    }




    public TileView getView(){
        return observer;
    }

    public Figure getFig(){
        return figureOnThisTile;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    public boolean isEmpty(){
        return figureOnThisTile == null;
    }


    public void removeFigure(){
        figureOnThisTile = null;
    }




    public void addFigure(Figure f){
        if(!isEmpty()){
            figureOnThisTile.capture();
        }
        figureOnThisTile = f;

    }


    public void selectFigureOnThisTile(){
        if(figureOnThisTile != null){
            Figure.selectFigure(figureOnThisTile);
        }

    }

    public Figure getFigureOnThisTile(){
        return figureOnThisTile;
    }

    @Override
    public String toString() {
        return colChars[col]+Integer.toString(row+1);
    }
}