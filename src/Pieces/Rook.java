package Pieces;

import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Square;

import java.util.HashSet;

public class Rook extends SliderPiece{

    King myKing;//need to store so that castling rights are looked after
    private boolean leftStartingPosition = false;
    public Rook(PieceColor PiececCol, int posCol, int posRow, King myKing, Side side) {
        super(PieceType.ROOK, PiececCol, posCol, posRow, side);

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
        if(otherRook != null && calculateControlledSquares().contains(otherRook.Square)){
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
    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> availableSquares = new HashSet<>();

        int[] iValues = {0,1,0,-1};
        int[] jValues = {1,0,-1,0};

        for(int c = 0; c < 4; c++) {

            availableSquares.addAll(getControlledSquaresInDir(Square , iValues[c] ,jValues[c]));
        }

        return availableSquares;
    }

    @Override
    public void moveTo(Square newSquare){
        Square.removePiece();
        newSquare.addPiece(this);
        Square = newSquare;

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
