package Views;

import Pieces.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PieceView {

    Image image;
    Piece observedPiece;
    boolean selected = false;

    public PieceView(String imageName, Piece p) {
        String path = System.getProperty("user.dir") + "\\textures\\" + imageName;
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

    public void paint(Graphics g){

        int x = observedPiece.getSquare().getView().getX(); //meh
        int y = observedPiece.getSquare().getView().getY();
        g.drawImage(image,x,y,null);


        //TODO only for debug

        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(new Color(34,177,76));
        double value = observedPiece.getRelativeValue();
        g.drawString(String.format("%.2f",value),x + 25,y + 50);

    }

    public void setSelected(boolean b){
        selected = b;
    }


}
