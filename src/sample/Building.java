package sample;

import java.awt.*;
import java.util.ArrayList;

public class Building extends MovingObject {

    //The height and width of this object will not be in pixels but rather in tiles
    //eg: 3 columns by 2 rows with each row / column size defined by a provided gameMap
    private Map gameMap;

    //The isSolid Boolean is used to dicate on collision with others objects to indicate if the other object should be allowed to overlap
    private boolean isSolid;

    public Building(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed, int defaultVSpeed, Map gameMap, boolean isSolid) {
        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
        this.gameMap = gameMap;
        this.isSolid = isSolid;
    }

    @Override
    /**
     * Override the calcmovment as buildings don't move but they do take actions
     * EG: on calcmovement make a calculation to produce ore or the like
     */
    public void calcMovement(){

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
        if(super.getUp_Image()==null){
            if(super.getDown_Image()==null||
                    super.getR_Image()!=null || super.getL_Image()!=null
            )super.drawobj(gg, maps);
        }

        else{

            //Draw the object going Down
            if(super.getObjVSpeed()>0 && super.getObjHSpeed()==0
            )
                gg.drawImage(super.getDown_Image(),
                        super.getPosX() - maps.getViewX(),
                        super.getPosY()- maps.getViewY(),
                        super.getObjWidth(),
                        super.getObjHeight(),
                        null);

            //Draw the object going Right
            else if(super.getObjVSpeed()==0 && super.getObjHSpeed()>0
            )
                gg.drawImage(super.getR_Image(),
                        super.getPosX() - maps.getViewX(),
                        super.getPosY()- maps.getViewY(),

                        super.getObjWidth(),

                        super.getObjHeight(),

                        null);

            //LEFT
            else if(super.getObjVSpeed()==0 && super.getObjHSpeed()<0
            )
                gg.drawImage(super.getL_Image(),
                        super.getPosX() - maps.getViewX(),
                        super.getPosY()- maps.getViewY(),

                        super.getObjWidth(),

                        super.getObjHeight(),

                        null);

            else
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
     * Given a moving object calculate the coorect action to occur on collision
     * EG: the conveyor will override the system but the standard building
     * will not allow for collisions
     */
    protected void calcCollsion(MovingObject a){
        /**
         * Need to determine
         */
        a.reverseDirection();
    }


    protected static void drawBuildings(Graphics2D gg, ArrayList<Building> list, Map maps){
        //safety check
        if(list!=null){
            //Loop through each object in the arraylist
            for(SolidObject a: list){
                //only draw the object if it collides with the map to prevent unneccessary clutter
                if(a!=null&&
                        a.isCollision(maps.getViewX(),maps.getViewY(),maps.getMapWidth(),maps.getMapHeight())) {

                    int w = a.getObjWidth();
                    int h = a.getObjHeight();

                    a.setObjWidth(w*maps.getTileWidth());
                    a.setObjHeight(h*maps.getTileHeight());

                    a.drawobj(gg, maps);



                    a.setObjWidth(w);
                    a.setObjHeight(h);
                }
            }
        }
    }


    protected Map getGameMap() {
    return gameMap;
    }

    protected void setGameMap(Map gameMap) {
        this.gameMap = gameMap;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }
}
