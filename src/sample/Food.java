package sample;
import java.awt.*;
import java.util.ArrayList;

public class Food extends SolidObject{


    public Food(int posX, int posY, int objWidth, int objHeight, Color objColour) {
        super(posX, posY, objWidth, objHeight, objColour);
    }


    protected static void drawFood(Graphics2D gg, ArrayList<Food> list, Map maps){
        //safety check
        if(list!=null){
            //Loop through each object in the arraylist
            for(Food a: list){
                //  a.setUp_Image(defaultCoal.getUp_Image());
                //only draw the object if it collides with the map to prevent unneccessary clutter
                if(a!=null&&
                        a.isCollision(maps.getViewX(),maps.getViewY(),maps.getMapWidth(),maps.getMapHeight())) {
                    //int w = a.getObjWidth();
                    //int h = a.getObjHeight();

                    a.setObjHeight(maps.getTileHeight());
                    a.setObjWidth(maps.getTileWidth());

                    a.drawobj(gg, maps);

                    //a.setObjWidth(w);
                    //a.setObjHeight(h);
                }
            }
        }
    }
}
