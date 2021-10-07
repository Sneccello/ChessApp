import BoardElements.ChessBoard;
import BoardElements.ChessBoardPanel;
import BoardElements.Side;
import Figures.*;

import javax.swing.*;
import java.awt.*;

public class ChessApp extends JFrame{

    JPanel chessFieldPanel;
    JPanel infoPanel;

    public ChessApp(){
        this.setPreferredSize(new Dimension(800+30,800+50));

        this.setVisible(true);
        this.chessFieldPanel = new ChessBoardPanel(ChessBoard.board.getView());
        this.add(chessFieldPanel, BorderLayout.CENTER);

        this.setLayout(null);

        this.pack();
    }



    public static void main(String[] args) {


        ChessBoard board = ChessBoard.board;
        JFrame window = new ChessApp();


        //add the 2 kings so that the rook can have king pointers


        Side whiteSide = new Side(PieceColor.WHITE);
        Side blackSide = new Side(PieceColor.BLACK);
        board.addSide(whiteSide);
        board.addSide(blackSide);




        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();

    }

}
