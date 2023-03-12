package views;

import views.panels.EvaluationPanel;

import java.awt.*;
import java.util.ArrayList;

public class SideView {


    private static EvaluationPanel sideInfoPanel;

    public ArrayList<PieceView> getPieceViews() {
        return pieceViews;
    }

    public static void setSideEvaluationPanel(EvaluationPanel panel) {
        sideInfoPanel=panel;
    }

    public Image getIcon(){
        return null; //TODO
    }
    private final ArrayList<PieceView> pieceViews = new ArrayList<>();


    public void addPieceView(PieceView pw){
        pieceViews.add(pw);
    }

    public void removePieceView(PieceView pw){
        pieceViews.remove(pw);
    }

    public void paint(Graphics g){
        for(PieceView pw : pieceViews){
            pw.paint(g);
        }
        sideInfoPanel.updateInfo(null);
    }

}
