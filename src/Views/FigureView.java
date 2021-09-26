package Views;

import BoardElements.ChessBoard;
import Figures.Figure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FigureView {

    Image image;
    Figure observedFig;
    boolean selected = false;

    public FigureView(String imageName, Figure f) {
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

        int x = observedFig.getCol()* TileView.TILE_SIZE;
        int y = observedFig.getRow()* TileView.TILE_SIZE;

        g.drawImage(image,x,y,null);

    }

    public void setSelected(boolean b){
        selected = b;
    }


}
