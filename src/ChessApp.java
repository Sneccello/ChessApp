import BoardElements.ChessBoard;
import Views.ChessBoardPanel;
import BoardElements.Side;
import BoardElements.Pieces.*;

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


        //add the 2 kings so that the rook can have king pointers for castling


        //TODO should setup nicer
        Side whiteSide = new Side(PieceColor.WHITE);
        Side blackSide = new Side(PieceColor.BLACK);

        whiteSide.setOpponent(blackSide);
        blackSide.setOpponent(whiteSide);

        board.addSide(whiteSide);
        board.addSide(blackSide);







        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
