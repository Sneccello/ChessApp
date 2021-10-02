package BoardElements;

import Figures.Figure;

public class Move {

    private final Tile to;
    private final Tile from;
    private final Figure f;

    public Move(Figure f, Tile from, Tile to){
        this.to = to;
        this.from = from;
        this.f = f;
    }


    public Tile getTo() {
        return to;
    }

    public Tile getFrom() {
        return from;
    }

    public Figure getF() {
        return f;
    }


}
