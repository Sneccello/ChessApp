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
    EvaluationPanel pieceInfoPanel2;
    public ChessApp(){

        this.chessBoardPanel = new ChessBoardView();

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);

        promotionOptionsPanel = new PromotionOptionsPanel();
        this.add(promotionOptionsPanel, BorderLayout.SOUTH);

        evalViews = new JPanel();
        evalViews.setPreferredSize(new Dimension(200,800));
        this.add(evalViews, BorderLayout.WEST);


        pieceInfoPanel = new EvaluationPanel(EvaluationPanel.DisplayMode.PIECES, new Color(187,215,236));
        pieceInfoPanel2 = new EvaluationPanel(EvaluationPanel.DisplayMode.SIDES, new Color(183,240,224));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(pieceInfoPanel);
        infoPanel.add(pieceInfoPanel2);

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


        PieceView.setPieceEvaluationPanel(window.pieceInfoPanel);

        window.pieceInfoPanel.updateInfo(board.getSquareViews()[0].getObservedSquare().getPieceOnThisSquare());
        window.revalidate();

        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
