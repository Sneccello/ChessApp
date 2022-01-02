package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;

import java.util.HashSet;

public class Rook extends SliderPiece{

    King myKing;//need to store so that castling rights are looked after
    private boolean leftStartingPosition = false;
    public Rook(PieceColor figCol, int posCol, int posRow, King myKing, Side side) {
        super(PieceType.ROOK, figCol,posCol,posRow,side);

        this.myKing = myKing;

    }

    @Override
    public double getRelativeValue() {
        return 0;
    }


    @Override
    public HashSet<Tile> calculateControlledTiles() {
        HashSet<Tile> availableTiles = new HashSet<>();

        int[] iValues = {0,1,0,-1};
        int[] jValues = {1,0,-1,0};

        for(int c = 0; c < 4; c++) {

            availableTiles.addAll(getControlledTilesInDir(tile , iValues[c] ,jValues[c]));
        }

        return availableTiles;
    }

    @Override
    public void moveTo(Tile newTile){
        tile.removeFigure();
        newTile.addFigure(this);
        tile = newTile;

        if(!leftStartingPosition){

            if(getCol() == 7){
                myKing.disableCastleShort();
            }
            else{
                myKing.disableCastleLong();
            }

            leftStartingPosition = true;
        }

    }



}
