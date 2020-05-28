package sample;

import java.awt.*;
import java.util.ArrayList;

public class Frog extends  Plane {


    private int energy = 100;

    private int defaultEnergy;

    private Food targetPellet;


    /**
     * @param posX
     * @param posY
     * @param objWidth
     * @param objHeight
     * @param defaultHSpeed
     * @param defaultVSpeed
     * @param c
     * @param name
     * @param sight
     * @param projwidth
     * @param projheight
     * @param health
     */
    public Frog(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed,
                int defaultVSpeed, Color c, String name, int sight, int projwidth, int projheight, int health) {

        super(posX,posY, objWidth,objHeight, defaultHSpeed, defaultVSpeed, projwidth, projheight,health,name, false);

        super.setObjColour(c);
        Sight = sight;
    }



    static void drawFrog(Graphics2D gg, ArrayList<Frog> list, Map maps, boolean calcmovment) {

        //safety check
        if(list!=null){
            //Loop through each object in the arraylist
            for(Frog a: list){
                //  a.setUp_Image(defaultCoal.getUp_Image());
                //only draw the object if it collides with the map to prevent unneccessary clutter
                if(a!=null&&
                        a.isCollision(maps.getViewX(),maps.getViewY(),maps.getMapWidth(),maps.getMapHeight())) {
                    int w = a.getObjWidth();
                    int h = a.getObjHeight();

                    if(calcmovment)a.calcMovement();

                    a.setObjHeight(h*maps.getTileHeight());
                    a.setObjWidth(w*maps.getTileWidth());

                    //then if they exceeded any border then loop them around the value
                    a.PlaneBorderTest(a, true, maps);

                    a.drawobj(gg, maps);

                    a.setObjWidth(w);
                    a.setObjHeight(h);
                }
            }
        }
    }

    private int Sight;

    private int foodSupply;

    private String name;

    public void increaseFoodSupply(int additionalFood){
        foodSupply+=additionalFood;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getFoodSupply() {
        return foodSupply;
    }

    public void setFoodSupply(int foodSupply) {
        this.foodSupply = foodSupply;
    }


    public int getSight() {
        return Sight;
    }

    public void setSight(int sight) {
        Sight = sight;
    }


    public Food getTargetPellet() {
        return targetPellet;
    }

    public void setTargetPellet(Food targetPellet) {
        this.targetPellet = targetPellet;
    }

    public int getDefaultEnergy() {
        return defaultEnergy;
    }

    public void setDefaultEnergy(int defaultEnergy) {
        this.defaultEnergy = defaultEnergy;
    }
}
