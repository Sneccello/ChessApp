package Views;

import BoardElements.ChessBoard;
import Views.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ConcurrentModificationException;

public class ChessBoardPanel extends JPanel implements MouseListener {

    private final ChessBoardView boardView;

    int WIDTH = 800;
    int HEIGHT =  800;

    public ChessBoardPanel(ChessBoardView boardView){
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        this.setSize(new Dimension(WIDTH,HEIGHT));
        this.boardView = boardView;
        this.addMouseListener(this);

    }


    @Override
    public void paintComponent(Graphics g){
        try{
            boardView.paint(g);
        }
        catch(ConcurrentModificationException ex) {//TODO
            System.err.println("ConcurrentModificationException: " + ex.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1){
            boardView.locateClick(e.getX(),e.getY());

        }
        else if(e.getButton() == MouseEvent.BUTTON3){
            ChessBoard.board.undoLastMove();

        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
