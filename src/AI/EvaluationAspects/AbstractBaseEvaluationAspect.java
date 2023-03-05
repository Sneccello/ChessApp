package AI.EvaluationAspects;

public abstract class AbstractBaseEvaluationAspect {

    protected double aspectCoefficient;

    public boolean isPenalty() {
        return isPenalty;
    }

    protected boolean isPenalty;

    public double getCurrentValue() {
        return currentValue;
    }


    protected double currentValue;
    public double getAdhocMax() {
        return adhocMax;
    }

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
        currentValue =  aspectCoefficient * calculateAspectValue();
        return currentValue;
    }

    public String getName() {
        return name;
    }
}
