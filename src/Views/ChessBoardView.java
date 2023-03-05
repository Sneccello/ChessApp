package Views;

import BoardElements.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ChessBoardView extends JPanel implements MouseListener {

//    private final ChessBoardView boardView;

    int WIDTH = 800;
    int HEIGHT =  800;
//    SquareView[] squareViews;
    private final ArrayList<SideView> sideViews = new ArrayList<>();

    public ChessBoardView(){
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        this.setSize(new Dimension(WIDTH,HEIGHT));
//        this.boardView = boardView;
        this.addMouseListener(this);

    }




    public void addSideView(SideView sw){
        sideViews.add(sw);
    }

    public void refresh(){
        this.invalidate();
    }

    public void paint(Graphics g){
        for(SquareView tw : ChessBoard.board.getSquareViews()) {
            tw.paint(g);
        }
        for(SideView sw : sideViews){
            sw.paint(g);
        }
    }

    public SquareView locateSquare(int x, int y){
        for(SquareView tw : ChessBoard.board.getSquareViews()){
            if(tw.clickIsOnMe(x,y)){
                return tw;
            }
        }
        return null;
    }

    public void locateClick(int x, int y){
        for(SquareView tw : ChessBoard.board.getSquareViews()){
            if(tw.clickIsOnMe(x,y)){
                tw.clicked();
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        try{
            this.paint(g);
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
            SquareView sw = locateSquare(e.getX(),e.getY());
            if(sw != null){
                sw.clicked();
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON3){
            SquareView sw = locateSquare(e.getX(),e.getY());
            if(sw != null){
                PieceView.getEvaluationPanel().updateInfo(sw);
            }

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
