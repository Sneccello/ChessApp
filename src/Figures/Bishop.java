package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;


import java.util.HashSet;

public class Bishop extends SliderPiece{

    public Bishop(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.BISHOP, figCol,posCol,posRow,side);
    }


    @Override
    public double calculateRelativeValue() {
        double startValue = baseValue;

        int protectedPieces = 0;
        int blockedByOwnPawns = 0;
        int ownPawnsOnSameColor = 0;
        int isUndefended = 1; //boolean value, but will be used in calculations

        for(Move m : possibleMoves){
            if(m.getCapturedPiece() != null &&  ! m.getCapturedPiece().isDifferentColorAs(this)){
                protectedPieces += 1;

                if(m.getCapturedPiece().getType() == PieceType.PAWN && getMoveLength(m) == 1){
                    blockedByOwnPawns+=1;
                }
            }
        }

        int otherBishopIsAlive = 0;//boolean value, but will be used in calculations
        int numberOfAlliedPawns = 0;
        for(Piece p : mySide.getRegularPieces()){
            if(p.getType() == PieceType.PAWN){
                numberOfAlliedPawns+=1;
                if(areSameColorComplex(tile,p.getTile())) {
                    ownPawnsOnSameColor += 1;
                }
            }
            if(isUndefended == 1 && p.isProtecting(tile)){
                isUndefended = 0;
            }
            if(p.getType() == PieceType.BISHOP && p != this){
                otherBishopIsAlive = 1;
            }

        }
        double alliedPawnsOnSameColorRatio = (double)ownPawnsOnSameColor/numberOfAlliedPawns;


        double colorWeakness = Math.abs(0.5-alliedPawnsOnSameColorRatio);

        relativeValue =  startValue + 0.25 * protectedPieces + 0.2 * otherBishopIsAlive
                        - 0.25 * isUndefended - 1.5 * colorWeakness - 0.1 * blockedByOwnPawns + pieceSquareTableDB.getTableValue(this);

        return relativeValue;

    }



    private int getMoveLength(Move m){
        Tile to = m.getTo();
        Tile from = m.getFrom();

        return Math.abs(to.getRow() - from.getRow()); //since being a bishop means moving the same amount of tiles in both directions
    }

    @Override
    public HashSet<Tile> calculateControlledTiles() {
        HashSet<Tile> controlledTiles = new HashSet<>();
        for(int i = -1; i <=1; i+=2 ){
            for(int j = -1; j <= 1; j+=2){
                controlledTiles.addAll(getControlledTilesInDir(tile, i, j));
            }
        }
        return controlledTiles;
    }








}
