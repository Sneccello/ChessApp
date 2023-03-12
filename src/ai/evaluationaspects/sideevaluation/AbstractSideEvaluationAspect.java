package ai.evaluationaspects.sideevaluation;

import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import boardelements.Side;

public abstract class AbstractSideEvaluationAspect extends AbstractBaseEvaluationAspect {

    protected Side side;

    protected abstract int calculateAspectValue();




}
