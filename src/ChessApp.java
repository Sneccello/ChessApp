import ai.ChessBot;
import boardelements.ChessBoard;
import boardelements.Side;
import boardelements.pieces.*;
import views.*;
import views.panels.eval.BotEvalBar;
import views.panels.EvaluationPanel;
import views.panels.PromotionOptionsPanel;
import views.panels.eval.PieceEvaluationView;
import views.panels.eval.SideEvaluationDisplay;

import javax.swing.*;
import java.awt.*;

public class ChessApp extends JFrame{

    ChessBoardView chessBoardPanel;
    public final JPanel evalViews;
    JPanel promotionOptionsPanel;

    EvaluationPanel pieceInfoPanel;
    EvaluationPanel sideInfoPanel;
    public ChessApp(){

        this.chessBoardPanel = new ChessBoardView();

        ChessBoard.board.registerView(chessBoardPanel);
        this.add(chessBoardPanel, BorderLayout.CENTER);

        promotionOptionsPanel = new PromotionOptionsPanel();
        this.add(promotionOptionsPanel, BorderLayout.SOUTH);

        evalViews = new JPanel();
        evalViews.setPreferredSize(new Dimension(200,800));
        this.add(evalViews, BorderLayout.WEST);


//        pieceInfoPanel = new EvaluationPanel(EvaluationPanel.DisplayMode.PIECES, new Color(187,215,236));
//        pieceInfoPanel2 = new EvaluationPanel(EvaluationPanel.DisplayMode.SIDES, new Color(183,240,215,236));
        pieceInfoPanel = new EvaluationPanel(new PieceEvaluationView(500,400));
        sideInfoPanel = new EvaluationPanel(new SideEvaluationDisplay(500,400));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(pieceInfoPanel);
        infoPanel.add(sideInfoPanel);

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
        BotEvalBar botview = new BotEvalBar(bot);
        bot.registerView(botview);
        window.evalViews.add(botview);


        blackSide.setBot(bot);

        board.addSide(whiteSide);
        board.addSide(blackSide);


        PieceView.setPieceEvaluationPanel(window.pieceInfoPanel);
        SideView.setSideEvaluationPanel(window.sideInfoPanel);

        window.pieceInfoPanel.updateInfo(board.getSquareViews()[0].getObservedSquare().getPieceOnThisSquare());
        window.revalidate();

        ChessBoard.board.calculateMoves(); //calculate possible moves
        window.repaint();
        //TODO draw by repetition, endgame results and draw after 50moves without pawn moves or captures

    }

}
