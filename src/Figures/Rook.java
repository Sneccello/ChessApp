package Figures;

import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Tile;

import java.util.HashSet;

public class Rook extends SliderPiece{

    King myKing;//need to store so that castling rights are looked after
    private boolean leftStartingPosition = false;
    public Rook(PieceColor figCol, int posCol, int posRow, King myKing, Side side) {
        super(PieceType.ROOK, figCol, posCol, posRow, side);

        this.myKing = myKing;

    }


    private Rook getOtherRook(){
        for(Piece p : mySide.getRegularPieces()){
            if(p.type == PieceType.ROOK && p != this){
                return (Rook) p;
            }
        }
        return null;
    }

    @Override
    public double calculateRelativeValue() {
        int nPawnsInGame = ChessBoard.board.getNumberOfPawnsInGame();
        int seventhRankIdx = ( color == PieceColor.WHITE ? 6 : 1 );
        int pawnsInFile = ChessBoard.board.countPawnsInFile(getCol());
        Rook otherRook = getOtherRook();
        int protectionByOtherRook = 0;
        if(otherRook != null && calculateControlledTiles().contains(otherRook.tile)){
            protectionByOtherRook = 1;
        }
        int seventhRankPositionValue = (seventhRankIdx == getRow() ? 1 : 0);

        relativeValue =  1.0/16 * nPawnsInGame
                + 0.5 * (seventhRankPositionValue
                + protectionByOtherRook) * (seventhRankPositionValue + protectionByOtherRook)
                - pawnsInFile * pawnsInFile * 0.25
                + pieceSquareTableDB.getTableValue(this);

        return relativeValue;
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
