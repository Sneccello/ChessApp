package Views;

import BoardElements.Side;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoardView{

    TileView[]  tileViews;
    private final ArrayList<SideView> sideViews = new ArrayList<>();


    public void addSideView(SideView sw){
        sideViews.add(sw);
    }

    public void setTileViews(TileView[] tileViews){
        this.tileViews = tileViews;
    }


    public void paint(Graphics g){
        for(TileView tw : tileViews) {
            tw.paint(g);
        }
        for(SideView sw : sideViews){
            sw.paintSide(g);
        }
    }

    public void locateClick(int x, int y){
        for(TileView tw : tileViews){
            if(tw.clickIsOnMe(x,y)){
                tw.clicked();
                break;
            }
        }
    }


}
