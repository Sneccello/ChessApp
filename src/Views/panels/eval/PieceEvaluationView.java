package Views.panels.eval;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.Evaluable;

import java.awt.*;
import java.util.List;

public class PieceEvaluationView extends EvaluationView {

    public PieceEvaluationView(int WIDTH, int HEIGHT){
        super(WIDTH,HEIGHT);

    }

    private int drawIcon(Graphics g,Evaluable evaluable){
        Image icon = evaluable.getIcon();
        int imH = icon == null? 0 : icon.getHeight(null);
        if(icon !=null){
            g.drawImage(icon, WIDTH/2-40, 0, null);
        }
        return imH;
    }

    public void draw(Graphics g, List<Evaluable> evaluables){
        g.setColor(new Color(187,215,236));
        g.fillRect(0,0,WIDTH,HEIGHT);

        if(evaluables== null){
            return;
        }

        assert evaluables.size() == 1;
        Evaluable evaluable = evaluables.get(0);
        int iconHeight = drawIcon(g, evaluable);
        double sumValue = 0;

        String desc = "Showing: " + evaluable.getDescription() + " evaluation";
        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString(desc,(int)(WIDTH/2-desc.length()/2*8),(int)(iconHeight+margin*0.3));
        g.setFont(new Font("default", Font.PLAIN, 12));
        int drawnRows = 0;
        for (AbstractBaseEvaluationAspect aspect : evaluable.getEvaluationAspects()) {

            if (aspect.getName() == null) {
                continue;
            }
            g.setColor(aspect.getCurrentValue()< 0 || aspect.isPenalty() ? new Color(150,13,13) : new Color(16,150,37));

            int textX = margin;
            int textY = margin + drawnRows * rowHeight + rowHeight / 2 + iconHeight;
            g.drawString(aspect.getName(), textX, textY);

            int filled = (int) (aspect.getCurrentValue() / aspect.getAdhocMax() * maxBarWidth);
            g.fillRect(textWidth, textY - rowHeight / 2, filled, rowHeight);
            g.drawString(Double.toString(aspect.getCurrentValue()) + '/' + aspect.getAdhocMax(), + maxBarWidth+textWidth+20,textY);

            drawnRows++;
            sumValue+=aspect.getCurrentValue();
        }
        drawnRows++;


        String sumValueText = "Sum Value: " +sumValue;
        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString(sumValueText,WIDTH/2-50,margin + drawnRows * rowHeight + rowHeight / 2 + 60);
    }

}
