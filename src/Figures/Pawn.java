package Figures;

import BoardElements.ChessBoard;
import BoardElements.Move;
import BoardElements.Side;
import BoardElements.Tile;

import java.util.HashSet;

import static BoardElements.ChessBoard.board;


public class Pawn extends Piece {

    int yDir; //where are the pawns are headed. -1 or 1 depending on if the pawn moves increase or decrease the row
    int promotionRowIndex;
    boolean leftStartingTile = false;

    public Pawn(PieceColor figCol, int posCol, int posRow, Side side) {
        super(PieceType.PAWN, figCol,posCol,posRow,side);
        yDir = (color == PieceColor.WHITE ? 1 : -1);
        promotionRowIndex = Math.max(0,yDir*7);

    }

    @Override
    public HashSet<Move> calculatePossibleMoves() {
        HashSet<Move> moves = new HashSet<>();


        for(int step = 1; step <=2 ; step++){ //test moving ahead 1 or 2
            if(step == 1 || !leftStartingTile) {
                Tile t = testMoveForwardBy(step);
                if (t != null) {
                    moves.add(new Move(this,tile,t));
                }
            }
        }


        for(int xOffset = -1 ; xOffset <= 1; xOffset+=2){//test capturing forward
            Tile t = testCaptureForward(xOffset);
            if(t != null){//this tile is occupied by the enemy
                moves.add(new Move(this,tile,t));
                if(t.getFig().isTheEnemyKingFor(this)){
                    ChessBoard.board.registerCheckEnemyKing(this,null);//pawn check cannot be blocked
                }
            }
        }


        //checking en passant
        int startingRowIdxForEnPassant = ( color == PieceColor.WHITE ? 4 : 3);
        if(getRow() == startingRowIdxForEnPassant){
            Move m = ChessBoard.board.getLastMove();
            if(m.getPiece().getType() == PieceType.PAWN){ //if pawn made the last move
                //if that pawn stepped 2 tiles forward from its starting position
                if(m.getTo().getRow() == startingRowIdxForEnPassant && m.getFrom().getRow() == startingRowIdxForEnPassant+yDir*2){
                    System.out.println(true);
                    if(getCol()+1 < 8 && m.getFrom().getCol() == getCol()+1){ //if the last move was made on my side (column-wise)
                        Tile target = ChessBoard.board.getTileAt(getCol()+1,getRow()+yDir);
                        moves.add(new Move(this,tile,target, m.getPiece()));
                        System.out.println(true);
                    }
                    else if(getCol()-1 >= 0 && m.getFrom().getCol() == getCol()-1){//if the last move was made on my other side (column-wise)
                        Tile target = ChessBoard.board.getTileAt(getCol()-1,getRow()+yDir);
                        moves.add(new Move(this,tile,target,m.getPiece()));
                    }
                }
            }
        }

        return moves;
    }



    private Tile testMoveForwardBy(int amount){ //test moving forward 1 or 2
        if(amount > 2 || amount < 0){
            return null;
        }
        int rowAhead = getRow()+amount * yDir;
        if(rowAhead >= 0 && rowAhead < 8){
            Tile t = board.getTileAt(getCol(),rowAhead);
            boolean blocked = false;
            for(int i = 1; i <= amount ; i++){//checking if it can step ahead 1 or 2 (<=amount) steps being not blocked
                if( ! board.getTileAt(getCol(),getRow()+i*yDir).isEmpty()){
                    blocked = true;
                }
            }
            if( ! blocked){
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
        int testX = getCol() + xDir;
        int testY = getRow() + yDir;

        if(testX >= 0 && testX < 8 && testY >= 0 && testY < 8){
            Tile t = ChessBoard.board.getTileAt(testX,testY);

            ChessBoard.board.addIllegalKingTileForOpponent(this,t); //the enemy king cannot step here

            if(! t.isEmpty() && t.getFig().isDifferentColorAs(this)){
                return t;
            }
            else if( ! t.isEmpty() && ! t.getFig().isDifferentColorAs(this)){//this tile is protected and the enemy king cannot capture
                ChessBoard.board.addIllegalKingTileForOpponent(this,t);
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
            mySide.removePiece(this);
            mySide.addPiece(new Queen(color,getCol(),promotionRowIndex,mySide));
            //ChessBoard.board.addPiece(new Queen(color,getCol(),promotionRowIndex)); //TODO autoqueen for now
        }

    }




}
