package AI;

import BoardElements.Side;
import Figures.Piece;
import Figures.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChessBot {
    private final Side side;

    public ChessBot(Side s){
        this.side = s;


    }



    public double evaluateBoard(){
        ArrayList<Piece> pieces = side.getRegularPieces();

        double relativeValueSum = 0;

        for(Piece p : pieces){
            relativeValueSum += p.getRelativeValue();
        }

        int pawnIslands = side.countPawnIslands();
        double kingSafety = side.evaluateKingSafety();
        return relativeValueSum - pawnIslands/4.0 + kingSafety;


    }


}
