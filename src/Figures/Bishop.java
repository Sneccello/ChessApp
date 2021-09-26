package Figures;

import BoardElements.ChessBoard;
import BoardElements.Tile;
import Views.FigureView;


import java.util.HashSet;

public class Bishop extends Figure{



    public Bishop(FigureTypes type, FigureColor figCol,int posCol, int posRow) {
        super(type, figCol,posCol,posRow);
    }

    @Override
    public void calculatePossibleMoves() {
        HashSet<Tile> moves = new HashSet<>();
        for(int i = -1; i <=1; i+=2 ){
            for(int j = -1; j <= 1; j+=2){
                calculateMovesInDir(tile.getCol(), tile.getRow() , i, j, moves);
            }
        }
        moves.remove(ChessBoard.board.getTileAt(tile.getCol(),tile.getRow()));//moving to where we are is not a move;

        //System.out.println("moves:" + moves); //TODO for now, return maybe?

        possibleMoves = moves;
    }

    private void calculateMovesInDir(int posCol, int posRow,int dx, int dy, HashSet<Tile> moves){
        boolean endOfSearch = false;
        while(!endOfSearch){

            posCol+=dx;

            posRow+=dy;

            if(posCol < 0 || posCol >= 8 || posRow < 0 || posRow >= 8){
                endOfSearch = true;
            }
            else{
                Tile tile = ChessBoard.board.getTileAt(posCol,posRow);
                if( tile.isEmpty() ||  ! tile.getFigureOnThisTile().isTheSameColorAs(this)) {
                    moves.add(tile);
                }
                if(!tile.isEmpty()){
                    endOfSearch = true;
                }
            }
        }
    }


    @Override
    protected void moveTo(int i, int j) {

    }
}
