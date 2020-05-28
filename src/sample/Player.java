package sample;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Player extends Plane {

    //the keyboard value associated with each of the following movment / input types
    private int buttonUp; //UP
    private int buttonDown; //DOWN
    private int buttonLeft; //LEFT
    private int buttonRight; //RIGHT
    private int buttonSprint = 16;//SPRINT

    private Point mouseLocation; // used to track the players mouse position

    private int killCount; // used to track the bloody massacre of the player

    //Used in the implenetation + tracking of a sprint function
    private boolean isRunning; // tracks if the player is currently sprinting
    private int MAXSPRINTSPEED=100; // the full 'bar' / amt of sprint the player has; (called to display max sprint value)
    private double currentSprintCount = MAXSPRINTSPEED;// used to track the actual sprint value (called to display current sprint value)
    private double SprintIncrease = 2;//the cost of sprinting value used
    private double SprintRecoverySpeed = .20;//the value assigned to recovery of the sprint


    //0 - Up
    //1 - Right
    //2 - Down
    //3 - Left
    private int direction;//this defines which way the building's will be placed in


    /**
     * Used to track the various items in the player bar
     */
    private ArrayList<BeltSlot> beltList;

    /**
     * This is default to te first item in an instatiated beltlist but is the referenced tile when the player will
     * select to make a tile
     * if the player uses one of the keycmds in the beltlist to change the selected item then it should change to that different ite
     */
    private BeltSlot selectedItem;
    /**
     *
     * @param posX - the X position of the top left of the square
     * @param posY - the Y position of the top left of the square
     * @param objWidth - the width
     * @param objHeight height
     * @param defaultHSpeed the H value that is referenced as how fast it can/ should be going
     * @param defaultVSpeed the V value that is referenced as how fast it can/ should be going
     *
     * @param buttonUp
     * @param buttonDown
     * @param buttonLeft
     * @param buttonRight
     *
     * @param health the health of the player
     * @param name the name of the player
     *
     * @param beltList
     */
    public Player(int posX, int posY, int objWidth, int objHeight, int defaultHSpeed,
                  int defaultVSpeed, int buttonUp, int buttonDown, int buttonLeft, int buttonRight, int health,
                  String name
            ,ArrayList<BeltSlot> beltList, boolean isGravityBound
    ){

        super(posX, posY, objWidth, objHeight, defaultHSpeed, defaultVSpeed, health, name, 0,
                0, 0, 0, isGravityBound);

        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.buttonLeft = buttonLeft;
        this.buttonRight = buttonRight;



        this.beltList = beltList;
        selectedItem = null;

    }

    /**
     * @param m - a mouse input
     *          <p>
     *          properly handle a mouse click
     */
    protected Building calcBuildingMouseClick(MouseEvent m, Map maps) {


        //compensate the map view position to get the actual position of the mouse click in connection to the view
        int posX = m.getX() + maps.getViewX();
        int posY = m.getY() + maps.getViewY();

        //determine what checkerboard position was ch
        SolidObject sub_output = maps.CollidedTile(posX, posY, maps);
        Building output = null;

        //then convert the output to an object
        if (selectedItem != null ) {
            output = selectedItem.getDefaultBuilding();

            output.setPosX(sub_output.getPosX());
            output.setPosY(sub_output.getPosY());

            output.setObjWidth(
                    selectedItem.getDefaultBuilding().getObjWidth());

            output.setObjHeight(
                    selectedItem.getDefaultBuilding().getObjHeight());

            output.setObjColour(selectedItem.getDefaultBuilding().getObjColour());


            //1 - Right
            if(direction==1){
                output.setObjVSpeed(0);
                output.setObjHSpeed(1);
            }
            //2 - Down
            else if(direction==2){
                output.setObjVSpeed(1);
                output.setObjHSpeed(0);
            }
            //3 - Left
            else if(direction==3){
                output.setObjVSpeed(0);
                output.setObjHSpeed(-1);
            }

            //Set the direction of the building
            //0 - Up
            else {
                output.setObjVSpeed(-1);
                output.setObjHSpeed(0);
            }
        }

        return output;
    }


    protected void UpdateBelt(Map map){
        if(beltList!=null)for(BeltSlot a: beltList)a.getDefaultBuilding().setGameMap(map);
    }


    /**
     * If any of the players weapons are activated then fire towards the mouse target
     * @param pointy
     */
    private void calcPlayerFire(Point pointy, Map map){
    }

    /**
     * Given a
     * @param gg - to draw on
     * Draw relevant UI elements
     */
    protected void drawUI(Graphics2D gg, Map maps){

        //drawMouseLocation(gg);

        drawHealthBar(gg, maps);
        drawBelt(gg, maps);

        drawKillCount(gg);

        drawSprintBar(gg,maps);

    }

    /**
     * Draw on the
     * @param gg - 2d Graphics that the belt is drawn on
     */
    protected void drawBelt(Graphics2D gg, Map maps){
        if(beltList!=null)drawBuildingsBelt(gg,maps);

    }

    /**
     * Draw on the
     * @param gg - 2d Graphics that the belt is drawn on
     */
    protected void drawBuildingsBelt(Graphics2D gg, Map maps){
        if(beltList!=null){
            int beltItems = beltList.size();

            int beltheight = 60;
            int beltwidth = beltItems*60;

            int beltx =  (maps.getViewWidth()) - (beltwidth);
            int belty = maps.getViewHeight()-(beltheight+(beltheight/5));

            //draw each indidual box in the belt

            int inset = 5;//this is used to have something of a 'shadow' for the belt

            int itemWidth = beltwidth / beltItems;

            int itemX = beltx;
            int position =0;

            //draw up to 9 boxes that should each have a keyboard listener
            //and a cooresponding action in arraylists

            gg.setColor(new Color(
                    64, 17, 0
            ));

            gg.fillRect(itemX - (inset)
                    , belty - (inset),
                    beltwidth + (inset * 2), beltheight + (inset * 2));


            gg.setColor(new Color(0,0,0));

            gg.fillRect(beltx,belty,beltwidth,beltheight);



            if(beltList!=null){
                for(BeltSlot a: beltList){
                    if(a!=null){
                        if(a!=selectedItem) {
                            gg.setColor(a.getDefaultBuilding().getObjColour());

                            gg.fillRect(itemX + inset, belty + inset,
                                    itemWidth - (inset * 2), beltheight - (inset * 2));

                            //Try to draw the image directionally but if fails check to ensure that up is initalized and default to up
                            //Draw the object going Down
                            if(a.getDefaultBuilding().getDown_Image()!=null&&
                                    direction ==2
                            )
                                gg.drawImage(a.getDefaultBuilding().getDown_Image(),itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);

                                //Draw the object going Right
                            else if(a.getDefaultBuilding().getR_Image()!=null&&
                                    direction == 1
                            ) {
                                gg.drawImage(a.getDefaultBuilding().getR_Image(), itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }
                            //Draw the object going LEFT
                            else if(a.getDefaultBuilding().getL_Image()!=null&&
                                    direction==3
                            ) {
                                gg.drawImage(a.getDefaultBuilding().getL_Image(), itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }


                            //Draw the image inside the box as a representative
                            else if(a.getDefaultBuilding().getUp_Image()!=null){
                                gg.drawImage(a.getDefaultBuilding().getUp_Image(),itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }


                        }
                        else{
                            //graphically show which item is the selected item
                            gg.setColor(Color.yellow);
                            gg.fillRect(itemX, belty,
                                    itemWidth, beltheight);

                            gg.setColor(a.getDefaultBuilding().getObjColour());
                            a.DrawBeltItem(gg,itemX + inset, belty + inset,
                                    itemWidth - (inset * 2), beltheight - (inset * 2));

                            //Try to draw the image directionally but if fails check to ensure that up is initalized and default to up
                            //Draw the object going Down
                            if(a.getDefaultBuilding().getDown_Image()!=null&&
                                    direction ==2
                            )
                                gg.drawImage(a.getDefaultBuilding().getDown_Image(),itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);

                                //Draw the object going Right
                            else if(a.getDefaultBuilding().getR_Image()!=null&&
                                    direction == 1
                            ) {
                                gg.drawImage(a.getDefaultBuilding().getR_Image(), itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }
                            //Draw the object going LEFT
                            else if(a.getDefaultBuilding().getL_Image()!=null&&
                                    direction==3
                            ) {
                                gg.drawImage(a.getDefaultBuilding().getL_Image(), itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }


                            //Draw the image inside the box as a representative
                            else if(a.getDefaultBuilding().getUp_Image()!=null){
                                gg.drawImage(a.getDefaultBuilding().getUp_Image(),itemX + inset, belty + inset,
                                        itemWidth - (inset * 2), beltheight - (inset * 2), null);
                            }

                        }
                        position++;

                        itemX+=itemWidth;
                    }
                }

                /**
                 * This line only runs in the event that at the end of the belt list thtere is unfilled items in comparision to the
                 * belt size
                 */
                for(int i = position; i < beltItems; i++){

                    gg.setColor(Color.lightGray);
                    gg.fillRect(itemX+inset, belty+inset,
                            itemWidth-(inset*2),beltheight-(inset*2));
                    itemX+=itemWidth;
                }
            }}}




    private void drawMouseLocation(Graphics2D gg){
        if(mouseLocation!=null){

            int mouseX = (mouseLocation.x);
            int mouseY = (mouseLocation.y);

            int reticalSize = 20;

            Color recticalColour = new Color(255, 0, 17);

            gg.setColor(recticalColour);

            gg.drawOval(mouseX-reticalSize,
                    mouseY-reticalSize,
                    reticalSize*2
                    ,reticalSize*2
            );



        }
    }



    /**
     *
     * @return an arraylist containing the defaulted belt list of the player's belt
     */
    protected static ArrayList<BeltSlot> intializeBelt(){

        ArrayList<BeltSlot> list = new ArrayList<>();

        list.add(new BeltSlot( 49, new Color(255, 15, 0), null,false));

        list.add(new BeltSlot(50, new Color(57, 255, 0), null,true));

        list.add(new BeltSlot(51, new Color(255, 24, 217) , null,false));

        list.add(new BeltSlot(52, new Color(70, 255, 184),null,false ));

        list.add(new BeltSlot(53, new Color(109, 121, 255), null,false));

        return list;
    }


    //given a 2d Graphics graw the object in reference to viewX and viewY of the MapView
    //Eg: object 0,0 and the mapview 1,1
    //draw the object @ -1,-1
    //Additionally scale the object in comparision to the width and height of the map view to allow objects to appear
    //to get bigger / smaller as the zoom changes


    /**
     * @param gg - graphics to draw on
     * Draw the health of the player
     */
    private void drawHealthBar(Graphics2D gg, Map maps){

        //this is the assumption of how large the player health can be
        int maxhealth = 100;
        int healthSizeIncreaser = 2;

        //The position of the health bar
        int x =  maps.getViewWidth() - (50+(maxhealth*healthSizeIncreaser));
        int y = 75;

        int height = 25;

        //draw the background for the health bar
        gg.setColor(Color. black);
        gg.fillRect(x,y,maxhealth*healthSizeIncreaser,height);

        //Draw the actual health
        gg.setColor(Color.red);
        gg.fillRect(x,y,super.getHealth()*healthSizeIncreaser,height);

        if(super.getObjColour().equals(Color.black))gg.setColor(Color.white);
        else{
            super.setObjColour(Color.black);
        }

        gg.setFont(new Font("TimesRoman", Font.PLAIN,
                (height/
                        (2*healthSizeIncreaser)
                )
                        +height
        ));

        gg.drawString("Health", x, y+height);


    }


    /**
     * @param gg - graphics to draw on
     * Draw the health of the player
     */
    private void drawSprintBar(Graphics2D gg, Map maps){

        //this is the assumption of how large the player health can be
        int SizeIncreaser = 2;

        int height = 20;

        //The position of the health bar
        int x =  maps.getViewWidth() - (50+(MAXSPRINTSPEED*SizeIncreaser));
        int y = 75+35;

        //draw the background for the health bar
        gg.setColor(Color. white);
        gg.fillRect(x,y,
                (
                        (int)Math.round(MAXSPRINTSPEED)
                )
                        *SizeIncreaser,height);

        //Draw the actual health
        gg.setColor(Color.yellow);
        gg.fillRect(x,y,
                (
                        (int)Math.round(currentSprintCount)
                )
                        *SizeIncreaser,height);

        if(super.getObjColour().equals(Color.black))gg.setColor(Color.white);
        else{
            super.setObjColour(Color.black);
        }

        gg.setFont(new Font("TimesRoman", Font.PLAIN,
                (height/
                        (2*SizeIncreaser)
                )
                        +height
        ));

        gg.setColor(Color.black);

        gg.drawString("Stamina"
                , x, y+height);


    }


    protected void drawKillCount(Graphics2D gg){

        int height = 25;

        int width = 25;

        int x = width *2;
        int y = height*2;

        String description = "KillCount: ";

        String value= " "+this.getKillCount();

        gg.setColor(Color.red);

        gg.setFont(new Font("TimesRoman", Font.PLAIN,
                (height
                        +height
                )/2)
        );

        gg.drawString(
                description+value
                , x, y+height);






    }

    /**
     * This is used to calculate if and to process the updating of the selcted belt item
     * @param e
     */
    protected void BeltItemKeyEvent(KeyEvent e){
        if(e!=null) {
            int key = e.getKeyCode();

            if (beltList != null) {

                for (BeltSlot a : beltList) {

                    if (a != null
                            && key == a.getKeyCmd()) {
                        selectedItem = a;

                    }
                }
            }
        }
    }


    /**
     *
     * @param gg
     * @param list
     * @param maps
     * @param calcmovment
     * @param pointy
     */
    protected static void drawPlayers(Graphics2D gg, ArrayList<Player> list,
                                      Map maps, boolean calcmovment, Point pointy){

        //safety check
        if(list!=null){
            //Loop through each object in the arraylist
            for(Player a: list){
                //  a.setUp_Image(defaultCoal.getUp_ImageF());
                //only draw the object if it collides with the map to prevent unneccessary clutter
                if(a!=null){


                    //then if they exceeded any border then loop them around the value
                    a.PlaneBorderTest(a, false, maps);

                    //Only actually move the player if movement is enabled
                    if(calcmovment)a.calcMovement();

                    //only draw the player object if it is actually in the map view
                    if(  a.isCollision( maps.getViewX(),
                            maps.getViewY(),    maps.getMapWidth(),
                            maps.getMapHeight())
                    ) {

                        int w = a.getObjWidth();
                        int h = a.getObjHeight();

                        //Draw the player in accordance to the map tile sizes to allow scaling of the visual size
                        a.setObjHeight(h*maps.getTileHeight());
                        a.setObjWidth(w*maps.getTileWidth());

                        a.drawobj(gg, maps);

                        a.setObjWidth(w);
                        a.setObjHeight(h);
                    }


                }}
        }
    }

    @Override
    public void calcMovement(){

        if(isRunning
        ){

            double H = this.getObjHSpeed();
            double V = this.getObjVSpeed();

            this.setObjVSpeed(V*getSprintIncrease());
            this.setObjHSpeed(H*getSprintIncrease());
            super.calcMovement();

            this.setObjVSpeed(V);
            this.setObjHSpeed(H);

            currentSprintCount-=-.01;

            recoverSprint();
        }
        else{
            super.calcMovement();
            recoverSprint();
        }


    }

    protected void recoverSprint(){


        if(super.getObjVSpeed()==0&&super.getObjHSpeed()==0
        ){
            currentSprintCount += abs(SprintRecoverySpeed);
        }

        //standard safety checks to not exceed boundries of max and min
        if(currentSprintCount<0)currentSprintCount=0;
        if(currentSprintCount>MAXSPRINTSPEED)currentSprintCount=MAXSPRINTSPEED;

    }


    /**
     * Jump Function to prevent unsupported jumps
     * ie: jumping off air
     *
     * This function will ascertain if the player is either
     * 1. on the map floor
     * 2. on an object
     *
     * and only then will perform the jump command if one or both of the above instances are met
     */
    private void movePlayerUp(Map gameMap){

        //now a proper jump may occur
        this.setObjVSpeed(
                -abs(this.getDefaultVSpeed()
                ));


    }

    /**
     * Given e handle any relevant action that should occur with the players
     * @param e - Keyevent
     *
     * calls ArrayList PlayerList<Plane>
     *
     * This will move the plane on a speed fom the planes default speed value
     *
     */
    protected static void calcPlayerReleasedInput(KeyEvent e, ArrayList<Player> playerList){

        int key = e.getKeyCode();

        /**
         * Loop through each plane in the arraylist and handle the relevant action if any match each individuals list of actions
         */

        for(Player self: playerList){

            //UP Key
            if(self.getButtonUp()==key){
                //handle moving the plane / player in the requested direction
                //Move the player up by negativify an absolute of the default value
                self.setObjVSpeed(0);
            }

            else //DOWN Key
                if(self.getButtonDown()==key){
                    //handle moving the plane / player in the requested direction
                    //Move the player up by absolute of the default value

                    self.setObjVSpeed(0);
                }


                else //LEFT Key
                    if(self.getButtonLeft()==key){
                        //handle moving the plane / player in the requested direction
                        //Move the player left by negativify an absolute of the default value

                        self.setObjHSpeed(0);
                    }
                    else //RIGHT Key
                        if(self.getButtonRight()==key){
                            //handle moving the plane / player in the requested direction
                            //Move the player right an absolute of the default speed value

                            self.setObjHSpeed(0);
                        }

                        //SPRINTING
                        else if(self.getButtonSprint()==key){
                            self.setRunning(false);
                        }

            self.calcMovement();
        }

    }

    protected void calcPlayerPressedKey(KeyEvent e, Map gameMap){

        int key = e.getKeyCode();


        //calcplayer belt selection changes
        this.BeltItemKeyEvent(e);

        //UP Key
        if (this.getButtonUp() == key) {

            //call the jump fuction which factors in a number of points and decides if jumping is appropriate
            movePlayerUp(gameMap);


        } else //DOWN Key
            if (this.getButtonDown() == key) {
                //handle moving the plane / player in the requested direction
                //Move the player up by absolute of the default value

                this.setObjVSpeed(
                        abs(this.getDefaultVSpeed()
                        ));
            } else //LEFT Key
                if (this.getButtonLeft() == key) {
                    //handle moving the plane / player in the requested direction
                    //Move the player left by negativify an absolute of the default value

                    this.setObjHSpeed(
                            -abs(this.getDefaultHSpeed()
                            ));
                } else //RIGHT Key
                    if (this.getButtonRight() == key) {
                        //handle moving the plane / player in the requested direction
                        //Move the player right an absolute of the default speed value

                        this.setObjHSpeed(
                                abs(this.getDefaultHSpeed()
                                ));}

                    //SPRINTING
                    else if(this.getButtonSprint()==key){

                        this.setRunning(true);
                    }

    }




    protected void setDirection(int direction) {
        this.direction = direction;

        if(direction>3)direction=0;//loop direction back around

    }

    /**
     * moves direction to the right
     */
    protected void rotateRight(){
        direction++;
        if(direction>3)direction=0;
    }


    protected void addOneKill(){
        killCount++;
    }

    protected ArrayList<BeltSlot> getBeltList() {
        return beltList;
    }

    protected void setBeltList(ArrayList<BeltSlot> beltList) {
        this.beltList = beltList;
    }

    protected BeltSlot getSelectedItem() {
        return selectedItem;
    }

    protected void setSelectedItem(BeltSlot selectedItem) {
        this.selectedItem = selectedItem;
    }

    protected int getButtonUp() {
        return buttonUp;
    }

    protected void setButtonUp(int buttonUp) {
        this.buttonUp = buttonUp;
    }

    protected int getButtonDown() {
        return buttonDown;
    }

    protected void setButtonDown(int buttonDown) {
        this.buttonDown = buttonDown;
    }

    protected int getButtonLeft() {
        return buttonLeft;
    }

    protected void setButtonLeft(int buttonLeft) {
        this.buttonLeft = buttonLeft;
    }

    protected int getButtonRight() {
        return buttonRight;
    }

    protected void setButtonRight(int buttonRight) {
        this.buttonRight = buttonRight;
    }

    protected int getDirection() {
        return direction;
    }


    public Point getMouseLocation() {
        return mouseLocation;
    }

    public void setMouseLocation(Point mouseLocation) {
        this.mouseLocation = mouseLocation;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getMAXSPRINTSPEED() {
        return MAXSPRINTSPEED;
    }

    public void setMAXSPRINTSPEED(int MAXSPRINTSPEED) {
        this.MAXSPRINTSPEED = MAXSPRINTSPEED;
    }

    public double getCurrentSprintCount() {
        return currentSprintCount;
    }

    public void setCurrentSprintCount(double currentSprintCount) {
        this.currentSprintCount = currentSprintCount;
    }

    public double getSprintIncrease() {
        return SprintIncrease;
    }

    public void setSprintIncrease(int sprintIncrease) {
        SprintIncrease = sprintIncrease;
    }

    public int getButtonSprint() {
        return buttonSprint;
    }

    public void setButtonSprint(int buttonSprint) {
        this.buttonSprint = buttonSprint;
    }
}
