package Views;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import AI.EvaluationAspects.Evaluable;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceColor;
import BoardElements.Side;

import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EvaluationPanel extends JPanel implements ItemListener {


    public enum DisplayMode {
        PIECES,
        SIDES
    }


    private Evaluable evaluable;
    private final int WIDTH = 500;
    private final int HEIGHT = 400;

    private JToggleButton sideToggleButton;
    private final DisplayMode mode;

    public EvaluationPanel(DisplayMode mode){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.mode = mode;

        if(mode==DisplayMode.SIDES){
            this.setLayout(new BorderLayout());
            JToggleButton btn = new JToggleButton();
            sideToggleButton=btn;
            btn.addItemListener(this);
            this.add(btn, BorderLayout.SOUTH);
            showSide(PieceColor.WHITE);
        }
    }


    public EvaluationPanel(DisplayMode mode, Color background) {
        this(mode);
        setBackground(background);
    }

    private void showSide(PieceColor color){
        sideToggleButton.setText(color.toString());
        updateInfo(ChessBoard.board.getSides().get(color));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(sideToggleButton.isSelected()) {
            showSide(PieceColor.BLACK);
        }
        else {
            showSide(PieceColor.WHITE);

        }
    }


    public void updateInfo(Evaluable evaluable){
        if(evaluable != null){
            this.evaluable=evaluable;
            repaint();
        }
    }


    private void setColors(Graphics g,AbstractBaseEvaluationAspect aspect,boolean colorAspects){
        if(mode == DisplayMode.SIDES){
            if(colorAspects){
                if(sideToggleButton.getText().equals(PieceColor.BLACK.toString())){
                    setBackground(new Color(105,105,105));
                    g.setColor(Color.WHITE);
                }
                else{
                    setBackground(new Color(240,240,240));
                    g.setColor(Color.BLACK);
                }
            }
            else{
                g.setFont(new Font("default", Font.BOLD, 16));
                g.setColor(Color.BLACK);
            }
        }
        else{
            if(colorAspects)
                g.setColor(aspect.getCurrentValue()< 0 ? new Color(150,13,13) : new Color(16,150,37));
            else{
                g.setFont(new Font("default", Font.BOLD, 16));
                g.setColor(Color.BLACK);
            }
        }

    }

    private void drawBars(Graphics g, int pushDown){
        int margin = 50;
        int textWidth = 250;
        int maxBarWidth = 100;
        int rowHeight = 30;
        int drawnBars = 0;
        double sumValue = 0;

        String desc = "Showing: " + evaluable.getDescription() + " evaluation";
        g.drawString(desc,(int)(WIDTH/2-desc.length()/2*5),(int)(pushDown+margin*0.3));

        for (AbstractBaseEvaluationAspect aspect : evaluable.getEvaluationAspects()) {

            if (aspect.getName() == null) {
                continue;
            }

            setColors(g,aspect,true);
            int textX = margin;
            int textY = margin + drawnBars * rowHeight + rowHeight / 2 + pushDown;
            g.drawString(aspect.getName(), textX, textY);

            int filled = (int) (aspect.getCurrentValue() / aspect.getAdhocMax() * maxBarWidth);
            g.fillRect(textWidth, textY - rowHeight / 2, filled, rowHeight);
            g.drawString(Double.toString(aspect.getCurrentValue()) + '/' + Double.toString(aspect.getAdhocMax()), + maxBarWidth+textWidth+20,textY);

            drawnBars++;
            sumValue+=aspect.getCurrentValue();
        }
        drawnBars++;


        String sumValueText = "Sum Value: " +sumValue;
        setColors(g,null,false);//Color.BLACK,null,false);
        g.drawString(sumValueText,WIDTH/2-50,margin + drawnBars * rowHeight + rowHeight / 2 + 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (evaluable == null) {
            return;
        }





        Image icon = evaluable.getIcon();
        int pushDown = icon == null? 0 : icon.getHeight(null);
        drawBars(g,pushDown);
        if(icon !=null){
            g.drawImage(icon, WIDTH/2-40, 0, null);
        }

    }

}
