package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;

import java.util.HashSet;

import static BoardElements.ChessBoard.board;


public class Pawn extends Figure{

    int yDir; //where are the pawns are headed. -1 or 1 depending on if the pawn moves increase or decrease the row
    int promotionRowIndex;
    boolean leftStartingTile = false;

    public Pawn(FigureColor figCol, int posCol, int posRow) {
        super(FigureTypes.PAWN, figCol,posCol,posRow);

        yDir = (color == FigureColor.WHITE ? 1 : -1);
        promotionRowIndex = Math.max(0,yDir*7);

    }

    @Override
    public HashSet<Tile> calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<Tile>();

        ChessBoard board =  ChessBoard.board;

        for(int step = 1; step <=2 ; step++){ //test moving ahead 1 or 2
            if(step == 1 || !leftStartingTile) {
                Tile t = testMoveForwardBy(step);
                if (t != null) {
                    moves.add(t);
                }
            }
        }

        for(int xOffset = -1 ; xOffset <= 1; xOffset+=2){//test capturing forward
            Tile t = testCaptureForward(xOffset);
            if(t != null){
                moves.add(t);
            }
        }

        //TODO en passant

        int startingRowForEnPassant = ( color == FigureColor.WHITE ? 5 : 4);
//        if(getRow() == startingRowForEnPassant){
//
//
//        }

        return moves;
    }



    private Tile testMoveForwardBy(int amount){ //test moving forward 1 or 2
        if(amount > 2 || amount < 0){
            return null;
        }
        int rowAhead = getRow()+amount * yDir;
        if(rowAhead >= 0 && rowAhead < 8){
            Tile t = board.getTileAt(getCol(),rowAhead);
            if(t.isEmpty()) {
                return t;
            }
        }
        return null;
    }


    private Tile testCaptureForward(int xDir){
        if(!(xDir == 1 || xDir == -1)){
            return null;
        }

        //move forward 1 and capture left or right
        int testX = getCol()+xDir;
        int testY = getRow() + yDir;

        if(testX >= 0 && testX < 8 && testY >= 0 && testY < 8){
            Tile t = ChessBoard.board.getTileAt(testX,testY);
            if(!t.isEmpty() && t.getFig().isDifferentColorAs(this)){
                return t;
            }
        }
        return null;



    }


    @Override
    public void moveTo(Tile newTile){
        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;
        leftStartingTile = true;

        if(tile.getRow() == promotionRowIndex){
            tile.removeFigure();
            ChessBoard.board.removeFigure(this);
            ChessBoard.board.addFigure(new Queen(color,getCol(),promotionRowIndex)); //TODO autoqueen for now
        }

    }




}
