package Views;

import java.awt.*;
import java.util.ArrayList;

public class SideView {

    private final ArrayList<PieceView> pieceViews = new ArrayList<>();


    public void addPieceView(PieceView pw){
        pieceViews.add(pw);
    }

    public void removePieceView(PieceView pw){
        pieceViews.remove(pw);
    }

    public void paintSide(Graphics g){
        for(PieceView pw : pieceViews){
            pw.paint(g);
        }
    }

}
