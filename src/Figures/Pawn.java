package Figures;

import Views.FigureView;

public class Pawn extends Figure{
    Pawn( FigureTypes type, FigureColor figCol,int posCol, int posRow) {
        super(type, figCol,posCol,posRow);

    }

    @Override
    public void calculatePossibleMoves() {

    }

    @Override
    protected void moveTo(int i, int j) {

    }
}
