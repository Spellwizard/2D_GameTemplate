package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Conveyor extends Building {


    //The height and width of this object will not be in pixels but rather in tiles
    //eg: 3 columns by 2 rows with each row / column size defined by a provided gameMap
    private Map gameMap;

    //These are 'preset' of the width and height of the object and are used to set the initial width and size in comparision to the given Map tile width and legnth
    private int Width = 1;
    private int Height = 1;

    //It is important to note that some values shouldn't be changed

    /**
     * @param posX - the actual X position of the object
     * @param posY - the actual Y position of the object
     *  objWidth the width of the object given in tiles and NOT pixels
     * objHeight the height of the object given in tiles and NOT pixels
     * @param defaultHSpeed This is yet to be fully implented but will not be used for movement speed but rather processing speed
     * @param defaultVSpeed This is yet to be fully implented but will not be used for movement speed but rather processing speed
     * @param c The Color of the object
     * @param gameMap - This is very important as when initalized until changed this is what the actual width / length of the Building is
     *                This means that if the tile width / length are changed then this must be updated else there will be an unwanted offset
     * @param image_Up - the image to draw when the object is moving / facing up
     * @param image_Down - the image to draw when the object is moving / facing down
     * @param image_Left - the image to draw when the object is moving / facing left
     * @param image_Right - the image to draw when the object is moving / facing right
     */
    public Conveyor(int posX, int posY,
                          int defaultHSpeed, int defaultVSpeed, Color c, Map gameMap,
                          BufferedImage image_Up, BufferedImage image_Down, BufferedImage image_Left, BufferedImage image_Right
            , boolean isSolid
    ) {

        super(posX, posY, 1, 1, defaultHSpeed, defaultVSpeed, gameMap,isSolid);

        //Due to not being able to call child variables before initalizing the parent coorectly setting values must occur here
        //Assume the provided height is given in columns / rows and multiply by the width / height of
        super.setObjHeight(gameMap.getTileHeight()*Height);
        super.setObjWidth(gameMap.getTileWidth()*Width);

        this.gameMap = gameMap;

        //set the images
        super.setUp_Image(image_Up);
        super.setDown_Image(image_Down);
        super.setL_Image(image_Left);
        super.setR_Image(image_Right);
    }

    /**
     * @param posX - the actual X position of the object
     * @param posY - the actual Y position of the object
     *  objWidth the width of the object given in tiles and NOT pixels
     * objHeight the height of the object given in tiles and NOT pixels
     * @param defaultHSpeed This is yet to be fully implented but will not be used for movement speed but rather processing speed
     * @param defaultVSpeed This is yet to be fully implented but will not be used for movement speed but rather processing speed
     * @param c The Color of the object
     * @param gameMap - This is very important as when initalized until changed this is what the actual width / length of the Building is
     *                This means that if the tile width / length are changed then this must be updated else there will be an unwanted offset
     * @param fileName read a file address for the picture of the object
     */
    public Conveyor(int posX, int posY,
                          int defaultHSpeed, int defaultVSpeed, Color c, Map gameMap, String fileName, boolean isSolid) {

        super(posX, posY, 1, 1, defaultHSpeed, defaultVSpeed, gameMap, isSolid);


        //Due to not being able to call child variables before initalizing the parent coorectly setting values must occur here
        super.setObjHeight(gameMap.getTileHeight()*Height);
        super.setObjWidth(gameMap.getTileWidth()*Width);
        this.gameMap = gameMap;
        initImages(fileName);
    }


    @Override
    /**
     * Use the parent version of the draw object if the picture is not correctly initalized
     * then use the 'up_image' to draw the object
     * given a 2d Graphics graw the object in reference to viewX and viewY of the MapView
     *Eg: object 0,0 and the mapview 1,1
     *draw the object @ -1,-1
     *Additionally scale the object in comparision to the width and height of the map view to allow objects to appear
     *to get bigger / smaller as the zoom changes
     *@param gg - the Graphics 2D that the object is drawn on
     *@param maps - the Map that is used to draw the object relatively to the view X, Y positions
     *
     */
    public void drawobj(Graphics2D gg, Map maps)
    {
        if(super.getUp_Image()==null)super.drawobj(gg, maps);

        else{
            gg.drawImage(super.getUp_Image(),
                    super.getPosX() - maps.getViewX(),
                    super.getPosY()- maps.getViewY(),

                    super.getObjWidth(),

                    super.getObjHeight(),

                    null);
        }

    }

    /**
     * @param fileName - fullfile path to override all the images to ensure that even in bad coding that the only image is refernced
     */
    public void initImages(String fileName){

        try{
            super.setUpImage(fileName);
            super.setDownImage(fileName);
            super.setLeftImage(fileName);
            super.setRightImage(fileName);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }


    public Map getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map gameMap) {
        this.gameMap = gameMap;
    }
}
