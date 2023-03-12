package ai.evaluationaspects;

import boardelements.pieces.PieceColor;

import java.awt.*;
import java.util.List;

public interface Evaluable {
    public List<AbstractBaseEvaluationAspect> getEvaluationAspects();

    public PieceColor getColor();
    public Image getIcon();

    public String getDescription();
}
