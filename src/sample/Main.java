package sample;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

/**
 * 9/9/19
 * Currently a bug is causing the program to not draw the miners
 *
 * the current progject is to implement working buildings and the miners is the first of said objects,
 * the next building to implement will be the conveyors then inserters then furnace, then assembler,
 *
 * next an integration of power for the power lines and the generator should be implented along with the pipe system
 */

import static java.lang.Math.*;

        public class Main {

            public static JFrame frame = new JFrame("Frog Version: July 31, 2019");


            public Main(){

                Container c = frame.getContentPane();
                c.setBackground(Color.red);

                frame.setBackground(Color.yellow);


                frame.setLocationRelativeTo(null);
                GameCanvas program = new GameCanvas();
                frame.add(program);
                frame.setVisible(true);

                //On Close of game window go back to the menu window
                frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing Window");

                FileReader ouputData = new FileReader("DataOutput.txt");

                for(String line: program.getDateOutput()){
                    ouputData.addLine(line);
                    ouputData.addLine("FUnnny");
                }

                Menu.makeVisible();
            }
        });
    }
}
    class GameCanvas extends JComponent {

        private Conveyor defaultConveyor;
        private Player defaultPlayer;

        private SolidObject defaultSolidObject;
        private MovingObject defaultMovingObject;
        private Plane defaultPlane;
        private Frog defaultFrog;

        //default list of ores
        private CopperOre defaultCopper;
        private IronOre defaultIron;
        private CoalOre defaultCoal;

        //General World Settings
        private boolean gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

        private boolean graphicsOn = true;

        private int initPopulationSize;//used as the beginning population of the frogs




        private int GameSpeed = 1;//each increase is in every loop is how many times it per count eg: if 2 then every 10 millseconds all calculations are run twice

        private boolean isDebug; //if true the score board function will display a lot of information

        private int pelletCount = 1; // the number of pellets added per round

        //framecount using maths can sorta be used to get seconds / minutes ect but can be out of sync due to program / hardware lag
        private int framecount=1; //the total count of all frames for the duration of the program running

        private int roundDuration= 2;//this is in minutes
        private int roundCount = 0;//this is the current round count

        private int tempRoundCount = 0;

        private Random random = new Random(); // called in various places; mostly used to get a random nuber in a range using nextInt()

        //use a variable size player list to allow for more players later on / to allow for some to die
        private  ArrayList<Frog> frogList;

        private ArrayList<Player> playerList;

        //this is the list of the 'food' items
        private ArrayList<Food> pelletList;

        /**
         * Dynamic arraylist of all the buildings that are currently built
         */
        private ArrayList<Building> buildingsList  = new ArrayList<>();

        private ArrayList<Ore> oreList = new ArrayList<>();


        public ArrayList<String> getDateOutput() {
            return dateOutput;
        }

        public ArrayList<String> dateOutput = new ArrayList<String>();


        private ArrayList<BackgroundImage> backgroundImageList = new ArrayList<BackgroundImage>();

        //IMAGES FILE PATHS

        //The background images
        private  String backgroundFilePath;

        private BufferedImage BACKGROUNDIMAGE;

        //THE ACTUAL IMAGE OBJECT

        private BufferedImage currentBackground;

        private Map gameMap;

        private devTools developerTool = new devTools();

        /**
         * The mouse listener is used to activate various actions when the player / user should use the mouse
         */
        MouseListener ratListner = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            /**
             * ToDo
             * use the functions mouse pressed and mouse released to store a starting tile and ending tile
             * from these two tiles attempt to place from the top left the maximum of the player selected building
             * such buildings list should then indidually be compared for the saefty functions eg: collision of buildings
             *
             * This should allow the player to place a set of objects in a rectangular space
             *
             * adding a player alt button possibly shift might allow for additional safety
             *
             * eg: draggin and relaseing the mouse will create either along the columns or rows
             * but if the shift key is currenlty pressed then the mouse should default as described above wherin it goes along both the columns and rows
             *
             * this may help faster building and more intutive design
             *
             * such design should attempt to on some level provide a visual output of the currenlty selected tiles first by showing
             * the rectangle and then later by showing graphically but softened to show they haven't been placed images of the selected object as they will be placed
             *
             */
            @Override
            /**
             * This function will activate when a mouse button is pressed
             */
            public void mousePressed(MouseEvent e) {

            }

            @Override
            /**
             * Activates whenever a mouse button is released
             */
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            /**
             * This function will trigger with the first position of the mouse as it enters the window
             */
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            /**
             * This function will trigger with the last position of the mouse as it exits the window
             */
            public void mouseExited(MouseEvent e) {
            }
        };

        /**
         * The InputTracker is used to track keyboard actions as both listed under the developer commands and the
         * various commands of the players in the player lists
         * ToDo
         * I would like to see a comination of commands eg: shift + r to reverese the direction of rotation
         *
         */
        KeyListener InputTracker = new KeyListener() {

            public void keyPressed(KeyEvent e) {
                calcPlayerInput(e);

                /**
                 * This function is given e
                 * @param e - a keyboard input
                 * Then test the keyboard button against the dev buttons and activate various commands as needed
                 * @param graphicsOn
                 * @param gameMap
                 * @param gamePaused
                 * @param Keycmd_PauseGame
                 * @param Keycmd_repopulateFood
                 * @param Keycmd_ToggleGraphics
                 * @param Keycmd_IncreaseSpeed
                 * @param Keycmd_DecreaseSpeed
                 */
                developerTool.calcCommands(e,graphicsOn,gamePaused,gameMap, playerList);


                int key = e.getKeyCode();
            }
            public void keyTyped(KeyEvent e){


            }

            public void keyReleased(KeyEvent e) {
                Player.calcPlayerReleasedInput(e,playerList);
            }

        };

        private void InitializeDefaultValues() {
            Color defaultC = new Color(50,50,50);

            System.out.println("InitializeDefaultValues");

            defaultConveyor = new Conveyor(0,0,0,0,defaultC,
                    gameMap, null,null,null,null, false);

             defaultSolidObject = new SolidObject(0,0,0,0,defaultC);
             defaultMovingObject = new MovingObject(0,0,0,0,0,0);


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

            defaultPlayer = new Player(
                    0,0,0,0,0,0,
                    0,0,0,0,

                    0,"",
                    Player.intializeBelt(),

                    true
                                            );

            //default ORE initalizations prior to reading files
            defaultCoal = new CoalOre(0,0,0,0,defaultC,
                    null, null,null,null);


            //THE BLUE PLAYER FILE PATH
           // blueJetFilePath = "BlueJet.png";
            //blueJetFlipPath = "BlueJetFlipped.png";

            //The background image
            backgroundFilePath = "";

            //General World Settings
            gamePaused = false; // this is a toggle for the 'p' button to pause all movement players and arrows at the time of creation but potentially enemies

            initPopulationSize = 1;

            //the following must have additional lines for additional built players



            isDebug = false;

            //GROUND TROOP VALUES
            //use a variable size player list to allow for more players later on / to allow for some to die
            frogList = new ArrayList<>();

            playerList = new ArrayList<>();

            buildingsList = new ArrayList<>();

            pelletList = new ArrayList<>();

            graphicsOn = true;

            int roundDuration= 2;//this is in minutes
        }

        /**
         *
         */
        protected GameCanvas() {

            int tile_width = 35;
            int tile_height = tile_width;

            //by multipying the desired tiles by the respective size it ensures proper fitting.
            int map_width = tile_width *100;
            int map_height = tile_height *35;

            gameMap = new Map(0,0,400,400,
                    map_width, map_height, tile_width, tile_height,
                    1, 0);

        InitializeDefaultValues();

        populateDefaultOres();
        System.out.println("GameCanvas: "+(defaultCoal.getUp_Image()!=null));


        gamePaused = false;

        populateDefaultVariables();

        firstTimeinitialization();
    }


private void overrideGameValues(String fileName) {

    System.out.println("\noverrideGameValues\n");

    FileReader file = new FileReader(fileName);

    //Overrride all the player values
    OverrideAllPlayerValues(playerList);

    //Finally handle overriding the game cmd buttons
    OverridingValuesClass.OverrideGameCmds(file, pelletCount, gamePaused, isDebug,
            graphicsOn, roundDuration, initPopulationSize, developerTool.getKeycmd_IncreaseSpeed(),
            developerTool.getKeycmd_DecreaseSpeed(),developerTool.getKeycmd_repopulateFood(),
            developerTool.getKeycmd_ToggleGraphics(),developerTool.getKeycmd_StepRound(),
            developerTool.getKeycmd_repopulateFood()
            );
}


        /**
         * @param list This is at time of creation the player list which is referenced against internal values:
         * KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
         * Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
         *  Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
         *
         *  and will safely loop through and override if any such values are found
         *
         *  This looping allows for easy changes to the numbers of players without having to add additional code but
         *             means that to set a value it will look for buttonUp = 'Player_' + (the position, yes from 0)+'buttonUp'
         *             so as a whole it might set the 3rd players buttonAltFire = 'Player_2_buttonAltFire'
         */

        private void OverrideAllPlayerValues(ArrayList<Player> list) {

            System.out.println("OverrideAllPlayerValues");


            FileReader file = new FileReader("Players Model\\Player_0\\playersettings.txt");
            String temp = "";
            String players_folder = "Players Model\\Player_";
            String fileName = "playersettings.txt";
            String type = "Player";



            for (int position = 0; position< list.size();position++) {

                Player self = list.get(position);

               // file.setFileName(players_folder+position+"/"+fileName);
               // file.setFileName(fileName+position+"\\"+fileName);

                file.setFileName("Players Model\\Player_0\\playersettings.txt");

                file.setFileFolder("Players Model\\Player_0\\");

                System.out.println("OverridingPlayer: "+file.getFileName());
            }


            basePopulatePlayers(1,playerList,"OverrideAllPlayerValues");
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
        private void calcPlayerInput(KeyEvent e){

            int key = e.getKeyCode();


            /**
             * Loop through each plane in the arraylist and handle the relevant action if any match each individuals list of actions
             */

            if(playerList!=null)for(Player self: playerList) {

                if (self != null) {

                    //calcplayer belt selection changes
                    self.BeltItemKeyEvent(e);

                    //UP Key
                    if (self.getButtonUp() == key) {
                        //handle moving the plane / player in the requested direction
                        //Move the player up by negativify an absolute of the default value

                        self.setObjVSpeed(
                                -abs(self.getDefaultVSpeed()
                                ));

                    } else //DOWN Key
                        if (self.getButtonDown() == key) {
                            //handle moving the plane / player in the requested direction
                            //Move the player up by absolute of the default value

                            self.setObjVSpeed(
                                    abs(self.getDefaultVSpeed()
                                    ));
                        } else //LEFT Key
                            if (self.getButtonLeft() == key) {
                                //handle moving the plane / player in the requested direction
                                //Move the player left by negativify an absolute of the default value

                                self.setObjHSpeed(
                                        -abs(self.getDefaultHSpeed()
                                        ));
                            } else //RIGHT Key
                                if (self.getButtonRight() == key) {
                                    //handle moving the plane / player in the requested direction
                                    //Move the player right an absolute of the default speed value

                                    self.setObjHSpeed(
                                            abs(self.getDefaultHSpeed()
                                            ));
                                }
                    self.calcMovement();
                }
            }
        }




        private void intializeImages() {

            BACKGROUNDIMAGE = null;
            currentBackground = null;
        }

        private BufferedImage imageGetter(String filePathName) {
            try {

                return ImageIO.read(new File(filePathName));
            } catch (IOException e) {


                System.out.println(e.toString());
            }
            return null;
        }

        private void initMaze(){

            FileReader file = new FileReader("F:\\IdeaProjects\\Maze\\MazeList\\Maze_Default.txt");

            ArrayList<String> name = file.cleanArrayRead;

            for(String line: name){
                System.out.println(line);

                for(String sub_line: file.convertStringtoArray(line)){
                    System.out.println("SubLine: "+sub_line);
                }

            }



        }

        private void firstTimeinitialization() {

            initMaze();

            //use prebuilt values, make players and put them into the frogList arrayList

            String temp = "PlayerCount";
            FileReader file = new FileReader("GameSettings.txt");

            int value = 0;

            if (!file.findValue(temp).equals("")
                    && file.convertStringToInt(file.findValue(temp)) != -1)
                value =
                        file.  convertStringToInt(file.findValue(temp))
                ;


            overrideGameValues("GameSettings.txt");

            intializeImages();

            //make sure that the window will actually listen for inputs
            initListeners();

           Thread animationThread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        repaint();
                        try {
                            Thread.sleep(10);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
            animationThread.start();
        }

        public void initListeners() {
            this.addKeyListener(InputTracker);
            this.addMouseListener(ratListner);

            this.setFocusable(true);
        }




        public void paintComponent(Graphics g) {

            int frameSeconds =0; //calculates the seconds per the frame count
            int frameMinutes = 0; // calculates the minutes based on the seconds which come from the frame count

            Graphics2D gg = (Graphics2D) g;

            gameMap.setViewHeight(this.getHeight());
            gameMap.setViewWidth(this.getWidth());

            gameMap.updatePosition(playerList.get(0));

            gameMap.drawCheckerboard(gg);

            gameMap.drawBackgroundImages(gg, backgroundImageList);

            //stop the program doing anything when the program is paused

                //loop the game per regular cycle timers the game speed which means that there is a functional fast forward button
                for(int i = 0;i<GameSpeed;i++) {

                    //Only draw each object if the graphics are on and only calculate the movmenet if the game is not paused

                    if(graphicsOn) {
                        /**
                         * The order that these following lines are very important
                         * this is the order that things are drawn,
                         * the last on on the list gets to draw over everyone else and thus will appear if overlapping with another object
                         */

                        Ore.drawOre(gg,oreList,gameMap);

                        Food.drawFood(gg, pelletList, gameMap);
                        Building.drawBuildings(gg, buildingsList, gameMap);

                        Frog.drawFrog(gg, frogList, gameMap, !gamePaused);

                        Player.drawPlayers(gg, playerList, gameMap, !gamePaused,
                                null);


                        playerList.get(i).drawUI(gg, gameMap);

                        if(!isDebug)developerTool.drawScorebaord(gg, framecount, frameSeconds, frameMinutes
                        , roundCount, graphicsOn, gameMap, buildingsList,
                                oreList, playerList, frogList


                        );
                    }

                    framecount++;

                    frameSeconds = (framecount / 60); //calculates the seconds per the frame count
                    frameMinutes = frameSeconds / 60; // calculates the minutes based on the seconds which come from the frame count

                    //NEW ROUND
                    /**
                     * Given RoundDuration calculate when a new round occurs
                     *
                     * eg: round duration = 2 //it's in minutes
                     * everytime the frame minutes is evenly divisble into it then start a roun
                     */
                    try {

                        if (roundCount < frameMinutes / roundDuration
                            //|| noMovesLeft
                        ) {
                            roundCount++;
                            calcNewRound();
                        }
                    } catch (Exception e) {
                    }

                    //this function along with subfunctions handles collisions between most objects
                    calcCollisions();

                    repaint();
    }


            }



        public void calcNewRound(){
     //this is run to populate a new round
    }



/**
 * This function calculates and handles the                    collisions for the following arrayLists:
 *
 * This function should be called before any objects are drwawn
 * Explosions: explosionList
* Frog: frogList
* GroundFighters: groundFighters
* Projectiles: projectileList
*/
protected void calcCollisions(){

    calcBuildingCollisions();
}

        protected void calcBuildingCollisions(){
    if(buildingsList!=null){
    //Compare each building against the list of moving objects
    //this is going to typically be any dropped items (dropped items do include items on conveyors)
    //additionally this will be the player too
    for(Building building: buildingsList){


        /**
         * Calculate a collision of a building against the player
         * When this heppens the player should bounce off the building unless it is a solid building
         */
        for(Player player: playerList){
            if(building.isCollision(player,gameMap)){

                if(building.isSolid()) {
                    //TOP
                    if (player.isCollision(
                            building.getPosX(), building.getPosY(), building.getObjWidth(), 1, gameMap
                    )) {
                        player.setPosY(
                                building.getPosY() -
                                        player.getObjHeight()
                        );
                    } else if (
                        //BOTTOM
                            player.isCollision(
                                    building.getPosX(),
                                    (building.getPosY()+building.getObjWidth())-1,
                                    building.getObjWidth(),
                                    1, gameMap
                            )
                    ) {
                        player.setPosY(
                                building.getPosY() + building.getObjHeight()

                        );
                    }
                    //LEFT
                    if (player.isCollision(
                            building.getPosX(),
                            building.getPosY(),
                            1,
                            building.getObjHeight(), gameMap
                    )) {
                        player.setPosX(
                                building.getPosX()
                                        -
                                        player.getObjWidth()
                        );
                    } else if (
                        //RIGHT
                            player.isCollision(
                                    (building.getPosX()+building.getObjWidth())-1,
                                    building.getPosY(),
                                    1,
                                    building.getObjHeight(), gameMap
                            )
                    ) {

                        player.setPosX(
                                building.getPosX() + building.getObjWidth()+1
                        );

                    }

                }

                else{
                    building.calcCollsion(player);
                }

            }
        }
    }
  }
}

        /**
         *
         * @return an arraylist containing the defaulted belt list of the player's belt
         */
        private ArrayList<BeltSlot> intializeBelt(boolean popdefaultvalues){

            System.out.println("intializeBelt");

        if(popdefaultvalues)populateDefaultVariables();

        ArrayList<BeltSlot> list = new ArrayList<>();

        list.add(new BeltSlot(51, new Color(255, 24, 217) , defaultConveyor,false));


        populateDefaultVariables();

        return list;
        }

    /**
     KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
     Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
     Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
     * @param count - the amount of players to be added
     * @param list - the list of which to add them to
     */
    private void basePopulatePlayers(int count, ArrayList<Player> list, String calledby){
        System.out.println("basePopulatePlayers: "+ calledby);

        System.out.println("Playervalue successful: "+ (defaultPlayer!=null));


        int x = gameMap.getMapWidth()/2;
        int y = gameMap.getMapHeight()/2;

        String name = "PLAYER";

        //Default KeyBoard values
        int defaultUp = 0;
        int defaultDown = 0;
        int defaultLeft =0;
        int defaultRight = 0;
        int defaultFire = 69;
        int defaultAltFire = 81;

        //Default Plane values
        int width = 1;
        int height = 3;
        int VSpeed = 1;
        int HSpeed = 1;
         int health = 50;

        //Reference Values
        int fireCool = 0;
        int bombCool = 0;

        int projHeight = 100;
        int projWidth = 100;

        ArrayList<BeltSlot> beltList = intializeBelt(true);

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

            Player player = new Player(
                    x,y,width,height,HSpeed,VSpeed,

                    defaultUp,defaultDown,defaultLeft,defaultRight

                     ,health, name
                    ,beltList, true);

            FileReader file = new FileReader("Players Model\\Player_0\\playersettings.txt");
            file.setFileFolder("Players Model\\Player_0\\");


            OverridingValuesClass.OverridePlayer(defaultPlayer,file);

            System.out.println("\nDefaultOverClass: "+(defaultPlayer!=null)+"\n");
            System.out.println("Safely overriden player values: "+OverridingValuesClass.OverridePlayer(player,defaultPlayer));

            playerList.add(player);

}



/**
 * This function will call various sub functions to populate the default objects
 * the default objects are pre populated to store the various values of images and sizes, colours, ect
 * to prevent serious load issues on calling each objet as the function caries out its duties
 */
    private void populateDefaultVariables(){
    populateDefaultBuildings();
    populateDefaultOres();
    }


protected void populateDefaultBuildings(){
    System.out.println("populateDefaultBuildings");

    FileReader file = new FileReader("");

    String fileFolder = "";

    String fileName = "buildingSettings.txt";

    String fileType = "";

    //Buildings
    fileFolder="Buildings\\";

    //Assembler
    fileType = "Assembler\\";

    //Conveyor
    fileType = "Conveyor\\";

    System.out.println(fileFolder+fileType+fileName);
    file.setFileFolder(fileFolder+fileType);
    file.setFileName(fileFolder+fileType+fileName);
    OverridingValuesClass.OverrideConveyor(defaultConveyor,file);
    defaultConveyor.setGameMap(gameMap);


}

/**
 * GameMap MUST be initalized before this is called
 * && the default Ores MUST BE inialized
 */
private void populateDefaultOres() {
    System.out.println("populateDefaultOres");

    FileReader Ofile = null;
    /*
    String fileType = "";
    String fileFolder = "ore\\";
    String fileName = "OreSettings.txt";
    */
    //COAL
    Ofile = new FileReader("ore\\coal\\OreSettings.txt");
    Ofile.setFileFolder("ore\\coal\\");
    OverridingValuesClass.OverrideSolidObject(defaultCoal, Ofile);
    defaultCoal.setGameMap(gameMap);


}


        }