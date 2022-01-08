package BoardElements.Pieces;

import BoardElements.*;
import ChessAbstracts.Check;
import ChessAbstracts.Pin;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class SliderPiece extends Piece {

    public SliderPiece(PieceType PiececType, PieceColor PiececCol, int posCol, int posRow, Side side) {
        super(PiececType, PiececCol,posCol,posRow,side);
    }



    protected HashSet<Square> tryPinningPieceInDir(Square toBePinnedSquare, int dx, int dy){

        ArrayList<Square> SquaresToTheNextPiece = discoverDirection(toBePinnedSquare,dx,dy);

        if(SquaresToTheNextPiece.isEmpty()){
            return null;
        }
        Square last = SquaresToTheNextPiece.get(SquaresToTheNextPiece.size()-1);

        if( isTheEnemyKingForMe(this,last.getPieceOnThisSquare())){// if enemy king found then a pin is sucessful
            SquaresToTheNextPiece.remove(last); //remove the king's Square as a possible move
            return new HashSet<>(SquaresToTheNextPiece);
        }
        return null; //could not pin in this direction
    }




    protected HashSet<Square> getControlledSquaresInDir(Square currentSquare, int dx, int dy){

        ArrayList<Square> controlledSquaresInDir = discoverDirection(currentSquare,dx,dy);

        if( ! controlledSquaresInDir.isEmpty()){

            Square lastFoundSquare = controlledSquaresInDir.get(controlledSquaresInDir.size() - 1 );

            if( lastFoundSquare.isEmpty() || lastFoundSquare.getPieceOnThisSquare().isSameColorAs(this)){
                return new HashSet<>(controlledSquaresInDir);
            }


            //if an enemy piece that is not the king is found then let's try to pin it to the king
            if(lastFoundSquare.getPieceOnThisSquare().isDifferentColorAs(this) && lastFoundSquare.getPieceOnThisSquare().type != PieceType.KING){

                HashSet<Square> SquaresAvailableForPinnedPiece = tryPinningPieceInDir(lastFoundSquare,dx,dy); //and we will try to pin is to the enemy king

                if(SquaresAvailableForPinnedPiece != null){ //=successful pin
                    Piece pinnedPiece = lastFoundSquare.getPieceOnThisSquare();

                    SquaresAvailableForPinnedPiece.addAll(controlledSquaresInDir); //we add the Squares between the pinned piece and the current pinner piece as available Squares for the pinned piece
                    SquaresAvailableForPinnedPiece.add(square); //we add ourselves as possible Squares for the pinned piece. It can capture us, if it can move towards us

                    Pin p = new Pin(convertSquaresToMoves(pinnedPiece, SquaresAvailableForPinnedPiece), this);
                    pinnedPiece.addPin(p);
                }
            }

            //if the enemy king is found then look behind him for additional controlled Squares
            else if(isTheEnemyKingForMe(this,lastFoundSquare.getPieceOnThisSquare())){
                controlledSquaresInDir.remove(lastFoundSquare); //remove the king's Square as a possible blocking Square
                King enemyKing = (King) lastFoundSquare.getPieceOnThisSquare();

                enemyKing.addCheck(new Check(this,new HashSet<>(controlledSquaresInDir)));


                ArrayList<Square> SquaresBehindKing = discoverDirection(lastFoundSquare,dx,dy);
                controlledSquaresInDir.addAll(SquaresBehindKing);

            }
        }
        return new HashSet<>(controlledSquaresInDir);
    }


    /**
     * goes out in a direction and returns the found Squares until it thits another piece or goes out of the board.
     * If an occupied Square is found in the direction that is also put in the returned set
     * @param startingSquare current position of the piece
     * @param dx x increment for the discovery
     * @param dy y increment for the discovery
     * @return the discovered set of Squares
     */
    public ArrayList<Square> discoverDirection(Square startingSquare, int dx, int dy) {

        int currentCol = startingSquare.getCol();
        int currentRow = startingSquare.getRow();

        ArrayList<Square> discoveredSquares = new ArrayList<>();

        while (true) {
            currentCol += dx;
            currentRow += dy;
            if (currentCol < 0 || currentCol >= 8 || currentRow < 0 || currentRow >= 8) {
                return discoveredSquares;
            } else {
                Square s = ChessBoard.board.getSquareAt(currentCol, currentRow);
                discoveredSquares.add(s);

                if( ! s.isEmpty() ){
                    return discoveredSquares;
                }
            }
        }
    }


}
