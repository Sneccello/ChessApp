package Views;

import BoardElements.ChessBoard;
import BoardElements.Pieces.Pawn;
import BoardElements.Pieces.PieceType;
import ChessAbstracts.Moves.Move;
import BoardElements.Square;
import BoardElements.Pieces.Piece;

import java.awt.*;
import java.util.HashSet;


public class SquareView {

    Square observedSquare;
    public static final int Square_SIZE = 80;
    private final Color color;
    private static final Color selectedColor = Color.CYAN;
    private static final Color darkColor = new Color(100,100,100);
    private static final Color lightColor = Color.WHITE;

    int x;
    int y;

    public SquareView(Square s){
        observedSquare = s;


        color = (s.getCol()+s.getRow()) % 2 == 0? darkColor : lightColor;

        int boardHeight = Square_SIZE * 8; //


        x = observedSquare.getCol()* Square_SIZE;
        y = boardHeight - Square_SIZE - observedSquare.getRow()* Square_SIZE; //a1 should be in the bottom left corner

    }

    public Square getObservedSquare(){
        return observedSquare;
    }
    public void paint(Graphics g){

        if(Piece.getSelectedPiece()!= null &&  Piece.getSelectedPiece().canStepTo(observedSquare)){
            g.setColor(selectedColor);
        }
        else {
            g.setColor(color);
        }
        g.fillRect(x,y,Square_SIZE,Square_SIZE);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }




    public void clicked() {

        Piece selectedPiece = Piece.getSelectedPiece();


        if (selectedPiece != null) { //move to an empty Square or capture something

            selectedPiece.tryToMoveToSquare(observedSquare);


            Piece.selectPiece(null); //deselect

        }else {//select Piece

            observedSquare.trySelectingPieceOnThisSquare(); //checks if it the right color to move

        }
    }


    public boolean clickIsOnMe(int x, int y){
        return x > getX() && x< getX() + SquareView.Square_SIZE && y > getY() && y < getY()+SquareView.Square_SIZE;
    }


}
