package sample;

import java.awt.*;
import java.util.ArrayList;

public class Plane extends MovingObject {

    //these track the players cooldown to when they can next fire
    private int fireCooldown =0;

    private int FIRECOOLDOWN = 0;

    private int altFireCooldown =0;
    private int ALTFIRECOOLDOWN = 0;

    private int defaultProjectileHeight=25;
    private int DefaultProjectileWidth =15;

    private int health = 100;

    private String name;


    private ArrayList<Projectile> projectileArrayList;

    /**
     * @param posX - the X position of the top left of the square
     * @param posY - the Y position of the top left of the square
     * @param objWidth +- the width
     * @param objHeight height
     * @param defaultHSpeed the H value that is referenced as how fast it can/ should be going
     * @param defaultVSpeed the V value that is referenced as how fast it can/ should be going
     *
     * @param health the health of the player
     * @param name the name of the player
     *
     * @param FIRECOOLDOWN The default value reference to stop the player from spamming the fire button
     * @param ALTFIRECOOLDOWN the default value reference to stop the player from spamming the alt fire button
     * @param defaultProjectileHeight the reference height for when the plane fires a projectile
     * @param defaultProjectileWidth the reference width of a fired projectile
     * @param isGravityBound
     */
    public Plane(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed, int defaultVSpeed,
                 int health, String name,

                 int FIRECOOLDOWN, int ALTFIRECOOLDOWN, int defaultProjectileHeight, int defaultProjectileWidth, boolean isGravityBound) {

        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);

        this.FIRECOOLDOWN = FIRECOOLDOWN;
        this.ALTFIRECOOLDOWN = ALTFIRECOOLDOWN;
        this.defaultProjectileHeight = defaultProjectileHeight;
        this.DefaultProjectileWidth = defaultProjectileWidth;
        this.health = health;
        this.name = name;

        this.projectileArrayList = new ArrayList<>();

        this.setGravityBound(isGravityBound);
    }


    //Full constructor that on construction sets all values for the player

    /**
     *
     * @param posX - the X position of the top left of the square
     * @param posY - the Y position of the top left of the square
     * @param objWidth - the width
     * @param objHeight height
     * @param defaultHSpeed the H value that is referenced as how fast it can/ should be going
     * @param defaultVSpeed the V value that is referenced as how fast it can/ should be going
     */
    public Plane(int posX, int posY, int objWidth, int objHeight,
                 int defaultHSpeed, int defaultVSpeed,
                 Color c, String name, boolean isGravityBound

    ) {

        super(posX,posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
        super.setObjColour(c);

        this.name = name;

        this.setGravityBound(isGravityBound);
    }


    /**
     *  //Lazy constructor with some default values
     * @param posX - the X position of the top left of the square
     * @param posY - the Y position of the top left of the square
     *
     * @param buttonUp The KeyBoard numerical reference key for input
     * @param buttonDown The KeyBoard numerical reference key for input
     * @param buttonLeft The KeyBoard numerical reference key for input
     *                   */
    public Plane(int posX, int posY, int buttonUp, int buttonDown,
                 int buttonLeft, int buttonRight) {

        super(50,50, 1, 1, 0, 0);
    }

    /**
     *
     * @param posX - the X position of the top left of the square
     * @param posY - the Y position of the top left of the square
     * @param objWidth - the width
     * @param objHeight height
     * @param defaultHSpeed the H value that is referenced as how fast it can/ should be going
     * @param defaultVSpeed the V value that is referenced as how fast it can/ should be going
     *
     */
    public Plane(int posX, int posY, int objWidth, int objHeight,
                 int defaultHSpeed, int defaultVSpeed, int defaultProjectileHeight, int defaultProjectileWidth,
                 int health, String name, boolean isGravityBound) {
        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed);
        this.defaultProjectileHeight = defaultProjectileHeight;
        DefaultProjectileWidth = defaultProjectileWidth;
        this.health = health;
        this.name = name;

        this.setGravityBound(isGravityBound);
    }

    //This is used to move the cooldowns slowly to 0
    public void calcCooldowns(){
        if(fireCooldown>0)fireCooldown --;
        if(altFireCooldown >0) altFireCooldown--;
    }

    //start the fire button cooldown
    public void fireCooldownStart(){
        fireCooldown = FIRECOOLDOWN;
    }
    //start the bomb button cooldown
    public void bombCooldownStart(){
        altFireCooldown = ALTFIRECOOLDOWN;
    }


    /**
     * @param john the movingobject that is tested
     * @param reverseDirection if true the object will be set to reverse direction
     * @return if a border was reached
     *
     * //use the given player object and calculate and adjust if the player is going to exceed the borders and then move the player to the
     *         //oppisite side of the window
     *
     *                      --------Area: B------
     *         (0,0)                                    (x,0)
     *     I                                                    I
     *  Area: A                                                  Area: D
     *    I                                                      I
     *    I                                                      I
     *    I                                                      I
     *    I                                                      I
     *    I                                                      I
     *    I                                                      I
     *          (0,y)                                   (x,y)
     *                               --------Area: C------
     */

    protected boolean PlaneBorderTest(MovingObject john, boolean reverseDirection, Map gameMap){

        //Test Area A to determine if a horizonal adjustment is needed

        if(inArea_A(john)){

            //reverse object motion if reverseDirection is true

            if(reverseDirection){
                john.setObjHSpeed(-john.getObjHSpeed());
            }

            john.setPosX(1);

        }

        //Test Area D to determine if a horizonal adjustment is needed

        if(inArea_D(john,gameMap)){

            //reverse object motion if reverseDirection is true

            if(reverseDirection){
                john.setObjHSpeed(-john.getObjHSpeed());
            }

            john.setPosX(
                    gameMap.getMapWidth() -
                            (   1+(john.getObjWidth()*gameMap.getTileWidth())   )

            );

        }

        //Test Area B to determine if a Vertical adjustment is needed

        if(inArea_B(john)){

            //reverse object motion if reverseDirection is true

            if(reverseDirection){
                john.setObjVSpeed(-john.getObjVSpeed());
            }

            john.setPosY(1);

        }

        //Test Area C to determine if a Vertical adjustment is needed

        if(inArea_C(john,gameMap)){

            //reverse object motion if reverseDirection is true

            if(reverseDirection){
                john.setObjVSpeed(-john.getObjVSpeed());
            }

            john.setPosY(   gameMap.getMapHeight()  -
                    (john.getObjHeight()*gameMap.getTileHeight())
            );

        }



        return false;

    }

    /**
     * see Diagram in notes for PlaneBorderTest to see area noted for testing of border exceeding
     * @param john - object noted for testing
     * @return true only when john is not within the gameMap's borders
     *
     * ***Assumption made map position is 0,0 then extends only in a positive direction
     */
    private boolean inArea_A(MovingObject john){

        if(john.getActualPosX()<0){

            return true;

        }



        return false;
    }


    /**
     * see Diagram in notes for PlaneBorderTest to see area noted for testing of border exceeding
     * @param john - object noted for testing
     * @return true only when john is not within the gameMap's borders
     *
     *  ***Assumption made map position is 0,0 then extends only in a positive direction
     */

    private boolean inArea_B(MovingObject john){


        if(john.getActualPosY()<0){

            return true;

        }


        return false;
    }



    /**
     * see Diagram in notes for PlaneBorderTest to see area noted for testing of border exceeding
     * @param john - object noted for testing
     * @param gameMap d
     * @return true only when john is not within the gameMap's borders
     *
     *  ***Assumption made map position is 0,0 then extends only in a positive direction
     */
    private boolean inArea_C(MovingObject john, Map gameMap){

        if(
                (   john.getActualPosY()    +   (john.getObjHeight()*gameMap.getTileHeight()) )
                        >
                        gameMap.getMapHeight()
        ){

            return true;

        }

        return false;
    }

    /**
     * see Diagram in notes for PlaneBorderTest to see area noted for testing of border exceeding
     * @param john - object noted for testing
     * @param gameMap d
     * @return true only when john is not within the gameMap's borders
     *
     *  ***Assumption made map position is 0,0 then extends only in a positive direction
     */
    private boolean inArea_D(MovingObject john, Map gameMap){

        if(
                (   john.getActualPosX()    +  ( john.getObjWidth()    *gameMap.getTileWidth()))
                        >
                        gameMap.getMapWidth()
        ){

            return true;

        }


        return false;
    }


    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }



    protected int getFireCooldown() {
        return fireCooldown;
    }

    protected void setFireCooldown(int fireCooldown) {
        this.fireCooldown = fireCooldown;
    }

    protected void setFIRECOOLDOWN(int FIRECOOLDOWN) {
        this.FIRECOOLDOWN = FIRECOOLDOWN;
    }

    protected int getAltFireCooldown() {
        return altFireCooldown;
    }

    protected void setAltFireCooldown(int altFireCooldown) {
        this.altFireCooldown = altFireCooldown;
    }


    protected void setALTFIRECOOLDOWN(int ALTFIRECOOLDOWN) {
        this.ALTFIRECOOLDOWN = ALTFIRECOOLDOWN;
    }

    protected int getDefaultProjectileHeight() {
        return defaultProjectileHeight;
    }

    protected void setDefaultProjectileHeight(int defaultProjectileHeight) {
        this.defaultProjectileHeight = defaultProjectileHeight;
    }

    protected int getDefaultProjectileWidth() {
        return DefaultProjectileWidth;
    }

    protected void setDefaultProjectileWidth(int defaultProjectileWidth) {
        DefaultProjectileWidth = defaultProjectileWidth;
    }

    protected ArrayList<Projectile> getProjectileArrayList() {
        return projectileArrayList;
    }

    protected void setProjectileArrayList(ArrayList<Projectile> projectileArrayList) {
        this.projectileArrayList = projectileArrayList;
    }
}
