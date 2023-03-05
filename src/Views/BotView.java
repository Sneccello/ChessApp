package Views;

import AI.ChessBot;

import javax.swing.*;
import java.awt.*;

public class BotView extends JPanel {

    private ChessBot bot;
    private final JTextArea  evalScoreLabel = new JTextArea();
    private final String botName;

    int boxHeight = 600;

    public BotView(ChessBot bot){
        this.setPreferredSize(new Dimension(70,boxHeight));
        this.add(evalScoreLabel, BorderLayout.SOUTH);
        this.bot = bot;
        botName = bot.getSide().getColor().toString();
        setVisible(true);

        this.setBorder(BorderFactory.createLineBorder(new Color(63,72,204)));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double evaluation = bot.getCurrentEvaluation();

        double maxAdvantage = 2000;
        evaluation = Math.min(evaluation,maxAdvantage);
        evaluation = Math.max(evaluation,-maxAdvantage);
        double blackRectHeight = (evaluation+maxAdvantage) / (2*maxAdvantage) * boxHeight;


        g.setColor(Color.BLACK);
        int margin = 50;
        g.fillRect(0,margin,70,(int)(blackRectHeight)+margin);
        g.setColor(Color.WHITE);
        g.fillRect(0,(int)(blackRectHeight)+margin,70,boxHeight+margin);


        evalScoreLabel.setText(botName + "\n" + "Eval:"+Double.toString(evaluation));
    }

    public void update(){
        repaint();
    }

}
