package Views;

import BoardElements.ChessBoard;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoardView{

    TileView[]  tileViews;
    private final ArrayList<FigureView> figViews = new ArrayList<>();


    public ChessBoardView(){

    }

    public void setTileViews(TileView[] tileViews){
        this.tileViews = tileViews;
    }


    public void addFigView(FigureView fw){
        figViews.add(fw);
    }

    public void paint(Graphics g){
        for(TileView tw : tileViews) {
            tw.paint(g);
        }
        for(FigureView fw : figViews){
            fw.paint(g);
        }
    }

    public void locateClick(int x, int y){
        for(TileView tw : tileViews){
            if(x > tw.getX() && x< tw.getX() + TileView.TILE_SIZE && y>tw.getY() && y < tw.getY()+TileView.TILE_SIZE){
                tw.clicked();
            }
        }
    }

    public void removeFigView(FigureView fw){
        figViews.remove(fw);
    }


}
