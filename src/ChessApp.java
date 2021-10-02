import BoardElements.ChessBoard;
import BoardElements.ChessBoardPanel;
import Figures.*;
import Views.ChessBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        ChessBoard.board.addKing(new King(FigureColor.BLACK,4,7));
        ChessBoard.board.addKing(new King(FigureColor.WHITE,4,0));


        for(int row = 0; row <= 7; row+= 7) {

            FigureColor figCol = (row == 0 ? FigureColor.WHITE : FigureColor.BLACK);



            Rook r1 = new Rook(figCol,0,row);
            Knight k1 = new Knight(figCol,1,row);
            Bishop b1 = new Bishop(figCol,2,row);
            Queen q = new Queen(figCol,3,row);
            Bishop b2 = new Bishop(figCol,5,row);
            Knight k2 = new Knight(figCol,6,row);
            Rook r2 = new Rook(figCol,7,row);

            board.addFigure(r1);
            board.addFigure(k1);
            board.addFigure(b1);
            board.addFigure(q);
            board.addFigure(b2);
            board.addFigure(k2);
            board.addFigure(r2);


            int pawnRow = (figCol == FigureColor.WHITE ? 1 : 6);
            for(int i = 0; i < 8; i++){
                Pawn p = new Pawn(figCol,i, pawnRow);
                board.addFigure(p);
            }

        }


        ChessBoard.board.moveWasMade(); //calculate possible moves
        window.repaint();

    }

}
