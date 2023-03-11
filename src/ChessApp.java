import AI.ChessBot;
import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Pieces.*;
import Views.*;

import javax.swing.*;
import java.awt.*;

public class ChessApp extends JFrame{

    ChessBoardView chessBoardPanel;
    public final JPanel evalViews;
    JPanel promotionOptionsPanel;

    EvaluationPanel pieceInfoPanel;
    public ChessApp(){

        this.chessBoardPanel = new ChessBoardView();

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);

        promotionOptionsPanel = new PromotionOptionsPanel();
        this.add(promotionOptionsPanel, BorderLayout.SOUTH);

        evalViews = new JPanel();
        evalViews.setPreferredSize(new Dimension(200,800));
        this.add(evalViews, BorderLayout.WEST);


        pieceInfoPanel = new EvaluationPanel(true,true);
        this.add(pieceInfoPanel, BorderLayout.EAST);

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


        PieceView.setPieceEvaluationPanel(window.pieceInfoPanel);

        window.pieceInfoPanel.updateInfo(board.getSquareViews()[0].getObservedSquare().getPieceOnThisSquare());
        PieceView.setDisplayedInEvaluation(board.getSquareViews()[0].getObservedSquare().getPieceOnThisSquare().getView());
        window.revalidate();

        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
