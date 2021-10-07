package Views;

import Figures.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PieceView {

    Image image;
    Piece observedFig;
    boolean selected = false;

    public PieceView(String imageName, Piece f) {
        String path = System.getProperty("user.dir") + "\\textures\\" + imageName;
        try {
            image = ImageIO.read(new File(path));
            int ogHeight = image.getHeight(null);
            int ogWidth = image.getHeight(null);
            int wantedHeight = TileView.TILE_SIZE;
            double ratio = (double)wantedHeight/ogHeight;
            int wantedWidth = (int)(ogWidth*ratio);

            image = image.getScaledInstance(wantedWidth,wantedHeight, Image.SCALE_DEFAULT);
            observedFig = f;

        } catch (IOException ex ){
            System.err.println("FileName Not Found While Loading: "  + path);
        }
    }

    public void paint(Graphics g){

        int x = observedFig.getTile().getView().getX(); //meh
        int y = observedFig.getTile().getView().getY();
        g.drawImage(image,x,y,null);

    }

    public void setSelected(boolean b){
        selected = b;
    }


}
