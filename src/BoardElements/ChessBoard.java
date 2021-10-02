package BoardElements;

import Figures.Figure;
import Figures.FigureColor;
import Figures.FigureTypes;
import Figures.King;
import Views.ChessBoardView;
import Views.TileView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ChessBoard {
    ArrayList<Figure> figures;
    private final ArrayList<King> kings = new ArrayList<>(2);
    Tile[][] tiles = new Tile[8][8];

    public final static ChessBoard board = new ChessBoard();
    private final ChessBoardView chessBoardView;


    private final HashSet<Tile> illegalTilesForWhiteKing = new HashSet<>();
    private final HashSet<Tile> illegalTilesForBlackKing = new HashSet<>();

    private final LinkedList<Move> moves = new LinkedList<>();



    private ChessBoard(){
        figures = new ArrayList<>();

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


    public void addIllegalKingTilesForOpponent(Figure sender, HashSet<Tile> illegalTiles){
        HashSet<Tile> setToWorkWith = (sender.getColor() == FigureColor.WHITE ? illegalTilesForBlackKing : illegalTilesForWhiteKing);
        setToWorkWith.addAll(illegalTiles);
    }


    public HashSet<Tile> getIllegalKingMovesFor(FigureColor color){
        return (color == FigureColor.WHITE ? illegalTilesForWhiteKing : illegalTilesForBlackKing);
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

    public void addFigure(Figure f){
        figures.add(f);
        chessBoardView.addFigView(f.getView());

    }

    public Tile getTileAt(int col, int row){
        return tiles[col][row];
    }

    public void moveWasMade(Move m){

        moves.add(m);

        illegalTilesForBlackKing.clear();
        illegalTilesForWhiteKing.clear();


        for(Figure f: figures){//reseting pinned flags from previous move
            f.clearPinnerPieces();

        }

        for(Figure f: figures){
            f.updatePossibleMoves();
        }

        for(Figure f : figures){
            f.checkLegalMovesBeingPinned();
        }

        for(King k: kings){
            k.updatePossibleMoves();
        }

    }

    public void removeFigure(Figure f){
        figures.remove(f);
        chessBoardView.removeFigView(f.getView());
    }


    public void addKing(King k){
        kings.add(k);
        chessBoardView.addFigView(k.getView());
    }


    public void printBoard(){


        HashMap<FigureTypes,Character> dict = new HashMap<>();
        dict.put(FigureTypes.KING,'+');
        dict.put(FigureTypes.QUEEN,'Q');
        dict.put(FigureTypes.ROOK,'R');
        dict.put(FigureTypes.KNIGHT,'K');
        dict.put(FigureTypes.BISHOP,'B');
        dict.put(FigureTypes.PAWN,'P');



        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 8; j++){
                if(board.getTileAt(j,i).isEmpty()){
                    System.out.print(".");
                }
                else {
                    System.out.print(dict.get(board.getTileAt(j,i).getFig().getType()));
                }
            }
            System.out.print('\n');
        }
        System.out.println();
    }


}
