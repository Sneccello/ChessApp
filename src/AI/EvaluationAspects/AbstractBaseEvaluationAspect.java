package AI.EvaluationAspects;

public abstract class AbstractBaseEvaluationAspect {

    protected double aspectCoefficient;
    protected boolean isPenalty;
    protected double adhocMax; //a maximum value for visualizing e.g.
    protected String name;
    public double getAspectCoefficient(){
        return aspectCoefficient;
    }

    public void setAspectCoefficient(int aspectCoefficient){
        this.aspectCoefficient = aspectCoefficient;
    }

    protected abstract int calculateAspectValue();

    public double evaluate(){
        return aspectCoefficient * calculateAspectValue();
    }



}
