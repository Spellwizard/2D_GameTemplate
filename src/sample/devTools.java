package sample;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class devTools {

    //User Input keyboard button values
    private int Keycmd_PauseGame=80; //pause / unpause the round
    private int Keycmd_StepRound=75; //allow for 1 run of the game and then pause before next round
    private int Keycmd_IncreaseSpeed=74;//increase round speed
    private int Keycmd_DecreaseSpeed=76;//decrease round speed
    private int Keycmd_ToggleGraphics= 0;//used to toggle if the graphics are on
    private int Keycmd_repopulateFood = 0;//resets food


    public devTools() {
    }

    /**
     *
     * @param gg
     * @param framecount
     * @param frameSeconds
     * @param frameMinutes
     * @param roundCount
     * @param graphicsOn
     * @param gameMap
     * @param buildingsList
     * @param oreList
     * @param playerList
     * @param frogList
     */
    protected void drawScorebaord(Graphics2D gg, int framecount, int frameSeconds, int frameMinutes
                                         , int roundCount, boolean graphicsOn,
                                         Map gameMap,
                                         ArrayList<Building> buildingsList, ArrayList<Ore> oreList,
                                         ArrayList<Player> playerList, ArrayList<Frog> frogList
                                         ){
    //  SCOREBOARD

        gg.setColor(Color.black);

        int pos = 60;

        int posy = 35;

        if (frogList != null)
            gg.setColor(Color.white);
        gg.setFont(new Font("TimesRoman", Font.PLAIN, 25));


            gg.setColor(Color.white);
            gg.setFont(new Font("TimesRoman", Font.PLAIN, 12));

            if(gameMap!=null)gg.drawString("Frame Count: " + framecount +
                            " Second count: " +  frameSeconds+
                            " Minute count: " +  frameMinutes
                            +" Map View: "+gameMap.getViewX()+", "+gameMap.getViewY()
                            +" Map Size: "+gameMap.getMapWidth()+" , "+gameMap.getMapHeight()
                            +" Round: "+roundCount
                            +" graphicsOn: "+graphicsOn
                            +" Map size: W"+gameMap.getTileWidth()
                            +" H"+gameMap.getTileHeight()
                    , 50, 30);
            gg.setColor(Color.white);

            ScrBrd_OreList(gg,gameMap,50,posy);

            posy+=100;


            /**
             * make some text on screen with some detail for the object list of the buildings
             */
            if(buildingsList!=null) {
                gg.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                for (SolidObject john : buildingsList) {
                    if (john != null) gg.drawString("Building: "
                                    + " Width " + (john.getObjHeight() + " Height: " + john.getObjWidth())
                                    + "\t X, Y Values: (" + john.getPosX() + ", " + john.getPosY() + ") " +
                                    " Compensated coords:(" + (john.getPosX() - gameMap.getViewX())
                                    + ", " + (john.getPosY() - gameMap.getViewY())
                            , 50, 50 + posy);
                    posy += 15;


                }

                if(playerList!=null){
                    for(Plane john: playerList) {
                        String extraDetails = " ButtonUp";

                        gg.drawString("Player Speed: H: " + john.getObjHSpeed() + " V: " + john.getObjVSpeed()
                                        + " Size: " + (john.getObjHeight() + john.getObjWidth()) / 2 +
                                        " Health: " + john.getHealth()
                                        +"\t (" + john.getPosX() + ", " + john.getPosY()+ ") "+
                                        " Compensated coords:("+(john.getPosX()-gameMap.getViewX())
                                        +", "+( john.getPosY()-gameMap.getViewY())
                                        +" W: "+john.getObjWidth()+" H: "+john.getObjHeight()
                                , 50, (50+posy));

                        posy+=15;


                    }
                }


                if (false) {


                    if(oreList!=null)for(Ore john: oreList) {
                        gg.drawString("ORE"
                                        + " Width " + (john.getObjHeight()+" Height: " + john.getObjWidth())
                                        +"\tX, Y Values: (" + john.getPosX() + ", " + john.getPosY() + ") "+
                                        " Compensated coords:("+(john.getPosX()-gameMap.getViewX())
                                        +", "+( john.getPosY()-gameMap.getViewY())
                                , 50, 50+posy);
                        posy+=15;
                    }

                    if(frogList!=null)for(Plane john: frogList) {
                        gg.drawString("Frog Speed: H: " + john.getObjHSpeed() + " V: " + john.getObjVSpeed()
                                        + " Width " + (john.getObjHeight()+" Height: " + john.getObjWidth())+
                                        " Energy: " + john.getEnergy()
                                        +"\tFrog X, Y Values: (" + john.getPosX() + ", " + john.getPosY() + ") "+
                                        " Compensated coords:("+(john.getPosX()-gameMap.getViewX())
                                        +", "+( john.getPosY()-gameMap.getViewY())
                                , 50, 50+posy);
                        posy+=15;
                    }



                }
            }
        }

        private void ScrBrd_OreList(Graphics2D gg, Map gameMap, int x,int posy){
            if(gameMap!=null
                    &&gameMap.getTileList()!=null) {
                gg.setFont(new Font("TimesRoman", Font.PLAIN, 10));

                //Very first tile
                if (gameMap.getTileList().get(0).get(0) != null) gg.drawString("Tile 0,0: "
                                + "("+gameMap.getTileList().get(0).get(0).getPosX()+", "
                                +gameMap.getTileList().get(0).get(0).getPosY()+") W:"+
                                gameMap.getTileList().get(0).get(0).getObjWidth()+" H:"
                                +gameMap.getTileList().get(0).get(0).getObjHeight()



                        , x, 50 + posy);
                posy += 15;

                int size =gameMap.getTileList().size();
                int size2 = gameMap.getTileList().get(size-1).size()-1;

                size--;

                if (gameMap.getTileList().get(size).get(size2) != null) gg.drawString("Last Tile: "
                                + "("+
                                gameMap.getTileList().get(size).get(size2)   .getPosX()+", "
                                +gameMap.getTileList().get(size).get(size2)     .getPosY()+") W:"+
                                gameMap.getTileList().get(size).get(size2) .getObjWidth()+" H:"
                                +gameMap.getTileList().get(size).get(size2)     .getObjHeight()



                        , x, 50 + posy);

                posy += 15;
            }

        }




    /**
     * This function is given e
     * @param e - a keyboard input
     * Then test the keyboard button against the dev buttons and activate various commands as needed
     * @param gameMap
     * @param gamePaused
     */
    protected void calcCommands(KeyEvent e,  boolean graphicsOn,boolean gamePaused,
                                Map gameMap,
                                       ArrayList<Player> playerList
    ){

        int key = e.getKeyCode();

        //'P' key
        //stop ALL motion until the 'p' key is hit again
        //all sorts of problems with running the freeze code.... just getting mvp
        if (key == Keycmd_PauseGame) {
            //toggle the game paused variable
            gamePaused =!gamePaused;
        }

        else if(key==Keycmd_repopulateFood){

        }
        else if(key==Keycmd_ToggleGraphics){
            graphicsOn=!graphicsOn;
        }

        else if(key == Keycmd_IncreaseSpeed){
            int increment = 3;
            gameMap.setTileHeight(gameMap.getTileHeight()+increment);
            gameMap.setTileWidth(gameMap.getTileWidth()+increment);
/**
 for(Plane john: playerList){
 john.setDefaultVSpeed(john.getDefaultVSpeed()+increment);
 john.setDefaultHSpeed(john.getDefaultHSpeed()+increment);
 }

 */
        }
        else if(key == Keycmd_DecreaseSpeed){
            int increment = -3;

            gameMap.setTileHeight(gameMap.getTileHeight()+increment);
            gameMap.setTileWidth(gameMap.getTileWidth()+increment);

           /** for(Plane john: playerList){
             john.setDefaultVSpeed(john.getDefaultVSpeed()+increment);
             john.setDefaultHSpeed(john.getDefaultHSpeed()+increment);
            }*/
        }

    }

    public int getKeycmd_PauseGame() {
        return Keycmd_PauseGame;
    }

    public void setKeycmd_PauseGame(int keycmd_PauseGame) {
        Keycmd_PauseGame = keycmd_PauseGame;
    }

    public int getKeycmd_StepRound() {
        return Keycmd_StepRound;
    }

    public void setKeycmd_StepRound(int keycmd_StepRound) {
        Keycmd_StepRound = keycmd_StepRound;
    }

    public int getKeycmd_IncreaseSpeed() {
        return Keycmd_IncreaseSpeed;
    }

    public void setKeycmd_IncreaseSpeed(int keycmd_IncreaseSpeed) {
        Keycmd_IncreaseSpeed = keycmd_IncreaseSpeed;
    }

    public int getKeycmd_DecreaseSpeed() {
        return Keycmd_DecreaseSpeed;
    }

    public void setKeycmd_DecreaseSpeed(int keycmd_DecreaseSpeed) {
        Keycmd_DecreaseSpeed = keycmd_DecreaseSpeed;
    }

    public int getKeycmd_ToggleGraphics() {
        return Keycmd_ToggleGraphics;
    }

    public void setKeycmd_ToggleGraphics(int keycmd_ToggleGraphics) {
        Keycmd_ToggleGraphics = keycmd_ToggleGraphics;
    }

    public int getKeycmd_repopulateFood() {
        return Keycmd_repopulateFood;
    }

    public void setKeycmd_repopulateFood(int keycmd_repopulateFood) {
        Keycmd_repopulateFood = keycmd_repopulateFood;
    }
}
