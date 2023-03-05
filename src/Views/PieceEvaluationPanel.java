package Views;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieceEvaluationPanel extends JPanel {

    private final PieceEvalBarPlot barPlots = new PieceEvalBarPlot();

    public PieceEvaluationPanel(){
        setPreferredSize(new Dimension(500,600));
        this.add(barPlots, BorderLayout.SOUTH);
    }

    public void updateInfo(SquareView sw){
        Piece piece = sw.observedSquare.getPieceOnThisSquare();
        if(piece != null){
            barPlots.setCurrentPiece(piece);
            invalidate();
        }
    }

}
