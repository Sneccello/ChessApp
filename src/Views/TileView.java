package Views;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Tile;
import Figures.Figure;

import java.awt.*;



public class TileView {

    Tile observedTile;
    public static final int TILE_SIZE = 80;
    private final Color color;
    private static final Color selectedColor = Color.CYAN;
    private static final Color darkColor = Color.ORANGE;
    private static final Color lightColor = Color.WHITE;



    int x;
    int y;

    public TileView(Tile t){
        observedTile = t;


        color = (t.getCol()+t.getRow()) % 2 == 0? darkColor : lightColor;

        int boardHeight = TILE_SIZE * 8; //

        x = observedTile.getCol()* TILE_SIZE;
        y = boardHeight - TILE_SIZE - observedTile.getRow()* TILE_SIZE; //a1 should be in the bottom left corner

    }


    public void paint(Graphics g){

        if(Figure.getSelectedFigure()!= null &&  Figure.getSelectedFigure().getPossibleMoves().contains(observedTile)){
            g.setColor(selectedColor);
        }
        else {
            g.setColor(color);
        }
        g.fillRect(x,y,TILE_SIZE,TILE_SIZE);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void clicked() {
        if (Figure.getSelectedFigure() != null) { //move to an empty tile or capture something
            if(Figure.getSelectedFigure().getPossibleMoves().contains(observedTile)) {

                Move move = new Move(Figure.getSelectedFigure(), Figure.getSelectedFigure().getTile(), observedTile);

                Figure.getSelectedFigure().moveTo(observedTile);

                ChessBoard.board.moveWasMade(move);
            }
            Figure.selectFigure(null); //deselect

        }else {//select figure
            observedTile.selectFigureOnThisTile();

        }
    }


    public boolean clickIsOnMe(int x, int y){
        return x > getX() && x< getX() + TileView.TILE_SIZE && y > getY() && y < getY()+TileView.TILE_SIZE;
    }


}
