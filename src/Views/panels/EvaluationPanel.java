package Views.panels;

import AI.EvaluationAspects.Evaluable;
import Views.panels.eval.EvaluationView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EvaluationPanel extends JPanel{



    private java.util.List<Evaluable> evaluables;

    private final EvaluationView display;

    public EvaluationPanel(EvaluationView display) {
        this.display = display;
        setPreferredSize(new Dimension(display.getWidth(),display.getHeight()));
    }

    public void updateInfo(Evaluable evaluable){

        if(evaluable != null){
            this.evaluables= List.of(evaluable);
        };
        repaint();

    }


//    private void setColors(Graphics g,AbstractBaseEvaluationAspect aspect,boolean colorAspects){
//        if(mode == DisplayMode.SIDES){
//            if(colorAspects){
//                if(sideToggleButton.getText().equals(PieceColor.BLACK.toString())){
//                    setBackground(new Color(105,105,105));
//                    g.setColor(Color.WHITE);
//                }
//                else{
//                    setBackground(new Color(240,240,240));
//                    g.setColor(Color.BLACK);
//                }
//            }
//            else{
//                g.setFont(new Font("default", Font.BOLD, 16));
//                g.setColor(Color.BLACK);
//            }
//        }
//        else{
//            if(colorAspects)
//                g.setColor(aspect.getCurrentValue()< 0 ? new Color(150,13,13) : new Color(16,150,37));
//            else{
//                g.setFont(new Font("default", Font.BOLD, 16));
//                g.setColor(Color.BLACK);
//            }
//        }
//
//    }

//    private void drawBars(Graphics g, int pushDown){
//        int margin = 50;
//        int textWidth = 250;
//        int maxBarWidth = 100;
//        int rowHeight = 30;
//        int drawnBars = 0;
//        double sumValue = 0;
//
//        String desc = "Showing: " + evaluable.getDescription() + " evaluation";
//        g.drawString(desc,(int)(WIDTH/2-desc.length()/2*5),(int)(pushDown+margin*0.3));
//
//
//
//        for (AbstractBaseEvaluationAspect aspect : evaluable.getEvaluationAspects()) {
//
//            if (aspect.getName() == null) {
//                continue;
//            }
//
//            setColors(g,aspect,true);
//            int textX = margin;
//            int textY = margin + drawnBars * rowHeight + rowHeight / 2 + pushDown;
//            g.drawString(aspect.getName(), textX, textY);
//
//            int filled = (int) (aspect.getCurrentValue() / aspect.getAdhocMax() * maxBarWidth);
//            g.fillRect(textWidth, textY - rowHeight / 2, filled, rowHeight);
//            g.drawString(Double.toString(aspect.getCurrentValue()) + '/' + Double.toString(aspect.getAdhocMax()), + maxBarWidth+textWidth+20,textY);
//
//            drawnBars++;
//            sumValue+=aspect.getCurrentValue();
//        }
//        drawnBars++;
//
//
//        String sumValueText = "Sum Value: " +sumValue;
//        setColors(g,null,false);//Color.BLACK,null,false);
//        g.drawString(sumValueText,WIDTH/2-50,margin + drawnBars * rowHeight + rowHeight / 2 + 60);
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        display.draw(g,evaluables);
    }

}
