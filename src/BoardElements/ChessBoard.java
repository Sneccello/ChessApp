package BoardElements;

import AI.ChessBot;
import ChessAbstracts.Moves.Move;
import BoardElements.Pieces.*;
import Views.ChessBoardView;
import Views.SquareView;

import java.util.*;

import static BoardElements.Pieces.PieceColor.BLACK;
import static BoardElements.Pieces.PieceColor.WHITE;

public class ChessBoard {
    //ArrayList<Piece> pieces = new ArrayList<>(32);

    HashMap<PieceColor,Side> sides = new HashMap<>(2);



    Square[][] Squares = new Square[8][8];

    public final static ChessBoard board = new ChessBoard();
    private ChessBoardView chessBoardView;


    private final HashSet<Square> illegalSquaresForWhiteKing = new HashSet<>();
    private final HashSet<Square> illegalSquaresForBlackKing = new HashSet<>();
    private final LinkedHashMap<PieceColor, King> kings = new LinkedHashMap<>();

    private boolean whiteToMove = true;

    public void registerView(ChessBoardView view){
        this.chessBoardView = view;
    }

    private final LinkedList<Move> moves = new LinkedList<>();

    private final  SquareView[] squareViews = new SquareView[64];

    private ChessBoard(){



        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                Square s = new Square(null, col, row);
                Squares[col][row] = s;

                int idx = col * 8 + row;
                squareViews[idx] = s.getView();
            }
        }
    }


    public SquareView[] getSquareViews(){
        return squareViews;
    }

    public void addSide(Side s){
        sides.put(s.getColor(),s);
        chessBoardView.addSideView(s.getView());
    }



    public HashMap<PieceColor,Side> getSides(){
        return sides;
    }

    public void addIllegalKingSquaresForOpponent(Piece sender,HashSet<Square> Squares){
        HashSet<Square> setToWorkWith = (sender.getColor() == WHITE ? illegalSquaresForBlackKing : illegalSquaresForWhiteKing);
        setToWorkWith.addAll(Squares);

    }

    public boolean checkIfCoordsAreOnTheBoard(int col, int row){
        return (col < 8 && col >= 0 && row < 8 && row >= 0);
    }

    public boolean inEndGame(){ //lets say 15 or less points of material is left for each side.
        for(PieceColor pc : sides.keySet()){
            double materialCount = sides.get(pc).countMaterial();
            if(materialCount > 15) {
                return false;
            }
        }
        return true;
    }


    public int getMoveCount(){
        return moves.size();
    }

    public HashSet<Square> getIllegalKingSquaresFor(PieceColor color){
        return (color == WHITE ? illegalSquaresForWhiteKing : illegalSquaresForBlackKing);
    }


    public Move getLastMove(){
        return moves.getLast();
    }


    /* TODO
    example:
    1. e4 e5 2. Nf3 d6 3. d4 Bg4 4. dxe5 Bxf3 5. Qxf3 dxe5 6. Bc4 Nf6 7. Qb3 Qe7 8.
    Nc3 c6 9. Bg5 b5 10. Nxb5 cxb5 11. Bxb5+ Nbd7 12. O-O-O Rd8 13. Rxd7 Rxd7 14.
    Rd1 Qe6 15. Bxd7+ Nxd7 16. Qb8+ Nxb8 17. Rd8# 1-0*/
    public void readAlgebraic(String input){

    }



    public ChessBoardView getView(){
        return chessBoardView;
    }



    public Square getSquareAt(int col, int row){
        return Squares[col][row];
    }

    public int getNumberOfPawnsInGame(){
        return sides.get(WHITE).countPawns() + sides.get(PieceColor.BLACK).countPawns();
    }


    private void refreshRelativeValues(){
        for(PieceColor pc : sides.keySet()){
            for(Piece p : sides.get(pc).regularPieces){
                p.evaluate();
            }
            sides.get(pc).getKing().evaluate();
        }
    }

    private boolean isCheckmate(){

        PieceColor sideToMove = whiteToMove ? WHITE : PieceColor.BLACK;
        String sideName = whiteToMove ? "WHITE" :  "BLACK" ;
        System.out.println(sideName + " to move. Possible moves: " + sides.get(sideToMove).getNumberOfPossibleMoves());

        return sides.get(sideToMove).getNumberOfPossibleMoves() == 0;
    }



    public void moveWasMade(Move m){

        moves.add(m);
        chessBoardView.refresh();
        whiteToMove = ! whiteToMove;


        Side sideToMove = whiteToMove? sides.get(WHITE) : sides.get(BLACK);
        if(sideToMove.hasBot()){
            Move botMove = sideToMove.getBot().getBestMove();
            if(botMove == null){
                System.out.println("BOT IS CHECKMATED");
            }
            else {
                System.out.println(botMove);
                botMove.execute();
            }
            whiteToMove = !whiteToMove;
        }

        calculateMoves();

        if(isCheckmate()){
            endGame();
        }



    }

    public void undoLastMove(){
        if(moves.isEmpty()){
            return;
        }

        Move last = moves.removeLast();
        last.undo();

        calculateMoves();
        whiteToMove = ! whiteToMove;

    }

    public void addKing(King k){
        kings.put(k.getColor(),k);
    }

    private void endGame(){
        System.out.println((whiteToMove ? "White" : "Black") + " is Checkmated ! " );
    }


    public void calculateMoves(){ //TODO should only calculate for one side tbh
        //TODO checkmate occurs when:
        //the king has no moves and on other (allied) piece can block for him

        resetBoard();

        sides.get(WHITE).calculateRegularPieceMoves();
        sides.get(PieceColor.BLACK).calculateRegularPieceMoves();


        sides.get(WHITE).purgeRegularPieceMovesRegardingPins();
        sides.get(PieceColor.BLACK).purgeRegularPieceMovesRegardingPins();


        sides.get(WHITE).calculateKingMoves();
        sides.get(PieceColor.BLACK).calculateKingMoves();

        sides.get(WHITE).limitMovesIfInCheck();
        sides.get(PieceColor.BLACK).limitMovesIfInCheck();

        refreshRelativeValues(); //TODO debug, later delete this from here

    }

    public int countPawnsInFile(int fileIdx){
        int c = 0;
        for(PieceColor pc : sides.keySet()){
            Side s = sides.get(pc);
            for(Piece p : s.getRegularPieces()){
                if(p.getType() == PieceType.PAWN && p.getCol() == fileIdx){
                    c++;
                }
            }
        }

        return c;
    }

    public PieceColor colorToMove(){
        return whiteToMove ? WHITE : PieceColor.BLACK;
    }

    private void resetBoard(){



        for(PieceColor c : kings.keySet()){
            kings.get(c).clearChecks();
        }

        for(PieceColor c : sides.keySet()){//resetting pinned flags from previous move
            sides.get(c).clearPins();
        }

        illegalSquaresForBlackKing.clear();
        illegalSquaresForWhiteKing.clear();

    }

    public Piece getPieceAt(int col, int row){
        return getSquareAt(col,row).getPieceOnThisSquare();
    }


    public void printBoard(int indentation){


        HashMap<PieceType,Character> dict = new HashMap<>();
        dict.put(PieceType.KING,'+');
        dict.put(PieceType.QUEEN,'Q');
        dict.put(PieceType.ROOK,'R');
        dict.put(PieceType.KNIGHT,'K');
        dict.put(PieceType.BISHOP,'B');
        dict.put(PieceType.PAWN,'P');



        for(int row = 7; row >= 0 ; row--){
            for(int i = 0; i < indentation; i++){
                System.out.print(" ");
            }
            for(int col = 0; col < 8; col++){

                if(board.getSquareAt(col,row).isEmpty()){
                    System.out.print(".");
                }
                else {
                    System.out.print(dict.get(board.getSquareAt(col,row).getPieceOnThisSquare().getType()));
                }
            }
            System.out.print('\n');
        }
        System.out.println();
    }





}
