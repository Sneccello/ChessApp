package AI.EvaluationAspects;

import BoardElements.Pieces.PieceColor;

import java.awt.*;
import java.util.List;

public interface Evaluable {
    public List<AbstractBaseEvaluationAspect> getEvaluationAspects();

    public PieceColor getColor();
    public Image getIcon();

    public String getDescription();
}
