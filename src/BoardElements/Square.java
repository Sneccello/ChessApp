package BoardElements;

import BoardElements.Pieces.Piece;
import Views.SquareView;

public class Square {

    private Piece pieceOnThisSquare = null;


    private final int col;
    private final int row;
    static private final Character[] colChars = {'a','b','c','d','e','f','g','h'};
    SquareView observer;



    Square(Piece p, int col, int row){
        pieceOnThisSquare = p;
        this.col = col;
        this.row = row;

        observer = new SquareView(this);
    }




    public SquareView getView(){
        return observer;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }


    public boolean isEmpty(){
        return pieceOnThisSquare == null;
    }


    public void removePiece(){
        pieceOnThisSquare = null;
    }




    public void addPiece(Piece p){
        pieceOnThisSquare = p;

    }


    public void trySelectingPieceOnThisSquare(){
        if(pieceOnThisSquare != null && pieceOnThisSquare.getColor() == ChessBoard.board.colorToMove()){
            Piece.selectPiece(pieceOnThisSquare);
        }

    }

    public Piece getPieceOnThisSquare(){
        return pieceOnThisSquare;
    }

    @Override
    public String toString() {
        return colChars[col]+Integer.toString(row+1);
    }




}
