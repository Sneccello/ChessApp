package BoardElements.Pieces;

import BoardElements.ChessBoard;
import BoardElements.Side;
import BoardElements.Square;
import ChessAbstracts.BinaryFlag;
import ChessAbstracts.CastlingPiece;
import ChessAbstracts.Moves.Move;

import java.util.HashSet;

public class Rook extends SliderPiece implements CastlingPiece {



    King myKing;//need to store so that castling rights are looked after
    private BinaryFlag leftStartingSquareFlag = new BinaryFlag(false);



    public Rook(PieceColor PieceCol, int posCol, int posRow, King myKing, Side side) {
        super(PieceType.ROOK, PieceCol, posCol, posRow, side);

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

    public void setLeftStartingSquare(boolean b){
        leftStartingSquareFlag.setValue(b);
    }

    @Override
    public double calculateRelativeValue() {
        int nPawnsInGame = ChessBoard.board.getNumberOfPawnsInGame();
        int seventhRankIdx = ( color == PieceColor.WHITE ? 6 : 1 );
        int pawnsInFile = ChessBoard.board.countPawnsInFile(getCol());
        Rook otherRook = getOtherRook();
        int protectionByOtherRook = 0;
        if(otherRook != null && calculateControlledSquares().contains(otherRook.square)){
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

            availableSquares.addAll(getControlledSquaresInDir(square , iValues[c] ,jValues[c]));
        }

        return availableSquares;
    }

    public boolean hasLeftStartingSquare(){
        return leftStartingSquareFlag.value();
    }



    @Override
    public BinaryFlag getLeftStartingSquareFlag() {
        return leftStartingSquareFlag;
    }

    @Override
    public void moveTo(Square newSquare){
        square.removePiece();
        newSquare.addPiece(this);
        square = newSquare;

        if( ! hasLeftStartingSquare() ){
            leftStartingSquareFlag.setValue(true);
        }

    }

    @Override
    public HashSet<Move> calculatePossibleMoves(){

        HashSet<Move> moves = super.calculatePossibleMoves();

        if( ! hasLeftStartingSquare() ){
            for(Move m :  moves){
                m.addFlagToResetWhenUndone(leftStartingSquareFlag);
            }
        }

        return moves;
    }


}
