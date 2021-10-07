package BoardElements;

import Figures.*;
import Views.SideView;

import java.util.ArrayList;
import java.util.HashSet;

public class Side {
    private final PieceColor color;
    ArrayList<Piece> regularPieces = new ArrayList<Piece>(15); //everything but the king
    private final King king;
    private final SideView sideView = new SideView();

    private int noPossibleMovesInRound;

    public Side(PieceColor color){

        this.color = color;
        int backRankIdx = (color == PieceColor.WHITE ? 0 : 7);

        this.king = new King(color,4,backRankIdx,this);
        sideView.addPieceView(king.getView());
        ChessBoard.board.addKing(king);

        Rook r1 = new Rook(color,0,backRankIdx,king,this);
        Knight k1 = new Knight(color,1,backRankIdx,this);
        Bishop b1 = new Bishop(color,2,backRankIdx,this);
        Queen q = new Queen(color,3,backRankIdx,this);
        Bishop b2 = new Bishop(color,5,backRankIdx,this);
        Knight k2 = new Knight(color,6,backRankIdx,this);
        Rook r2 = new Rook(color,7,backRankIdx,king,this);

        addPiece(r1);
        addPiece(k1);
        addPiece(b1);
        addPiece(q);
        addPiece(b2);
        addPiece(k2);
        addPiece(r2);


        int pawnRow = (color == PieceColor.WHITE ? 1 : 6);
        for(int i = 0; i < 8; i++){
            Pawn p = new Pawn(color,i, pawnRow, this);
            addPiece(p);
        }
    }

    public PieceColor getColor(){
        return color;
    }


    public void addPiece(Piece p){
        regularPieces.add(p);
        sideView.addPieceView(p.getView());
    }

    public SideView getView(){
        return sideView;
    }

    public void removePiece(Piece p){
        regularPieces.remove(p);
        sideView.removePieceView(p.getView());
    }


    public void calculateRegularPieceMoves(){
        for(Piece p : regularPieces){
            p.updatePossibleMoves();
        }
    }


    public void purgeRegularPieceMovesRegardingPins(){
        for(Piece p : regularPieces){
            p.checkLegalMovesBeingPinned();
            noPossibleMovesInRound += p.getPossibleMoves().size();
        }
    }

    public int getNoPossibleMovesInRound(){
        return noPossibleMovesInRound;
    }

    public void calculateKingMoves(){
        king.updatePossibleMoves();
        noPossibleMovesInRound+=king.getPossibleMoves().size();
    }

    public void clearPins(){
        for(Piece p : regularPieces){
            p.clearPins();
        }
    }


    public void limitMovesIfInCheck(){

        if( ! king.isInCheck()){
            return;
        }

        HashSet<Tile> possibleCheckEndingTiles = king.getTilesToEndCheck();
        if(possibleCheckEndingTiles == null){ //the king is in at least a double check so only the king can move
            for(Piece p : regularPieces){
                p.clearPossibleMoves();
            }
            return;
        }

        //if the king is in check but not in double, try blocking the check
        Check theCheckToEnd = king.getChecks().get(0);
        HashSet<Tile> targetTilesToEndCheck = theCheckToEnd.getPossibleEndingTiles();

        for(Piece p :regularPieces){
            p.limitMovesToEndCheck(targetTilesToEndCheck);
        }

    }



}
