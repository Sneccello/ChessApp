import AI.ChessBot;
import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Pieces.*;
import Views.BotView;
import Views.ChessBoardView;
import Views.PromotionOptionsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChessApp extends JFrame{

    ChessBoardView chessBoardPanel;
    public final JPanel evalViews;
    JPanel promotionOptionsPanel;

    public ChessApp(){
        this.setPreferredSize(new Dimension(1000,800));

//        this.setVisible(true);
        this.chessBoardPanel = new ChessBoardView();

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);


        promotionOptionsPanel = new PromotionOptionsPanel();
        this.add(promotionOptionsPanel, BorderLayout.SOUTH);

        evalViews = new JPanel(new FlowLayout());
        evalViews.setPreferredSize(new Dimension(200,800));
        this.add(evalViews, BorderLayout.EAST);

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);


        setVisible(true);

        this.pack();
    }



    public static void main(String[] args) {


        ChessBoard board = ChessBoard.board;

        //add the 2 kings so that the rook can have king pointers for castling
        ChessApp window = new ChessApp();

        //TODO should setup nicer
        Side whiteSide = new Side(PieceColor.WHITE);
        Side blackSide = new Side(PieceColor.BLACK);


        whiteSide.setOpponent(blackSide);
        blackSide.setOpponent(whiteSide);


        ChessBot bot = new ChessBot(blackSide);
        BotView botview = new BotView(bot);
        bot.registerView(botview);
        window.evalViews.add(botview);
        window.revalidate();

        blackSide.setBot(bot);

        board.addSide(whiteSide);
        board.addSide(blackSide);









        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
