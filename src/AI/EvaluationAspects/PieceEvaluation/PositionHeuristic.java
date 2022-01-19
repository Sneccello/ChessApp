package AI.EvaluationAspects.PieceEvaluation;

import AI.EvaluationAspects.AbstractBaseEvaluationAspect;
import BoardElements.ChessBoard;
import BoardElements.Pieces.Piece;
import BoardElements.Pieces.PieceColor;
import BoardElements.Pieces.PieceType;

import java.util.HashMap;

public class PositionHeuristic extends AbstractBaseEvaluationAspect {


    private static final HashMap<PieceType,int[][]> middleGameTables = new HashMap();
    //private HashMap<PieceType,int[][]> endGameTable = new HashMap(); currently only the king is different, im checking this case directly in getTableValue()

    private static final int[][] kingMid =
            {{0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 10, 10, -5, -5, -5, 10, 0}};


    private static final int[][] kingEnd =
            {{-50,-40,-30,-20,-20,-30,-40,-50},
                    {-30,-20,-10,  0,  0,-10,-20,-30},
                    {-30,-10, 20, 30, 30, 20,-10,-30},
                    {-30,-10, 30, 40, 40, 30,-10,-30},
                    {-30,-10, 30, 40, 40, 30,-10,-30},
                    {-30,-10, 20, 30, 30, 20,-10,-30},
                    {-30,-30,  0,  0,  0,  0,-30,-30},
                    {-50,-30,-30,-30,-30,-30,-30,-50}};

    private static final int[][] queenMid =
            {{-20, -10, -10, -5, -5, -10, -10, -20},
                    {-10, 0, 0, 0, 0, 0, 0, -10},
                    {-10, 0, 5, 5, 5, 5, 0, -10},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {-5, 0, 5, 5, 5, 5, 0, -5},
                    {-10, 5, 5, 5, 5, 5, 0, -10},
                    {-10, 0, 5, 0, 0, 0, 0, -10},
                    {-20, -10, -10, 0, 0, -10, -10, -20}};

    private static final int[][] rookMid =
            {{10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 10, 10, 0, 0, 0},
                    {0, 0, 0, 10, 10, 5, 0, 0}};

    private static final int[][] bishopMid =
            {{0,   0,   0,   0,   0,   0,   0,   0},
                    {  0,   0,   0,   0,   0,   0,   0,   0},
                    {  0,   0,   0,   0,   0,   0,   0,   0},
                    {  0,  10,   0,   0,   0,   0,  10,   0},
                    {  5,   0,  10,   0,   0,  10,   0,   5},
                    {  0,  10,   0,  10,  10,   0,  10,   0},
                    {  0,  10,   0,  10,  10,   0,  10,   0},
                    {  0,   0, -10,   0,   0, -10,   0,   0}};
    private static final int[][] knightMid =
            {{-5,  -5, -5, -5, -5, -5,  -5, -5},
                    {  -5,   0,  0, 10, 10,  0,   0, -5},
                    {  -5,   5, 10, 10, 10, 10,   5, -5},
                    {  -5,   5, 10, 15, 15, 10,   5, -5},
                    {  -5,   5, 10, 15, 15, 10,   5, -5},
                    {  -5,   5, 10, 10, 10, 10,   5, -5},
                    {  -5,   0,  0,  5,  5,  0,   0, -5},
                    {  -5, -10, -5, -5, -5, -5, -10, -5}};

    private static final int[][] pawnMid =
            {{ 0,   0,   0,   0,   0,   0,   0,   0},
                    {30,  30,  30,  40,  40,  30,  30,  30},
                    {20,  20,  20,  30,  30,  30,  20,  20},
                    {10,  10,  15,  25,  25,  15,  10,  10},
                    { 5,   5,   5,  20,  20,   5,   5,   5},
                    { 5,   0,   0,   5,   5,   0,   0,   5},
                    { 5,   5,   5, -10, -10,   5,   5,   5},
                    { 0,   0,   0,   0,   0,   0,   0,   0}};

    private Piece piece;

    public PositionHeuristic(Piece piece){
        middleGameTables.put(PieceType.PAWN,pawnMid);
        middleGameTables.put(PieceType.ROOK,rookMid);
        middleGameTables.put(PieceType.KING,kingMid);
        middleGameTables.put(PieceType.QUEEN,queenMid);
        middleGameTables.put(PieceType.BISHOP,bishopMid);
        middleGameTables.put(PieceType.KNIGHT,knightMid);


        this.piece = piece;
        aspectCoefficient = 100;

    }

    @Override
    public int calculateAspectValue(){
        PieceColor color = piece.getColor();
        int row = piece.getRow();
        int col = piece.getCol();
        if(color == PieceColor.WHITE){ //interestingly its white who has to be inverted because of the layouts of the tables above
            col = 7 - col;
            row = 7 - row;
        }

        int[][] tableToUse = middleGameTables.get(piece.getType());

        if(ChessBoard.board.inEndGame() && piece.getType() == PieceType.KING){
            tableToUse = kingEnd;
        }

        return tableToUse[row][col];
    }

}
