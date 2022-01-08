package ChessAbstracts.Moves;

import BoardElements.Square;
import BoardElements.Pieces.Pawn;

public class Lunge extends Move{


    public Lunge(Pawn pawn, Square from, Square to) {
        super(pawn, from, to);
    }

    @Override
    public void undo(){
        super.undo();
        ( (Pawn)piece ).setLeftStartingSquare(false);
    }

}
