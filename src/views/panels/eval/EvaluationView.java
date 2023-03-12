package views.panels.eval;

import ai.evaluationaspects.Evaluable;


import java.awt.*;
import java.util.List;

public abstract class EvaluationView {

    protected final int WIDTH;
    protected final int HEIGHT;

    protected int margin = 50;
    protected int textWidth = 250;
    protected int maxBarWidth = 100;
    protected int rowHeight = 30;



    public EvaluationView(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public abstract void draw(Graphics g, List<Evaluable> evaluable);
    public int getHeight(){
        return HEIGHT;
    }
    public int getWidth(){
        return WIDTH;
    }



}
