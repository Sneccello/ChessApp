package BoardElements;

import Figures.Piece;
import Views.TileView;

public class Tile {

    private Piece pieceOnThisTile = null;


    private final int col;
    private final int row;
    static private final Character[] colChars = {'a','b','c','d','e','f','g','h'};
    TileView observer;



    Tile(Piece f, int col, int row){
        pieceOnThisTile = f;
        this.col = col;
        this.row = row;

        observer = new TileView(this);
    }




    public TileView getView(){
        return observer;
    }

    public Piece getFig(){
        return pieceOnThisTile;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    public boolean isEmpty(){
        return pieceOnThisTile == null;
    }


    public void removeFigure(){
        pieceOnThisTile = null;
    }




    public void addFigure(Piece f){
        pieceOnThisTile = f;

    }


    public void trySelectingFigureOnThisTile(){
        if(pieceOnThisTile != null && pieceOnThisTile.getColor() == ChessBoard.board.colorToMove()){
            Piece.selectFigure(pieceOnThisTile);
        }

    }

    public Piece getPieceOnThisTile(){
        return pieceOnThisTile;
    }

    @Override
    public String toString() {
        return colChars[col]+Integer.toString(row+1);
    }




}
