package sample;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.abs;


/**
 * This is a class to be used as a parent class for sub classes such as
 * for a 'player', 'projectile', and any other moving objects in the game
 */
public class MovingObject extends SolidObject{

    //default speed of the plane
    private double defaultHSpeed; //horizontally
    private double defaultVSpeed; //vertically

    //the current actual value of the object speed
    private double objHSpeed; //the HORIZONTAL OR THE LEFT / RIGHT MOTION OF THE CRAFT
    private double objVSpeed; //THE VERTICAL OR THE UP / DOWN MOTION OF THE CRAFT

    // used to track the previous position of the x value of the plane
    private double lastX;
    private double lastY;

    private boolean isGravityBound;//Boolean used later to determine if gravity pull should be applied


    private BufferedImage currentImage = null;

    //More logical constructor only asking for some values
    //Full constructor that on construction sets all values for the player

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     * @param objWidth the width of the object
     * @param objHeight the height of the object
     * @param defaultHSpeed sets the current H speed and stores the original H speed as default for calling later
     * @param defaultVSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     */
    public MovingObject(int posX, int posY, int objWidth, int objHeight,
                        int defaultHSpeed, int defaultVSpeed) {
        super(posX, posY, objWidth, objHeight, Color.BLACK);

        this.defaultHSpeed = defaultHSpeed;
        this.defaultVSpeed = defaultVSpeed;

        this.objHSpeed = defaultHSpeed;
        this.objVSpeed = defaultVSpeed;

    }

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     * @param objWidth the width of the object
     * @param objHeight the height of the object
     * @param defaultHSpeed sets the current H speed and stores the original H speed as default for calling later
     * @param defaultVSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     */
    public MovingObject(double posX, double posY, int objWidth, int objHeight,
                        double defaultHSpeed, double defaultVSpeed) {
        super(posX, posY, objWidth, objHeight, Color.BLACK);



        this.defaultHSpeed = defaultHSpeed;
        this.defaultVSpeed = defaultVSpeed;

        this.objHSpeed = defaultHSpeed;
        this.objVSpeed = defaultVSpeed;

    }

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     */
    public MovingObject(int posX, int posY) {
        super(posX, posY, 50, 50, Color.BLACK);

        this.defaultHSpeed = 0;
        this.defaultVSpeed = 0;

        this.objHSpeed = 0;
        this.objVSpeed = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    /**
     *
     * @param posX the top left corner of the object's x value
     * @param posY the top left corner y value
     * @param objWidth the width of the object
     * @param objHeight the height of the object
     * @param objHSpeed and sets defaultHSpeed as sets the current H speed and stores the original H speed as default for calling later
     * @param objVSpeed and sets defaultVSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     */
    public MovingObject(int objWidth, int objHeight, int posX, int posY, Color objColour,
                        BufferedImage r_Image, BufferedImage l_Image, BufferedImage up_Image, BufferedImage down_Image,
                        int objHSpeed, int objVSpeed, int health, boolean isGravityBound) {

        super(objWidth, objHeight, posX, posY, objColour, r_Image, l_Image, up_Image, down_Image);
        this.defaultHSpeed = objHSpeed;
        this.defaultVSpeed = objVSpeed;
        this.objHSpeed = objHSpeed;
        this.objVSpeed = objVSpeed;
        this.health = health;
        this.isGravityBound = isGravityBound;
    }

    /**based on current values of movement calculate the new values for the x,y
     and save the old x, y to the lastX, lastY respecitvely
     */
    public void calcMovement(){
        //save the x & y values in the lastX, last Y value spots
        lastX = super.getActualPosX();
        lastY = super.getActualPosX();

        //finally update the values of the x & y using the resepctive speeds in those directions
        super.setPosX(super.getActualPosX()+objHSpeed);
        super.setPosY( super.getActualPosY()+objVSpeed);

    }

    /**
     *
     * @param map - the map
     * @param x - the object to move towards
     * @param y - the y position of the object to move towards
     *
     * @param desiredActualSpeed the desired speed hypotentuse to set the speed of the interceptor
     *
     * There is an inherent variablity due to the nature of the calculation simply looping to the max and min of the hypotenuse by scalling with 1.001
     * but it seems to work so 'why fix it if it ain't broke'?
     */
    public void moveTowards(Map map, double x, double y, double desiredActualSpeed){
        moveTowards(map,new SolidObject(x,y,1,1,Color.BLACK), desiredActualSpeed);
    }

    /**
     *
     * @param map - the map
     * @param target - the object to move towards
     *  - the movingobject that is directed towards the target
     * @param desiredActualSpeed the desired speed hypotentuse to set the speed of the interceptor
     *
     * There is an inherent variablity due to the nature of the calculation simply looping to the max and min of the hypotenuse by scalling with 1.001
     * but it seems to work so 'why fix it if it ain't broke'?
     */
    public void moveTowards(Map map,SolidObject target, double desiredActualSpeed){
        moveTowards(map,target, this, desiredActualSpeed);
    }

    /**
     *
     * @param map - the map
     * @param target - the object to move towards
     * @param interceptor - the movingobject that is directed towards the target
     * @param desiredActualSpeed the desired speed hypotentuse to set the speed of the interceptor
     *
     * There is an inherent variablity due to the nature of the calculation simply looping to the max and min of the hypotenuse by scalling with 1.001
     * but it seems to work so 'why fix it if it ain't broke'?
     */
    public static void moveTowards(Map map,SolidObject target, MovingObject interceptor, double desiredActualSpeed){

        //TODO Re- Implement Move Towards

        /**
         * Basic Algrebra / Geometry here that took my an embarrassing  amt of time to solve
         *
         * what we do is use an angle theta and a speed limit (speed of weapon) to setup the projectile
         *
         * SOH CAH TOA (basic math here)
         * S= speed limit or the hypotenuse or the desiredActualSpeed
         * so that means:
         * VSpeed = sin(theta) * S
         * HSpeed = cos(theta) * S
         *
         * Theta is found by another basic equation:
         *
         * Theta = y1 - y2 / x1 - x2
         * or
         * targetY - InterceptorY / targetX - InterceptorX
         *
         */

        double YTheta = interceptor.getActualPosY() - target.getActualPosY();
        double XTheta = interceptor.getActualPosX() - target.getActualPosX();
        double Theta = YTheta / XTheta;
        Theta*=100;

        //-------------
        //
        //-------------

        interceptor.setObjHSpeed(   (Math.cos(Theta)    )   *   desiredActualSpeed  );

        interceptor.setObjVSpeed(   (Math.sin(Theta)    )  *   desiredActualSpeed   );

        System.out.println("Theta: "+Theta+" HSpeed: "+interceptor.getObjHSpeed()+ "VSpeed: "+interceptor.getObjVSpeed()+

                " cosine: "+Math.cos(Theta)+" sin: "+Math.sin(Theta)
                +" Target ("+ target.getActualPosX()+ ", "+target.getActualPosY()+")"
        );

    }


    public static void old_moveTowards(Map map,SolidObject target, MovingObject interceptor, double desiredActualSpeed){



        double HSpeed = 0;
        double VSpeed = 0;

        HSpeed = (target.getPosX() + map.getViewX())
                - (interceptor.getPosX() +
                (interceptor.getObjWidth() *
                        (map.getTileWidth()) / 2
                ));
        VSpeed =
                (target.getPosY() + map.getViewY())
                        - (
                        interceptor.getPosY()
                                + (interceptor.getObjHeight() *
                                (
                                        map.getTileHeight()) / 2
                        ));

        double marginPercent = 1.001;


        /*
         * slow down the speed of the projectile hypotenuse
         * until it is to the desired speed within the margin desired
         */



        while (
                (
                        Math.hypot(
                                HSpeed
                                ,
                                VSpeed)
                )
                        >
                        (
                                desiredActualSpeed
                        )
        ) {
            HSpeed /= (marginPercent);
            VSpeed /= (marginPercent);
        }
        while (
                (
                        Math.hypot(
                                HSpeed
                                ,
                                VSpeed)
                )
                        <
                        (
                                desiredActualSpeed
                        )
        ) {
            HSpeed *= (marginPercent);
            VSpeed *= (marginPercent);
        }

        interceptor.setObjVSpeed(VSpeed);
        interceptor.setObjHSpeed(HSpeed);
    }

    public int getEnergy() {
        return health;
    }

    public void setEnergy(int energy) {
        this.health = energy;
    }

    private int health;

    //Basic Getter's / Setters that simply set and or get the variable


    public double getDefaultHSpeed() {
        return defaultHSpeed;
    }

    public void setDefaultHSpeed(double defaultHSpeed) {
        this.defaultHSpeed = defaultHSpeed;
    }

    public double getDefaultVSpeed() {
        return  defaultVSpeed;
    }

    public void setDefaultVSpeed(double defaultVSpeed) {
        this.defaultVSpeed = defaultVSpeed;
    }

    public double getObjHSpeed() {
        return objHSpeed;
    }

    public void setObjHSpeed(double objHSpeed) {
        this.objHSpeed = objHSpeed;
    }

    public double getObjVSpeed() {
        return objVSpeed;
    }



    /**
     *Move towards the object at the defaualt speeds of the moving object
     * @param sam - target
     *            move this abject towards the same object
     */
    protected void moveTowardsobj(SolidObject sam, Map map){
        this.setObjVSpeed(0);
        this.setObjHSpeed(0);

        //UP
        if(this.isAbove(sam)){
            this.setObjVSpeed(
                    -abs(    this.getDefaultVSpeed() )
            );
        }
        else
            //DOWN
            if(this.isBelow(sam)){
                this.setObjVSpeed(
                        abs(    this.getDefaultVSpeed() )
                );
            }
        //LEFT
        if(this.isLeft(sam)){
            this.setObjHSpeed(
                    -abs(    this.getDefaultHSpeed() )
            );
        }
        else
            //RIGHT
            if(this.isRight(sam)){
                this.setObjHSpeed(
                        abs(    this.getDefaultHSpeed() )
                );
            }
    }




    @Override
    protected void drawobj(Graphics2D gg, Map maps){

        //default to the regular drawobj if the desired image is not initalized

        if(getUp_Image()!=null && getDown_Image()!=null

                &&getL_Image()!=null&&getR_Image()!=null){

            //draw the object moving up if the movement is upwards
            if(getObjVSpeed()<0){
                currentImage = getUp_Image();
                gg.drawImage(
                        super.getUp_Image(),
                        Math.round(super.getPosX()-maps.getViewX()),
                        Math.round(super.getPosY()- maps.getViewY()),
                        super.getObjWidth(), super.getObjHeight(),null);
            }
            //Draw the object downards if moving down
            else if(getObjVSpeed()>0){
                currentImage = super.getDown_Image();
                gg.drawImage(
                        super.getDown_Image(),
                        Math.round(super.getPosX()-maps.getViewX()),
                        Math.round(super.getPosY()- maps.getViewY()),
                        super.getObjWidth(), super.getObjHeight(),null);
            }

            //ONLY draw the image moving left/Right if the up/down motion is 0
            if(getObjVSpeed()==0){

                //Draw the image moving right
                if(getObjHSpeed()>0){
                    currentImage = super.getR_Image();
                    gg.drawImage(super.getR_Image(),Math.round(super.getPosX()-maps.getViewX()),
                            Math.round(super.getPosY()- maps.getViewY()),
                            super.getObjWidth(), super.getObjHeight(),null);
                }
                else if(getObjHSpeed()<0) //Draw the image moving left
                {
                    currentImage = super.getL_Image();
                    gg.drawImage(super.getL_Image(), Math.round(super.getPosX() - maps.getViewX()),
                            Math.round(super.getPosY() - maps.getViewY()),
                            super.getObjWidth(), super.getObjHeight(), null);
                }
            }

            if(getObjVSpeed()==0&&getObjHSpeed()==0){
                if(currentImage!=null) {
                    gg.drawImage(currentImage, Math.round(super.getPosX() - maps.getViewX()),
                            Math.round(super.getPosY() - maps.getViewY()),
                            super.getObjWidth(), super.getObjHeight(), null);
                }
                else{
                    gg.drawImage(super.getUp_Image(), Math.round(super.getPosX() - maps.getViewX()),
                            Math.round(super.getPosY() - maps.getViewY()),
                            super.getObjWidth(), super.getObjHeight(), null);
                }
            }

        }
        else{
            super.drawobj(gg,maps);
        }
    }

    public boolean isStationary(){
        return (abs(objVSpeed)+abs(objHSpeed))<0;
    }

    public void setObjVSpeed(double objVSpeed) {
        this.objVSpeed = objVSpeed;
    }

    public int getLastX() {
        return (int)Math.round(lastX);
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {

        return (int)Math.round(lastY);
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    /**
     * Reverse the direction of the object both VSpeed and HSpeed using the default values
     */
    public void reverseDirection(){
        if(objVSpeed>0){
            objVSpeed = -abs(defaultVSpeed);
        }
        else if(objVSpeed< 0){
            objVSpeed = abs(defaultVSpeed);
        }

        if(objHSpeed>0){
            objHSpeed = -abs(defaultHSpeed);
        }
        else if(objVSpeed< 0){
            objHSpeed = abs(defaultHSpeed);
        }

    }


    public boolean isGravityBound() {
        return isGravityBound;
    }

    public void setGravityBound(boolean gravityBound) {
        isGravityBound = gravityBound;
    }
}
