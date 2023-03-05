import AI.ChessBot;
import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Pieces.*;
import Views.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChessApp extends JFrame{

    ChessBoardView chessBoardPanel;
    public final JPanel evalViews;
    JPanel promotionOptionsPanel;

    PieceEvaluationPanel infoPanel;
    public ChessApp(){
        this.setPreferredSize(new Dimension(1500,800));

//        this.setVisible(true);
        this.chessBoardPanel = new ChessBoardView();

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);


        promotionOptionsPanel = new PromotionOptionsPanel();
        this.add(promotionOptionsPanel, BorderLayout.SOUTH);

        evalViews = new JPanel(new FlowLayout());
        evalViews.setPreferredSize(new Dimension(200,800));
        this.add(evalViews, BorderLayout.WEST);

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);



        infoPanel = new PieceEvaluationPanel();
        this.add(infoPanel, BorderLayout.EAST);

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


        blackSide.setBot(bot);

        board.addSide(whiteSide);
        board.addSide(blackSide);


        PieceView.setPieceEvaluationPanel(window.infoPanel);
        window.infoPanel.updateInfo(board.getSquareViews()[0]);
        window.revalidate();

        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
