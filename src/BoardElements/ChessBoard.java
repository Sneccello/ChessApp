package BoardElements;

import Figures.*;
import Views.ChessBoardView;
import Views.TileView;

import java.awt.*;
import java.util.*;

public class ChessBoard {
    //ArrayList<Piece> pieces = new ArrayList<>(32);

    HashMap<PieceColor,Side> sides = new HashMap<>(2);

    Tile[][] tiles = new Tile[8][8];

    public final static ChessBoard board = new ChessBoard();
    private final ChessBoardView chessBoardView;


    private final HashSet<Tile> illegalTilesForWhiteKing = new HashSet<>();
    private final HashSet<Tile> illegalTilesForBlackKing = new HashSet<>();
    private final LinkedHashMap<PieceColor, King> kings = new LinkedHashMap<>();

    private boolean whiteToMove = true;

    private final LinkedList<Move> moves = new LinkedList<>();

    private ChessBoard(){

        TileView[] tileViews = new TileView[64];

        for(int row = 0; row < 8; row++){
            for(int col = 0; col <  8; col++){

                Tile t = new Tile(null, col, row);
                tiles[col][row] = t;

                int idx = col*8+row;
                tileViews[idx] = t.getView();
            }
        }

        chessBoardView = new ChessBoardView();
        chessBoardView.setTileViews(tileViews);
    }

    public void addSide(Side s){
        sides.put(s.getColor(),s);
        chessBoardView.addSideView(s.getView());
    }

    public Side getMySide(Piece p){
        return sides.get(p.getColor());
    }

    public Side getEnemySide(Piece p) {
        if (p.getColor() == PieceColor.WHITE) {
            return sides.get(PieceColor.BLACK);
        } else {
            return sides.get(PieceColor.WHITE);
        }
    }

    public void addIllegalKingTilesForOpponent(Piece sender, HashSet<Tile> tiles){
        HashSet<Tile> setToWorkWith = (sender.getColor() == PieceColor.WHITE ? illegalTilesForBlackKing : illegalTilesForWhiteKing);
        setToWorkWith.addAll(tiles);

    }


    public boolean checkPassedPawn(Pawn pawn){
        Side enemySide = pawn.getColor() == PieceColor.WHITE ? sides.get(PieceColor.BLACK) : sides.get(PieceColor.WHITE);

        return enemySide.checkPassedPawn(pawn);

    }

    public void addIllegalKingTileForOpponent(Piece sender, Tile t){
        HashSet<Tile> setToWorkWith = (sender.getColor() == PieceColor.WHITE ? illegalTilesForBlackKing : illegalTilesForWhiteKing);
        setToWorkWith.add(t);
    }


    public HashSet<Tile> getIllegalKingTilesFor(PieceColor color){
        return (color == PieceColor.WHITE ? illegalTilesForWhiteKing : illegalTilesForBlackKing);
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


    public void registerCheckEnemyKing(Piece sender,HashSet<Tile> possibleBlockingTiles){
        PieceColor opponentColor =  (sender.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
        kings.get(opponentColor).addCheck(new Check(sender, possibleBlockingTiles));
    }


    public ChessBoardView getView(){
        return chessBoardView;
    }



    public Tile getTileAt(int col, int row){
        return tiles[col][row];
    }

    public int getNumberOfPawnsInGame(){
        return sides.get(PieceColor.WHITE).countPawns() + sides.get(PieceColor.BLACK).countPawns();
    }

    public void moveWasMade(Move m){

        moves.add(m);

        illegalTilesForBlackKing.clear();
        illegalTilesForWhiteKing.clear();

        calculateMoves();

        whiteToMove = ! whiteToMove;

    }

    public void addKing(King k){
        kings.put(k.getColor(),k);
    }


    public void calculateMoves(){ //TODO should only calculate for one side
        //TODO checkmate occurs when:
        //the king has no moves and on other (allied) piece can block for him

        clearPinsAndCheckFromPrevMove();

        sides.get(PieceColor.WHITE).calculateRegularPieceMoves();
        sides.get(PieceColor.BLACK).calculateRegularPieceMoves();

        sides.get(PieceColor.WHITE).purgeRegularPieceMovesRegardingPins();
        sides.get(PieceColor.BLACK).purgeRegularPieceMovesRegardingPins();

        for(PieceColor c : kings.keySet()){
            kings.get(c).banNeighbouringTilesForEnemyKing();
        }
        sides.get(PieceColor.WHITE).calculateKingMoves();
        sides.get(PieceColor.BLACK).calculateKingMoves();

        sides.get(PieceColor.WHITE).limitMovesIfInCheck();
        sides.get(PieceColor.BLACK).limitMovesIfInCheck();





    }


    public PieceColor colorToMove(){
        return whiteToMove ? PieceColor.WHITE : PieceColor.BLACK;
    }

    private void clearPinsAndCheckFromPrevMove(){

        for(PieceColor c : kings.keySet()){
            kings.get(c).clearChecks();
        }

        for(PieceColor c : sides.keySet()){//resetting pinned flags from previous move
            sides.get(c).clearPins();
        }
    }

    public boolean colorWhichIsToMoveIsInCheck(){
        if(whiteToMove){
            return kings.get(PieceColor.WHITE).isInCheck();
        }
        else{
            return kings.get(PieceColor.BLACK).isInCheck();
        }
    }



    public void printBoard(){


        HashMap<PieceType,Character> dict = new HashMap<>();
        dict.put(PieceType.KING,'+');
        dict.put(PieceType.QUEEN,'Q');
        dict.put(PieceType.ROOK,'R');
        dict.put(PieceType.KNIGHT,'K');
        dict.put(PieceType.BISHOP,'B');
        dict.put(PieceType.PAWN,'P');



        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 8; j++){
                if(board.getTileAt(j,i).isEmpty()){
                    System.out.print(".");
                }
                else {
                    System.out.print(dict.get(board.getTileAt(j,i).getPieceOnThisTile().getType()));
                }
            }
            System.out.print('\n');
        }
        System.out.println();
    }


}
