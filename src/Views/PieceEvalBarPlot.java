package Views;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.Evaluable;
import BoardElements.Pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class PieceEvalBarPlot extends JPanel {

    private Evaluable evaluable;

    public void setCurrentPiece(Evaluable evaluable) {
        this.evaluable = evaluable;
    }



    public PieceEvalBarPlot() {
        setPreferredSize(new Dimension(500,600));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (evaluable == null) {
            return;
        }

        int margin = 20;
        int textWidth = 200;
        int maxBarWidth = 100;
        int rowHeight = 40;
        int drawnBars = 0;
        for (AbstractBaseEvaluationAspect aspect : evaluable.getEvaluationAspects()) {

            if (aspect.getName() == null) {
                continue;
            }

            g.setColor(aspect.isPenalty() ? Color.RED : Color.GREEN);
            int textX = margin;
            int textY = margin + drawnBars * rowHeight + rowHeight / 2;
            g.drawString(aspect.getName(), textX, textY);

            int filled = (int) (aspect.getCurrentValue() / aspect.getAdhocMax()) * maxBarWidth;
            g.fillRect(textWidth, textY - rowHeight / 2, filled, rowHeight);
//            g.setColor(new Color(200,200,200));
//            g.fillRect(textWidth,textY-rowHeight/2,maxBarWidth,rowHeight);

            drawnBars++;
        }


    }
}
