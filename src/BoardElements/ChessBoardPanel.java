package BoardElements;

import Views.ChessBoardView;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {

    private final ChessBoardView boardView;

    int WIDTH = 800;
    int HEIGHT =  800;

    public ChessBoardPanel(ChessBoardView boardView){
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.boardView = boardView;
    }


    @Override
    public void paintComponent(Graphics g){
        boardView.paint(g);
    }

}
