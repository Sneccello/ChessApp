package Views;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.Evaluable;
import BoardElements.Pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class EvaluationPanel extends JPanel {

    private Evaluable evaluable;
    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    private boolean drawSelectedPieceImage;
    private boolean verticalLayout;
    public EvaluationPanel(boolean verticalLayout,boolean drawSelectedPieceImage){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        this.drawSelectedPieceImage = drawSelectedPieceImage;
        this.verticalLayout = verticalLayout;
    }

    public EvaluationPanel(boolean verticalLayout){
        this(verticalLayout,false);
    }

    public void updateInfo(Evaluable evaluable){
        if(evaluable != null){
            this.evaluable=evaluable;
            repaint();
        }
    }

    private void drawBars(Graphics g){
        int margin = 20;
        int textWidth = 225;
        int maxBarWidth = 100;
        int rowHeight = 40;
        int drawnBars = 0;
        double sumValue = 0;
        for (AbstractBaseEvaluationAspect aspect : evaluable.getEvaluationAspects()) {

            if (aspect.getName() == null) {
                continue;
            }

            g.setColor(aspect.getCurrentValue()< 0 ? new Color(150,13,13) : new Color(16,150,37));
            int textX = margin;
            int textY = margin + drawnBars * rowHeight + rowHeight / 2 + 100;
            g.drawString(aspect.getName(), textX, textY);

            int filled = (int) (aspect.getCurrentValue() / aspect.getAdhocMax() * maxBarWidth);
            g.fillRect(textWidth, textY - rowHeight / 2, filled, rowHeight);
            g.drawString(Double.toString(aspect.getCurrentValue()) + '/' + Double.toString(aspect.getAdhocMax()), + maxBarWidth+textWidth+20,textY);

            drawnBars++;
            sumValue+=aspect.getCurrentValue();
        }
        g.setFont(new Font("default", Font.BOLD, 16));

        String sumValueText = "Piece value: " +sumValue;
        g.setColor(Color.BLACK);
        g.drawString(sumValueText,WIDTH/2-30,margin + drawnBars * rowHeight + rowHeight / 2 + 130);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (evaluable == null) {
            return;
        }


        drawBars(g);


        if(drawSelectedPieceImage){
            PieceView pieceView = PieceView.getDisplayedInEvaluation();
            if(pieceView != null) {
                Image selectedPieceImage = pieceView.getImage();
                g.drawImage(selectedPieceImage, WIDTH/2-40, 0, null);
            }

        }

    }

}
