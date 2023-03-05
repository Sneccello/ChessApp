package ChessAbstracts;

import java.util.Objects;

public class BinaryFlag {

    private boolean value;
    private boolean startingValue;

    public BinaryFlag(boolean staringValue){
        this.value = staringValue;
        this.startingValue = staringValue;
    }

    public void reset(){
        value = startingValue;
    }

    public boolean value(){
        return value;
    }

    public void setValue(boolean b){
        value = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryFlag that = (BinaryFlag) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
