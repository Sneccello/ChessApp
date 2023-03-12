package boardelements.pieces;

import ai.evaluationaspects.pieceevaluation.KnightMobilityBonus;
import ai.evaluationaspects.pieceevaluation.PawnsInGame;
import ai.evaluationaspects.pieceevaluation.UndefendedPiecePenalty;
import boardelements.*;
import chessabstracts.Check;

import java.util.HashSet;

public class Knight extends Piece {
    public Knight( int posCol, int posRow, Side side) {
        super(PieceType.KNIGHT, posCol,posRow,side);

    }


    @Override
    protected void initializeEvaluationAspects() {
        evaluationAspects.add(new UndefendedPiecePenalty(this));
        evaluationAspects.add(new PawnsInGame(false));
        evaluationAspects.add(new KnightMobilityBonus(this));
    }

    public HashSet<Square> calculateControlledSquares() {
        HashSet<Square> controlledSquares = new HashSet<>();

        for(int absRowOffset = 1; absRowOffset <= 2; absRowOffset++) {
            int absColOffset = (absRowOffset == 1 ? 2 : 1);
            for (int colSign = -1; colSign <= 1; colSign += 2) {
                for (int rowSign = -1; rowSign <= 1; rowSign += 2) {
                    int rowOffset = absRowOffset * rowSign;
                    int colOffset = absColOffset * colSign;

                    int candidateCol = getCol() + colOffset;
                    int candidateRow = getRow() + rowOffset;

                    if (ChessBoard.board.checkIfCoordsAreOnTheBoard(candidateCol,candidateRow)) {
                        Square candidateSquare = ChessBoard.board.getSquareAt(candidateCol, candidateRow);

                        controlledSquares.add(candidateSquare);
                        if( isTheEnemyKingForMe(this,candidateSquare.getPieceOnThisSquare())){
                            King enemyKing = (King) candidateSquare.getPieceOnThisSquare();
                            enemyKing.addCheck(new Check(this,new HashSet<>())); //knight check cannot be blocked, empty set
                        }

                    }
                }
            }
        }
        return controlledSquares;

    }
}
