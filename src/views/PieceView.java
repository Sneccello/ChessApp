package views;

import boardelements.pieces.Piece;
import views.panels.EvaluationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PieceView {


    Image image;
    Piece observedPiece;



    private static PieceView displayedInEvaluation;


    public static EvaluationPanel getEvaluationPanel() {
        return evaluationPanel;
    }
    public Image getImage() {
        return image;
    }

    private static EvaluationPanel evaluationPanel;

    public PieceView(String imageName, Piece p) {
        String path = Paths.get(System.getProperty("user.dir"), "textures", imageName).toString();
        try {
            image = ImageIO.read(new File(path));
            int ogHeight = image.getHeight(null);
            int ogWidth = image.getHeight(null);
            int wantedHeight = SquareView.Square_SIZE;
            double ratio = (double)wantedHeight/ogHeight;
            int wantedWidth = (int)(ogWidth*ratio);

            image = image.getScaledInstance(wantedWidth,wantedHeight, Image.SCALE_DEFAULT);
            observedPiece = p;

        } catch (IOException ex ){
            System.err.println("FileName Not Found While Loading: "  + path);
        }
    }



    public static void setPieceEvaluationPanel(EvaluationPanel p){
        evaluationPanel=p;
    }

    public void paint(Graphics g){

        int x = observedPiece.getSquare().getView().getX(); //meh
        int y = observedPiece.getSquare().getView().getY();
        g.drawImage(image,x,y,null);


        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(new Color(36,140,48));
        double value = observedPiece.getRelativeValue();
        g.drawString(String.format("%.2f",value),x + 15,y + 75);

    }



}
