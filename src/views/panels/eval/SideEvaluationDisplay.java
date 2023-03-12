package views.panels.eval;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import ai.evaluationaspects.Evaluable;
import boardelements.ChessBoard;
import boardelements.pieces.PieceColor;
import boardelements.Side;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SideEvaluationDisplay extends EvaluationView {

    List<Side> sides;
    public SideEvaluationDisplay(int WIDTH, int HEIGHT) {
        super(WIDTH, HEIGHT);

    }


    @Override
    public void draw(Graphics g, List<Evaluable> evaluables) {
        g.setColor(new Color(130,130,130,130));
        g.fillRect(0,0,WIDTH,HEIGHT);
        int textWidth = 200;
        int rowHeight = 20;
        sides = ChessBoard.board.getSides().values().stream().toList();
        if(sides.size() == 0){
            return;
        }
        double sumValue = 0;
        String desc = "Evaluation of the sides";
        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString(desc, (int) (WIDTH / 2 - desc.length() / 2 * 5), (int) (margin * 0.4));
        g.setFont(new Font("default", Font.PLAIN, 12));
        int drawnRows = 0;
        for (AbstractBaseEvaluationAspect aspectToDraw : sides.get(0).getEvaluationAspects()) {

            String aspectName = aspectToDraw.getName();
            if (aspectName == null) {
                continue;
            }
            g.setColor(aspectToDraw.getCurrentValue() < 0 || aspectToDraw.isPenalty()? new Color(150, 13, 13) : new Color(16, 150, 37));

            int textX = margin;
            int textY = margin + drawnRows * rowHeight + rowHeight / 2;
            g.drawString(aspectName, textX, textY);

            drawnRows++;
            sumValue += aspectToDraw.getCurrentValue();

            int drawnBarsInRow = 0;
            int rectHeight = rowHeight/sides.size();
            for (Side side : sides) {
                AbstractBaseEvaluationAspect aspectOfSide = side
                        .getEvaluationAspects()
                        .stream()
                        .filter(asp -> Objects.equals(asp.getName(), aspectName))
                        .toList()
                        .get(0);

                int filled = (int) (aspectOfSide.getCurrentValue() / aspectOfSide.getAdhocMax() * maxBarWidth);
                int rectY = textY - rowHeight / 2 + (drawnBarsInRow)*rectHeight;
                g.setColor(side.getColor() == PieceColor.WHITE? Color.WHITE: Color.BLACK);
                g.fillRect(textWidth, rectY, filled, rectHeight);
                g.drawString(Double.toString(aspectOfSide.getCurrentValue()) + '/' + aspectOfSide.getAdhocMax(), +maxBarWidth + textWidth + 20, rectY+rectHeight/2);
                drawnBarsInRow++;
            }
            drawnRows++;

        }

    }

}
