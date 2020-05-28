package sample;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ore extends SolidObject {

    private Map gameMap;

    public Ore(int objWidth, int objHeight, int posX, int posY, Color objColour, BufferedImage r_Image, BufferedImage l_Image, BufferedImage up_Image, BufferedImage down_Image) {
        super(objWidth, objHeight, posX, posY, objColour, r_Image, l_Image, up_Image, down_Image);
    }

    @Override
    /**
     * Use the parent version of the draw object if the picture is not correctly initalized
     * then use the 'up_image' to draw the object
     *
     *@param gg - the Graphics 2D that the object is drawn on
     *@param maps - the Map that is used to draw the object relatively to the view X, Y positions
     *
     */
    public void drawobj(Graphics2D gg, Map maps)
    {
        if(super.getUp_Image()==null){
            super.drawobj(gg, maps);
        }

        else{
            //Draw the object using the Up image as the default
            gg.drawImage(super.getUp_Image(),
                    super.getPosX() - maps.getViewX(),
                    super.getPosY()- maps.getViewY(),

                    super.getObjWidth(),

                    super.getObjHeight(),

                    null);

        }

    }

    /**
     * Calc collisiions siwht miners
     * @param a
     */
    public void calcCollision(Building a){

    }



    protected static void drawOre(Graphics2D gg, ArrayList<Ore> list, Map maps){

        //safety check
        if(list!=null){
            //Loop through each object in the arraylist
            for(Ore a: list){
                //  a.setUp_Image(defaultCoal.getUp_Image());
                //only draw the object if it collides with the map to prevent unneccessary clutter
                if(a!=null&&
                        a.isCollision(maps.getViewX(),maps.getViewY(),maps.getMapWidth(),maps.getMapHeight())) {


                    a.setObjWidth(maps.getTileWidth());
                    a.setObjHeight(maps.getTileHeight());

                    a.drawobj(gg, maps);

                }
            }
        }
    }


    public Map getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map gameMap) {
        this.gameMap = gameMap;
    }
}
