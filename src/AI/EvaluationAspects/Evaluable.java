package AI.EvaluationAspects;

import java.awt.*;
import java.util.List;

public interface Evaluable {
    public List<AbstractBaseEvaluationAspect> getEvaluationAspects();

    public Image getIcon();

    public String getDescription();
}
