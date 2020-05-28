package sample;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    /**
     * To point of this class to handle the playable area the 'map' if you will is larger than the visible area
     * Additionally this should handle providing the visible area and drawing it
     * <p>
     * The view will follow a moving object, typically a player and will compensate as the player in a given direction until the limit of the map size is met in any given direction and then the viewing port will stop in that direction
     */

    private int viewX; //The X Position of the top left of the viewing area
    private int viewY; //the Y position of the top left of the viewing area

    private int viewWidth;//the width of the viewing area, typically going to be the size of the graphical window
    private int viewHeight;//the width of the viewing area, typically going to be the size of the graphical window


    private int MapWidth;
    private int MapHeight;

    //Tiles will be used as the boxes to alow for a consistant grid system to allow for easy building
    private int tileWidth; // the width of the tiles
    private int tileHeight; //the height of the tiles


    private double Verticial_gravityConstant = 0;

    private double Horizontal_gravityConstant = 0;


    //2D arraylist of the tiles
    private ArrayList<ArrayList<SolidObject>> tileList = new ArrayList<>();

    /**
     *
     * @param viewX
     * @param viewY
     * @param viewWidth
     * @param viewHeight
     * @param mapWidth
     * @param mapHeight
     * @param tileWidth
     * @param tileHeight
     * @param verticial_gravityConstant
     * @param horizontal_gravityConstant
     */
    public Map(
            int viewX, int viewY, int viewWidth, int viewHeight,

            int mapWidth, int mapHeight, int tileWidth, int tileHeight,

            double verticial_gravityConstant,

            double horizontal_gravityConstant
    )
    {
        this.viewX = viewX;
        this.viewY = viewY;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        MapWidth = mapWidth;
        MapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        Verticial_gravityConstant = verticial_gravityConstant;
        Horizontal_gravityConstant = horizontal_gravityConstant;

        initTiles(new Color(202, 211, 203),
                new Color(156, 156, 156));
    }

    /**
     * Update the position of the map values every frame call
     * @param target
     * @param gg
     * @param width
     * @param height
     */
    protected void CycleUpdate(MovingObject target, Graphics2D gg, int width, int height, ArrayList<BackgroundImage> imagelist){
        this.setViewHeight(height);
        this.setViewWidth(width);
        this.drawCheckerboard(gg);
        this.drawBackgroundImages(gg, imagelist);
        this.updatePosition(target);
    }

    private void initTiles(Color white, Color notWhite){

        //calculate the number of rows / columns by finding out how may of the size given fit
        int rows = MapWidth/tileWidth;
        int columns = MapHeight/tileHeight;

        //make a checkerboard pattern on the screen equally sized based on the window size

        int colSize = tileHeight; // calculate the actual size of the rows

        int rowSize = tileWidth; // calculates the actual size of the columns

        boolean isWhite = false; // used to change back and forth the value of squares color

        ArrayList<SolidObject> sub_list = new ArrayList<>();

        Color colour = Color.white;

        //loop through every row
        // within each row loop through every column
        //in each position make a box in the available space

        for (int r = 0; r < rows; r++) {

            sub_list = new ArrayList<>();//erase for new set

            for (int c = 0; c < columns; c++) {
                //make a square at the current iterated position of the columns and rows of the size of those columns and rows divided by the window size
                int X = (r * rowSize)-viewX;
                // calculate the position of the top left of the rectangle

                int Y = (c* colSize)-viewY;

                //alternate what needs to be drawn where
                if (isWhite) {
                    colour = white;
                    //flip it back to the other color
                    isWhite = !isWhite;


                } else {
                    colour = notWhite;
                    //flip it back to the other color
                    isWhite = !isWhite;
                }

                sub_list.add(new SolidObject(X,Y,rowSize,colSize,colour));

            }

            tileList.add(sub_list);

            //end of a row looping through each column position

            if (columns == rows && (columns % 2) == 0) {
                isWhite = !isWhite;
            } else if (rows < columns && (columns % 2) == 0) {
                isWhite = !isWhite;
            }

        }


    }

    /**
     * Calc gravity per tick for a given arraylist
     * @param list -Moving Object arrayList to calc gravity for
     */
    public void calcGravity_ArrayList(ArrayList<Player> list){
        if(list!=null)//always safety checks
            for(MovingObject A: list){

                System.out.println(A.getActualPosY()+A.getObjHeight() +" < " + this.getMapHeight());

                //confirm the player isn't on the top /bottom of the board
                if(
                        A.getActualPosY() > 0
                                &&
                                (A.getActualPosY()+A.getObjHeight()) <
                                        (

                                                this.getMapHeight()
                                                        -
                                                        (this.getTileHeight()*3)
                                        )
                )
                    A.setObjVSpeed(A.getObjVSpeed() + getVerticial_gravityConstant());

                A.setObjHSpeed(A.getObjHSpeed() + getHorizontal_gravityConstant());

            }

    }


    /**
     * Given an arraylist of BackgroundImage draw the images in accordance
     * to the relative position to the view
     */

    public void drawBackgroundImages(Graphics2D gg,ArrayList<BackgroundImage> imageList){

        /**
         * only draw images that 'colide' with the view map
         */

        for(BackgroundImage i: imageList){

            int width = i.getObjWidth();
            int height = i.getObjHeight();

            i.setObjWidth(i.getObjWidth()*4);
            i.setObjHeight(i.getObjHeight()*4);

            if(i.isCollision(viewX,viewY,viewWidth*4,viewHeight)
            ){
                i.setObjWidth(width);
                i.setObjHeight(height);
                gg.drawImage(i.getImage(),
                        i.getPosX()- viewX
                        , i.getPosY()  - viewY
                        , i.getObjWidth(), i.getObjHeight()
                        , null);
            }
            i.setObjWidth(width);
            i.setObjHeight(height);


        }


    }


    /**
     * Draw a checkerboard to help determining if the program is working as intended
     * @param gg - it's what's drawn on
     */

    /**
     * The class values of the col, row are used to distiished how many rows / columns are needed
     * The  WindowLength and WindowWidth are used to disginquished how many columns may be neded to fill the vailable space
     * <p>
     * This function should only be called by the paint class as anyone else may cause an endless loop
     */
    public void drawCheckerboard(Graphics2D gg) {
        gg.setBackground(Color.red);

        for(ArrayList<SolidObject> list: tileList){

            for(SolidObject john: list){

                gg.setColor(john.getObjColour());

                john.drawobj(gg,this);

            }

        }


    }

    /**
     * Given an X,Y coordinates return a SolidObject representing the tile position (x,y,width, length, and Color Orange)
     * @param  targetx - x coordinate (assumes positive)
     * @param targety - y coordinate (assumes positive)
     */
    public SolidObject CollidedTile(int targetx, int targety, Map gameMap) {
        SolidObject output = null;

        for(int i = 0; i< tileList.size(); i ++){

            for(int j = 0 ; j < tileList.get(i).size();j++){

                if(
                        tileList.get(i).get(j).
                                isCollision(targetx,targety,1,1)){

                    output =  tileList.get(i).get(j);
                }
            }


        }

        return output;
    }

    /**
     * Draw the vision that should be the only area on the screen
     * @param gg - graphics to draw stuff
     */
    public void drawVisionSight(Graphics2D gg){

        gg.setColor(Color.orange);

        gg.fillRect(
                viewX+100
                ,
                viewY+100,

                (viewWidth-10), (viewHeight-10)

        );

    }

    public void updatePosition(SolidObject centerObject){

        viewX = centerObject.getPosX()-(viewWidth/2);
        viewY = (centerObject.getPosY()-(viewHeight/2));

        if(
                (viewX+viewWidth)
                        >MapWidth)viewX = MapWidth- viewWidth;
        if(
                (viewY + viewHeight)
                        > MapHeight) viewY = MapHeight - viewHeight;


        if(viewX<0)viewX = 1;//stop the X value of the view from exceeding the overall position of the map
        if(viewY<0)viewY=1;

    }


    /**
     * @return true if the Building and the object collide tiles
     */
    public boolean calcTileCollision(SolidObject john, SolidObject sam){
        boolean isCollision = true;

        if(isAbove(sam, john)||isBelow(sam,john)){
            return false;
        }
        else if(isRight(sam,john)||isLeft(sam,john)){
            return false;
        }
        return true;
    }

    public boolean isBelow(SolidObject sam, SolidObject john){
        /**
         * this is true when 'this' object is entirely above sam
         */
        if((john.getPosY()+john.getObjHeight()<sam.getPosY())){
            return true;
        }
        return false;
    }
    public boolean isAbove(SolidObject sam, SolidObject john){
        /**
         * this is true when 'this' object is entirely below sam
         */
        if(john.getPosY()>sam.getPosY()+sam.getObjHeight()){
            return true;
        }
        return false;
    }

    public boolean isRight(SolidObject sam, SolidObject john){
        /**
         * this is true when 'this' object is entirely above sam
         */
        if((john.getPosX()+john.getObjHeight()<sam.getPosX())){
            return true;
        }
        return false;
    }
    public boolean isLeft(SolidObject sam, SolidObject john){
        /**
         * this is true when 'this' object is entirely below sam
         */
        if(john.getPosX()>sam.getPosX()+sam.getObjWidth()){
            return true;
        }
        return false;

    }

    //Given an ore type, amount of ore locations, min size of ore location, max size of ore location
    public ArrayList<Ore> populateOre(Ore a, int x, int y, int width, int height){
        ArrayList<Ore> output = new ArrayList<>();

        //hand on the scale for now, will update the populatation with real randomiztion of the ore setup
        x*= tileWidth;
        y *=tileHeight;

        int counter = 0;

        for(int i =0; i<width;i++){

            for(int j=0;j<height;j++){

                output.add(new Ore(

                        1*tileWidth,1*tileHeight,
                        x+(i*tileWidth)
                        ,
                        y+(j*tileHeight)
                        ,Color.red,

                        a.getUp_Image(), a.getL_Image(),a.getUp_Image(),a.getUp_Image()

                ));

                OverridingValuesClass.OverrideSolidObject(output.get(counter),a);
                output.get(counter).setObjWidth(tileWidth);
                output.get(counter).setObjHeight(tileHeight);


                counter++;

            }
        }

        System.out.println("GAMEMAP : COAL: "+(a.getUp_Image()!=null)+" : "+(output.get(0).getUp_Image()!=null));


        return output;
    }

    protected void updateBoardSize(){
        int x = 0;
        int y = 0;
        for(ArrayList<SolidObject> a:tileList){

            y =tileList.indexOf(a)*tileWidth;

            for(SolidObject b: a){

                x = (a).indexOf(b)*tileHeight;


                b.setObjHeight(tileHeight);
                b.setObjWidth(tileWidth);

                b.setPosX(x);
                b.setPosY(y);

            }

        }

    }


    protected void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
        if(this.tileHeight<0)this.tileHeight=1;

        updateBoardSize();
    }

    protected void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
        if(this.tileWidth<0)this.tileWidth=1;
        updateBoardSize();
    }

    /**
     *
     * @param object - the object to determine
     *               if the object is found to be within the maps view then return true
     * @return if the obj is colliding with the view of the map
     */
    protected boolean isVisible(SolidObject object){
        boolean isVisible = false;

        isVisible=object.isCollision(this.getViewX(),this.getViewY(),this.getViewWidth(),this.getViewHeight(),this);


        return isVisible;
    }

    public int getViewX() {
        return viewX;
    }

    public void setViewX(int viewX) {
        this.viewX = viewX;
    }

    public int getViewY() {
        return viewY;
    }

    public void setViewY(int viewY) {
        this.viewY = viewY;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public int getMapWidth() {
        return MapWidth;
    }

    public void setMapWidth(int mapWidth) {
        MapWidth = mapWidth;
    }

    public int getMapHeight() {
        return MapHeight;
    }

    public void setMapHeight(int mapHeight) {
        MapHeight = mapHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }


    public int getTileHeight() {
        return tileHeight;
    }


    public ArrayList<ArrayList<SolidObject>> getTileList() {
        return tileList;
    }

    public void setTileList(ArrayList<ArrayList<SolidObject>> tileList) {
        this.tileList = tileList;
    }

    public double getVerticial_gravityConstant() {
        return Verticial_gravityConstant;
    }

    public void setVerticial_gravityConstant(double verticial_gravityConstant) {
        Verticial_gravityConstant = verticial_gravityConstant;
    }

    public double getHorizontal_gravityConstant() {
        return Horizontal_gravityConstant;
    }

    public void setHorizontal_gravityConstant(double horizontal_gravityConstant) {
        Horizontal_gravityConstant = horizontal_gravityConstant;
    }
}