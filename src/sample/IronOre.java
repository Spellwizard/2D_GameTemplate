package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IronOre extends Ore {

    public IronOre(int objWidth, int objHeight, int posX, int posY, Color objColour, BufferedImage r_Image, BufferedImage l_Image, BufferedImage up_Image, BufferedImage down_Image) {
        super(objWidth, objHeight, posX, posY, objColour, r_Image, l_Image, up_Image, down_Image);
    }
}
