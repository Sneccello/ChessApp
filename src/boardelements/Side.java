package boardelements;

import ai.ChessBot;
import ai.evaluationaspects.AbstractBaseEvaluationAspect;
import ai.evaluationaspects.Evaluable;
import ai.evaluationaspects.sideevaluation.*;
import chessabstracts.moves.Move;
import boardelements.pieces.*;
import views.SideView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Side implements Evaluable {
    private final PieceColor color;
    ArrayList<Piece> regularPieces = new ArrayList<Piece>(15); //everything but the king
    private King king;
    public final SideView sideView = new SideView();
    private Side opponent;

    private int numberOfPossibleMoves;

    public ArrayList<AbstractBaseEvaluationAspect> getEvaluationAspects() {
        return evaluationAspects;
    }

    @Override
    public Image getIcon() {
        return sideView.getIcon();
    }

    @Override
    public String getDescription() {
        return color.toString();
    }

    protected ArrayList<AbstractBaseEvaluationAspect> evaluationAspects = new ArrayList<>();
    private ChessBot bot;

    public Side(PieceColor color){
        this.color = color;
    }



    public boolean hasBot(){
        return bot != null;
    }

    public ChessBot getBot(){
        return bot;
    }


    public void setBot(ChessBot bot){
        this.bot = bot;
    }

    public void setOpponent(Side opp){
        opponent = opp;
        initializePieces();
        initializeEvaluationAspects();

    }

    private void initializeEvaluationAspects(){
        evaluationAspects.add(new BishopPairBonus(this));
        evaluationAspects.add(new ConnectedRooksBonus(this));
//        evaluationAspects.add(new PassedPawnsBonus(this)); #TODO buggy
        evaluationAspects.add(new PawnIslandsPenalty(this));
        evaluationAspects.add(new PawnMobilityBonus(this));
//        evaluationAspects.add(new PinsPenalty(this));#TODO buggy
        evaluationAspects.add(new ColorWeaknessPenalty(this));
        evaluationAspects.add(new OpponentCheckmatedBonus(this));
    }





    private void initializePieces(){

        int backRankIdx = (color == PieceColor.WHITE ? 0 : 7);
        this.king = new King(4,backRankIdx,this);
        sideView.addPieceView(king.getView());
        ChessBoard.board.addKing(king);

        Rook r1 = new Rook(0,backRankIdx,this,king);
        Knight k1 = new Knight(1,backRankIdx,this);
        Bishop b1 = new Bishop(2,backRankIdx,this);
        Queen q = new Queen(3,backRankIdx,this);
        Bishop b2 = new Bishop(5,backRankIdx,this);
        Knight k2 = new Knight(6,backRankIdx,this);
        Rook r2 = new Rook(7,backRankIdx,this,king);

        king.setRooks(r1,r2);

        addPiece(r1);
        addPiece(k1);
        addPiece(b1);
        addPiece(q);
        addPiece(b2);
        addPiece(k2);
        addPiece(r2);


        int pawnRow = (color == PieceColor.WHITE ? 1 : 6);
        for(int i = 0; i < 8; i++){
            Pawn p = new Pawn(i, pawnRow, this);
            addPiece(p);
        }

    }


    public double evaluate(){
        double sum = 0;

        for(Piece piece : regularPieces){
            sum += piece.evaluate();
        }
        sum += king.evaluate();

        for(AbstractBaseEvaluationAspect aspect : evaluationAspects){
            double value = aspect.evaluate();
            sum += value;
        }

        return sum;
    }



    public double countMaterial(){
        double sum = 0;
        for(Piece p : regularPieces){
            sum += p.getBasePieceValue();
        }
        return sum;
    }


    public Side getOpponent(){
        return opponent;
    }

    public HashSet<Square> getPawnControlledSquares(){
        HashSet<Square> controlledSquares = new HashSet<>();
        for(Piece p : regularPieces){
            if(p.getType() == PieceType.PAWN){
                controlledSquares.addAll(  ((Pawn) p).calculateControlledSquares()  );
            }
        }
        return controlledSquares;
    }

    public PieceColor getColor(){
        return color;
    }


    private boolean checkIfPawnIsPassedInColumn(Pawn pawn, int colIdx){

        int row = pawn.getRow() + pawn.getRowIncrementTowardsOpponent();
        while(row < 8 && row >= 0) {
            Square s = ChessBoard.board.getSquareAt(colIdx, row);
            if(s.isOccupied() && s.getPieceOnThisSquare().getType() == PieceType.PAWN && s.getPieceOnThisSquare().isDifferentColorAs(pawn)) {
                return false;
            }
            row += pawn.getRowIncrementTowardsOpponent();
        }
        return true;

    }

    public boolean checkPassedPawn(Pawn pawn){

        boolean itIsPassed = true;
        if(pawn.getCol() - 1 >= 0 && pawn.getCol() - 1 < 8){
            itIsPassed = checkIfPawnIsPassedInColumn(pawn, pawn.getCol() - 1 );
        }
        if(pawn.getCol() + 1 >= 0 && pawn.getCol() + 1 < 8){
            itIsPassed = ( itIsPassed && checkIfPawnIsPassedInColumn(pawn, pawn.getCol() + 1 ));
        }

        return itIsPassed;
    }

    public ArrayList<Piece> getRegularPieces(){
        return regularPieces;
    }

    public King getKing(){
        return king;
    }






    public int countPawns(){
        int sum = 0;
        for(Piece p : regularPieces){
            if(p.getType() == PieceType.PAWN){
                sum+=1;
            }
        }
        return sum;
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
            numberOfPossibleMoves += p.getPossibleMoves().size();
        }
    }

    public HashSet<Move> getPossibleMoves(){
        HashSet<Move> moves = new HashSet<>();
        for(Piece p : regularPieces){
            moves.addAll(p.getPossibleMoves());
        }
        moves.addAll(king.getPossibleMoves());

        return moves;
    }

    public int getNumberOfPossibleMoves(){
        return getPossibleMoves().size();
    }

    public void calculateKingMoves(){
        king.updatePossibleMoves();
        numberOfPossibleMoves +=king.getPossibleMoves().size();
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

        HashSet<Square> possibleCheckEndingSquares = king.getSquaresToEndCheck();

        if(possibleCheckEndingSquares.isEmpty()){ //the king is in a double-check or checked by a pawn or a night
            for(Piece p : regularPieces){
                p.clearPossibleMoves();
            }
            return;
        }

        //if the check is blockable try to block it

        for(Piece p :regularPieces){
            p.limitMovesToEndCheck(possibleCheckEndingSquares);
        }

    }



}
