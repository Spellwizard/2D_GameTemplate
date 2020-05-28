package sample;

public class OverridingValuesClass {

    /*
     * The purpose of this class is two fold:
     * To help with clutter in 'Main.java' class
     * and in general to be the class that actually overrides values as provided by files
     * eg: getting pictures of objects and reading object settings
     * Additionally storing a default value of all objects will be useful b/c it will stop unneccessary calls for pictures / reading files
     * Which can have significant effect on performance especially if the user is repeatitly creating objects
     */

    /**
     * SolidObjects
     *  posX - left @ 0
     *  posY - left @ 0
     *  objWidth - Override by looking for 'objWidth'
     *  objHeight - Override by looking for 'objHeight'
     *  objColour - not yet able to override, will implement feature in feature
     *  UpModel - override object image looking up
     *  DownModel - override object image looking up
     *  LeftModel - override object image looking up
     *  RightModel - override object image looking up
     *
     * @param nut given a SolidObject or child read from the filereader
     * @param fileReader read the file reader for certain values as documented above and override values
     * @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideSolidObject(SolidObject nut , FileReader fileReader   ){
        String temp;
        if(nut!=null&&fileReader!=null) {


            System.out.println("\nOverrideSolidObject: ");

            temp = "objWidth";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setObjWidth(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            System.out.println("objWidth: "+fileReader.convertStringToInt(fileReader.findValue(temp)));

            temp = "objHeight";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setObjHeight(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            System.out.println("objHeight: "+fileReader.convertStringToInt(fileReader.findValue(temp)));

            //This is prone to returning errors

            temp = "UpModel";
            System.out.println(fileReader.getFileFolder()+fileReader.findActualValue(temp));

            if (!fileReader.findActualValue(temp).equals(""))
                nut.setUpImage(fileReader.getFileFolder()+fileReader.findActualValue(temp));

            System.out.println("OverrideSolidObject UpModel: '"+  fileReader.getFileFolder()+fileReader.findActualValue(temp)+"';");

            temp = "DownModel";
            System.out.println(fileReader.getFileFolder()+fileReader.findValue(temp));
            if (!fileReader.findValue(temp).equals(""))
                nut.setDownImage(fileReader.getFileFolder()+fileReader.findValue(temp));
            System.out.println("OverrideSolidObject DownModel: "+fileReader.getFileFolder()+fileReader.findValue(temp));

            temp = "LeftModel";
            System.out.println(fileReader.getFileFolder()+fileReader.findValue(temp));
            if (!fileReader.findValue(temp).equals(""))
                nut.setLeftImage(fileReader.getFileFolder()+fileReader.findValue(temp));
            System.out.println("OverrideSolidObject LeftModel: "+fileReader.getFileFolder()+fileReader.findValue(temp));

            temp = "RightModel";
            System.out.println(fileReader.getFileFolder()+fileReader.findValue(temp));
            if (!fileReader.findValue(temp).equals(""))
                nut.setRightImage(fileReader.getFileFolder()+fileReader.findValue(temp));
            System.out.println("OverrideSolidObject RightModel: "+fileReader.getFileFolder()+fileReader.findValue(temp));

            return true;//overriding worked
        }
        else{
            return false;//overriding failed due to non instatiated object / filereader
        }
    }
    /**
     * SolidObjects
     *  posX - left @ 0
     *  posY - left @ 0
     *  objWidth - Override by looking for 'objWidth'
     *  objHeight - Override by looking for 'objHeight'
     *  objColour - not yet able to override, will implement feature in feature
     *
     * @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     * @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideSolidObject(SolidObject nut , SolidObject defaultObject   ){

        if(nut!=null&&defaultObject!=null) {

            nut.setObjWidth(defaultObject.getObjWidth());

            nut.setObjHeight(defaultObject.getObjHeight());

            nut.setObjColour(defaultObject.getObjColour());
/**
            nut.setPosX(defaultObject.getPosX());

            nut.setPosY(defaultObject.getPosY());
 */

                //This is prone to errors
            if(defaultObject.getUp_Image()!=null){
                    nut.setUp_Image(defaultObject.getUp_Image());
                }

            if(defaultObject.getDown_Image()!=null){
                nut.setDown_Image(defaultObject.getDown_Image());
            }

            if(defaultObject.getL_Image()!=null){
                nut.setL_Image(defaultObject.getL_Image());
            }

            if(defaultObject.getR_Image()!=null){
                nut.setR_Image(defaultObject.getR_Image());
            }

            return true;

        }
        return false;
    }

    /**
     * MovingObjects
     *  defaultHSpeed & HSpeed sets the current H speed and stores the original H speed as default for calling later
     *  defaultVSpeed & VSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     *
     * @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     * @return false if either nut or defaultObject was not initalized
     */
    protected static boolean OverrideMovingObject(MovingObject nut , MovingObject defaultObject   ){

        if(OverrideSolidObject(nut , defaultObject    )) {

            nut.setObjVSpeed(defaultObject.getDefaultVSpeed());
            nut.setDefaultVSpeed(defaultObject.getDefaultVSpeed());

            nut.setObjHSpeed(defaultObject.getDefaultHSpeed());
            nut.setDefaultHSpeed(defaultObject.getDefaultHSpeed());

            return true;
        }
        return false;
    }

    /**
     * MovingObjects
     *  defaultHSpeed & HSpeed sets the current H speed and stores the original H speed as default for calling later
     *  defaultVSpeed & VSpeed sets the current V speed and stores the oringinal V speed as defualt for caling later
     *
     * @param nut given an object or child read from the filereader
     * @param fileReader read the file reader for certain values as documented above and override values
     * @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideMovingObject(MovingObject nut , FileReader fileReader   ){
        String temp;
        if(OverrideSolidObject(nut , fileReader    )) {

            temp = "VSpeed";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1) {
                nut.setObjVSpeed(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );
                nut.setDefaultVSpeed(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );
            }

            temp = "HSpeed";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1) {
                nut.setObjHSpeed(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );
                nut.setDefaultHSpeed(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );
            }

            return true;
        }

        return false;

    }

    /**
     * Plane
     *  health the health of the player
     *  name the name of the player
     *  FIRECOOLDOWN The default value reference to stop the player from spamming the fire button
     *  ALTFIRECOOLDOWN the default value reference to stop the player from spamming the alt fire button
     *  defaultProjectileHeight the reference height for when the plane fires a projectile
     *  defaultProjectileWidth the reference width of a fired projectile
     * @param nut given an object or child read from the filereader
     *@param fileReader read the file reader for certain values as documented above and override values
     * @return false if either nut or filereader was not initalized
     */
    protected static boolean OverridePlane(Plane nut , FileReader fileReader   ){
        String temp;
        if(OverrideMovingObject(nut , fileReader    )) {

            temp = "health";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setHealth(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            temp = "name";

            if (!fileReader.findValue(temp).equals(""))
                nut.setName(
                        fileReader.findValue(temp)
                );

            temp = "FIRECOOLDOWN";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setFIRECOOLDOWN(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            temp = "ALTFIRECOOLDOWN";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setALTFIRECOOLDOWN(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            temp = "defaultProjectileHeight";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setDefaultProjectileHeight(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );

            temp = "defaultProjectileWidth";

            if (!fileReader.findValue(temp).equals("")
                    && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
                nut.setDefaultProjectileWidth(
                        fileReader.convertStringToInt(fileReader.findValue(temp))
                );
            return true;
        }
            return false;
    }

    /**
     * Plane
     *  health the health of the player
     *  name the name of the player
     *  FIRECOOLDOWN The default value reference to stop the player from spamming the fire button
     *  ALTFIRECOOLDOWN the default value reference to stop the player from spamming the alt fire button
     *  defaultProjectileHeight the reference height for when the plane fires a projectile
     *  defaultProjectileWidth the reference width of a fired projectile
     * @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverridePlane(Plane nut , Plane  defaultObject ){

        if(OverrideMovingObject(nut , defaultObject    )) {

           // temp = "health";

           nut.setHealth(defaultObject.getHealth());

           // temp = "name";

            nut.setName(defaultObject.getName());


           // temp = "FIRECOOLDOWN";
            nut.setFIRECOOLDOWN(defaultObject.getFireCooldown());


           // temp = "ALTFIRECOOLDOWN";
            nut.setALTFIRECOOLDOWN(defaultObject.getAltFireCooldown());


            //temp = "defaultProjectileHeight";
            nut.setDefaultProjectileWidth(defaultObject.getDefaultProjectileWidth());


            //temp = "defaultProjectileWidth";

            nut.setDefaultProjectileHeight(defaultObject.getDefaultProjectileHeight());




            return true;
        }
        return false;
    }

    /** Player
     *  buttonUp
     *  buttonDown
     *  buttonLeft
     *  buttonRight
     *  buttonFire
     *  buttonAltFire
     * @param nut given a SolidObject or child read from the filereader
     * @param fileReader read the file reader for certain values as documented above and override values
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverridePlayer(Player nut , FileReader fileReader   ){
       String temp;
       if(OverridePlane(nut , fileReader    )) {

           temp = "buttonUp";

           if (!fileReader.findValue(temp).equals("")
                   && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
               nut.setButtonUp(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );
           temp = "buttonDown";

           if (!fileReader.findValue(temp).equals("")
                   && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
               nut.setButtonDown(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );
           temp = "buttonLeft";

           if (!fileReader.findValue(temp).equals("")
                   && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
               nut.setButtonLeft(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );
           temp = "buttonRight";

           if (!fileReader.findValue(temp).equals("")
                   && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1)
               nut.setButtonRight(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );


           return true;
       }
            return false;
   }

    /** Player
     *  buttonUp
     *  buttonDown
     *  buttonLeft
     *  buttonRight
     *  buttonFire
     *  buttonAltFire
     * @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverridePlayer(Player nut , Player defaultObject   ){

        if(OverridePlane(nut , defaultObject    )) {

            //temp = "buttonUp";
            nut.setButtonUp(defaultObject.getButtonUp());

            //temp = "buttonDown";
            nut.setButtonDown(defaultObject.getButtonDown());

            //temp = "buttonLeft";
            nut.setButtonLeft(defaultObject.getButtonLeft());

            //temp = "buttonRight";
            nut.setButtonRight(defaultObject.getButtonRight());

            return true;
        }
        return false;


    }


    /**
     * energy & defaultEnergy
     * private Food targetPellet - not overriden
    *  @param nut given a SolidObject or child read from the filereader
      * @param fileReader read the file reader for certain values as documented above and override values
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideFrog(Frog nut , FileReader fileReader   ){
        String temp;
       if(OverridePlane(nut , fileReader    )) {

           temp = "energy";

           if (!fileReader.findValue(temp).equals("")
                   && fileReader.convertStringToInt(fileReader.findValue(temp)) != -1) {
               nut.setDefaultEnergy(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );
               nut.setEnergy(
                       fileReader.convertStringToInt(fileReader.findValue(temp))
               );
           }

           return true;
       }
        return false;
   }

    /**
     * energy & defaultEnergy
     * private Food targetPellet - not overriden
     *  @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideFrog(Frog nut , Frog defaultObject   ){

        if(OverridePlane(nut , defaultObject    )) {

            //temp = "energy";
            nut.setEnergy(defaultObject.getEnergy());


            return true;
        }
            return false;

    }

    /**
     *  @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideBuilding(Building nut , Building defaultObject  ) {

        if(OverrideMovingObject(nut, defaultObject)){

            nut.setSolid(defaultObject.isSolid());

        return true;
    }

        return false;
    }

    /**
     *  @param nut given a SolidObject or child read from the filereader
     * @param fileReader read the file reader for certain values as documented above and override values
     *  @return false if either nut or filereader was not initalized
     */
    protected static boolean OverrideBuilding(Building nut , FileReader fileReader   ) {

        if(OverrideMovingObject(nut, fileReader)){

            String temp;
            if(nut!=null&&fileReader!=null) {


                //isSolid
                temp = fileReader.findValue("isSolid");
                //check to make sure that there is a value
                if (!temp.equals("")) {

                    //check to see if the value is true
                    if (temp.equals("true")) {
                        nut.setSolid(true);
                    }

                    //check to see if the value is false
                    if (temp.equals("false")) {
                        nut.setSolid(false);
                    }
                }

            }


            return true;
        }

        return false;
    }


    /**
     *  @param nut given a SolidObject or child read from the filereader
     * @param fileReader read the file reader for certain values as documented above and override values
     *  @return false if either nut or filereader was not initalized
     */
    protected static  boolean OverrideConveyor(Conveyor nut , FileReader fileReader   ){

        if(OverrideBuilding(nut , fileReader    )){

            return true;
        }
        return false;

    }
    /**
     *  @param nut given a SolidObject or child read from the filereader
     * @param defaultObject - override 'nut's values with the default values of the default object
     *  @return false if either nut or filereader was not initalized
     */
    protected static  boolean OverrideConveyor(Conveyor nut , Conveyor defaultObject){

        if(OverrideBuilding(nut , defaultObject    )){

            return true;
        }
        return false;

    }




    /**
     * Look from the file and override some game cmd values like pausing
     * @param file - File that the values are looked for
     * @param pelletCount
     * @param gamePaused
     * @param isDebug
     * @param graphicsOn
     * @param Keycmd_PauseGame
     * @param Keycmd_StepRound
     * @param Keycmd_IncreaseSpeed
     * @param Keycmd_DecreaseSpeed
     * @param Keycmd_repopulateFood
     * @param Keycmd_ToggleGraphics
     * @param roundDuration
     * @param initPopulationSize
     */
    protected static void OverrideGameCmds(FileReader file
                                ,int pelletCount, boolean gamePaused,
                                  boolean isDebug, boolean graphicsOn,
                                  int Keycmd_PauseGame, int Keycmd_StepRound, int Keycmd_IncreaseSpeed, int Keycmd_DecreaseSpeed, int Keycmd_repopulateFood, int Keycmd_ToggleGraphics,
                                  int roundDuration, int initPopulationSize

    ) {

        String temp = "";

        //pelletCount
        if(!file.findValue("pelletCount").equals(""))
            pelletCount = file.convertStringToInt(file.findValue("pelletCount"));

        //BOOLEAN OVERRIDE VALUES

        //gamePaused
        temp = file.findValue("gamePaused");
        //check to make sure that there is a value
        if (!temp.equals("")) {

            //check to see if the value is true
            if (temp.equals("true")) {
                gamePaused = true;
            }

            //check to see if the value is false
            if (temp.equals("false")) {
                gamePaused = false;
            }
        }

        //isdebug
        temp = file.findValue("isdebug");
        //check to make sure that there is a value
        if (!temp.equals("")) {

            //check to see if the value is true
            if (temp.equals("true")) {
                isDebug = true;
            }

            //check to see if the value is false
            if (temp.equals("false")) {
                isDebug = false;
            }
        }

        temp=file.findValue("graphicsOn");;
        //check to make sure that there is a value
        if (!temp.equals("")) {

            //check to see if the value is true
            if (temp.equals("true")) {
                graphicsOn = true;
            }

            //check to see if the value is false
            if (temp.equals("false")) {
                graphicsOn = false;
            }
        }

        /**
         *
         *  int Keycmd_StepRound
         *  int Keycmd_IncreaseSpeed
         *  int Keycmd_DecreaseSpeed
         */
        //  int firstPlayer_buttonUp

        temp = "Keycmd_PauseGame";
        if (!file.findValue(temp).equals("")
                && file.convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_PauseGame = file.convertStringToInt(file.findValue(temp));

        // int Keycmd_StepRound
        temp = "Keycmd_StepRound";
        if (!file.findValue(temp).equals("")
                && file.convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_StepRound = file.convertStringToInt(file.findValue(temp));

        // int Keycmd_IncreaseSpeed
        temp = "Keycmd_IncreaseSpeed";
        if (!file.findValue(temp).equals("")
                &&file. convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_IncreaseSpeed = file.convertStringToInt(file.findValue(temp));

        // int Keycmd_DecreaseSpeed
        temp = "Keycmd_DecreaseSpeed";
        if (!file.findValue(temp).equals("")
                && file.convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_DecreaseSpeed = file.convertStringToInt(file.findValue(temp));

        //Keycmd_repopulateFood
        temp = "Keycmd_repopulateFood";
        if (!file.findValue(temp).equals("")
                &&file. convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_repopulateFood =file. convertStringToInt(file.findValue(temp));

        //Keycmd_ToggleGraphics
        temp = "Keycmd_ToggleGraphics";
        if (!file.findValue(temp).equals("")
                && file.convertStringToInt(file.findValue(temp)) != -1)
            Keycmd_ToggleGraphics =file. convertStringToInt(file.findValue(temp));


        //roundDuration
        temp = "roundDuration";
        if (!file.findValue(temp).equals("")
                &&file. convertStringToInt(file.findValue(temp)) != -1)
            roundDuration = file.convertStringToInt(file.findValue(temp));

        temp = "initPopulationSize";
        //confirm that the array contains the desired value and isn't an empty value
        if (
                !file.findValue(temp).equals("")
                        &&
                        file. convertStringToInt(file.findValue(temp)) != -1)

            initPopulationSize =file. convertStringToInt(file.findValue(temp));



    }


}
