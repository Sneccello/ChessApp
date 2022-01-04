package Views;

import BoardElements.Side;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoardView{

    SquareView[]  SquareViews;
    private final ArrayList<SideView> sideViews = new ArrayList<>();


    public void addSideView(SideView sw){
        sideViews.add(sw);
    }

    public void setSquareViews(SquareView[] SquareViews){
        this.SquareViews = SquareViews;
    }


    public void paint(Graphics g){
        for(SquareView tw : SquareViews) {
            tw.paint(g);
        }
        for(SideView sw : sideViews){
            sw.paintSide(g);
        }
    }

    public void locateClick(int x, int y){
        for(SquareView tw : SquareViews){
            if(tw.clickIsOnMe(x,y)){
                tw.clicked();
                break;
            }
        }
    }


}
