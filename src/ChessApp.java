import BoardElements.ChessBoard;
import BoardElements.ChessBoardPanel;
import Figures.*;
import Views.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessApp extends JFrame implements MouseListener {

    JPanel chessFieldPanel;
    JPanel infoPanel;

    public ChessApp(){
        this.setPreferredSize(new Dimension(800+30,800+50));

        this.setVisible(true);
        this.chessFieldPanel = new ChessBoardPanel(ChessBoard.board.getView());
        this.add(chessFieldPanel, BorderLayout.CENTER);

        this.setLayout(null);

        addMouseListener(this);

        this.pack();
    }



    public static void main(String[] args) {

        JFrame window = new ChessApp();
        Rook wr1 = new Rook(FigureTypes.ROOK, FigureColor.WHITE,0,0);
        Rook wr2 = new Rook(FigureTypes.ROOK, FigureColor.WHITE,7,0);
        Knight wk1 = new Knight(FigureTypes.KNIGHT, FigureColor.WHITE,1,0);
        Knight wk2 = new Knight(FigureTypes.KNIGHT, FigureColor.WHITE,6,0);
        Bishop wb1 = new Bishop(FigureTypes.BISHOP, FigureColor.WHITE,2,0);
        Bishop wb2 = new Bishop(FigureTypes.BISHOP, FigureColor.WHITE,5,0);
        Queen wq = new Queen(FigureTypes.QUEEN, FigureColor.WHITE,3,0);
        King wk = new King(FigureTypes.KING,FigureColor.WHITE,4,0);

        Rook br1 = new Rook(FigureTypes.ROOK, FigureColor.BLACK,0,7);
        Rook br2 = new Rook(FigureTypes.ROOK, FigureColor.BLACK,7,7);
        Knight bk1 = new Knight(FigureTypes.KNIGHT, FigureColor.BLACK,1,7);
        Knight bk2 = new Knight(FigureTypes.KNIGHT, FigureColor.BLACK,6,7);
        Bishop bb1 = new Bishop(FigureTypes.BISHOP, FigureColor.BLACK,2,7);
        Bishop bb2 = new Bishop(FigureTypes.BISHOP, FigureColor.BLACK,5,7);
        Queen bq = new Queen(FigureTypes.QUEEN, FigureColor.BLACK,3,7);
        King bk = new King(FigureTypes.KING,FigureColor.BLACK,4,7);

        ChessBoard.board.moveWasMade();
        window.repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        final int screenPanelXStart = 8; //TODO top left corner of screen if not at (0,0) because of the window bar
        final int screenPanelYStart = 31;//TODO will need something better


        int x = e.getX() - screenPanelXStart;
        int y = e.getY() - screenPanelYStart;



        ChessBoard.board.getView().locateClick(x,y);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
