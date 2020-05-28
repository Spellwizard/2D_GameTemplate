package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundImage extends SolidObject {

    private BufferedImage image;


    public BackgroundImage(int posX, int posY, int width, int height, BufferedImage image) {
        super(posX,posY,width, height, Color.white);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
