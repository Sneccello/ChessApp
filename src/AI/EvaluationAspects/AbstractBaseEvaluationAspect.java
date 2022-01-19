package AI.EvaluationAspects;

public abstract class AbstractBaseEvaluationAspect {

    protected double aspectCoefficient;

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
