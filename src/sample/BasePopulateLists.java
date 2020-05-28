package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BasePopulateLists {





    /**
     KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
     Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
     Player reference values: FIRECOOLDOWN, BOMBCOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
     * @param count - the amount of players to be added
     */
    protected static ArrayList<Player> basePopulatePlayers(int count, Map gameMap){

        ArrayList<Player> playerList = new ArrayList<>();



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

        ArrayList<BeltSlot> beltList =null;

        boolean isGravityBound = true;

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
         * @param weaponList
         */

        Player player = new Player(
                x,y,width,height,HSpeed,VSpeed,defaultUp,defaultDown,defaultLeft,defaultRight, health, name, null,
                 isGravityBound
        );

        FileReader file = new FileReader("Players Model\\Player_John\\playersettings.txt");
        file.setFileFolder("Players Model\\Player_John\\");

        OverridingValuesClass.OverridePlayer(player,file);


        player.setObjColour(new Color(179, 245, 255));

        playerList.add(player);


        return playerList;
    }




}
