package sample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
//This is used to track keyboard inputs
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

public class OptionsMenu extends JFrame implements ActionListener {

    FileReader fRead = new FileReader("GameSettings.txt");

    Label ButtonInputer = null;

    ArrayList<String> aNames = new ArrayList<String>();

    ArrayList<Label> optionValue = new ArrayList<>();

    ArrayList<JButton> buttonArray = new ArrayList<>();

    ArrayList<JTextField> OptionSetting = new ArrayList<>();

    private int playerCount = 1;

    KeyListener InputTracker = new KeyListener() {


        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("KeyPressed: "+e.getKeyCode());
            ButtonInputer.setText("Button Pressed: "+e.getKeyCode());
        }
        //these two are needed but will be unused
        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e) {}

    };


    public OptionsMenu(String windowName){

        initKeyListener();

        aNames.add("isdebug");
        aNames.add("graphicsOn");
        aNames.add("Keycmd_ToggleGraphics");
        aNames.add("roundDuration");
        aNames.add("Keycmd_PauseGame");
        aNames.add("Keycmd_StepRound");
        aNames.add("Keycmd_DecreaseSpeed");
        aNames.add("Keycmd_IncreaseSpeed");
        aNames.add("PlayerCount");
        /**
        aNames.add("frogEnergy");
        aNames.add("frogWidth");
        aNames.add("frogHeight");
        aNames.add("blueJetFilePath");
        aNames.add("blueJetFlipPath");
        aNames.add("frogdefaultHSpeed");
        aNames.add("frogdefaultVSpeed");
        aNames.add("booleangamePaused");

        aNames.add("pelletCount");
        aNames.add("initPopulationSize");
        aNames.add("Keycmd_repopulateFood");
        aNames.add("frogSight");

*/

        String temp = "PlayerCount";
        if (!fRead.findValue(temp).equals("")
                && fRead.convertStringToInt(fRead.findValue(temp)) != -1)
            playerCount= fRead.convertStringToInt(fRead.findValue(temp)
            );



        //make sure that on closure the options window is hidden in favour of the menu window being made visible
        //On Close of game window go back to the menu window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing OptionsWindow");

                Menu.makeVisible();
            }
        });

        System.out.println("New Sheet made: "+windowName);

        this.setTitle(windowName);

        onlineEXadaptations();


    }


    public ArrayList<String> addPlayerSets(int count){

/**
 KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
 * Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
 *  Player reference values: FIRECOOLDOWN, ALTFIRECOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
 */

        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i<count;i++){
            list.add("buttonUp");
            list.add("buttonDown");
            list.add("buttonLeft");
            list.add("buttonRight");
            list.add("buttonFire");
            list.add("buttonAltFire");
            list.add("objHeight");
            list.add("objWidth");
            list.add("VSpeed");
            list.add("HSpeed");
            list.add("health");
            list.add("Name");
            list.add("FIRECOOLDOWN");
            list.add("ALTFIRECOOLDOWN");
            list.add("DefaultProjectileHeight");
            list.add("DefaultProjectileWidth");

            /**
            list.add(type+"_"+i+"_UpPlayerModel");
            list.add(type+"_"+i+"_DownPlayerModel");
            list.add(type+"_"+i+"_LeftPlayerModel");
            list.add(type+"_"+i+"_RightPlayerModel");
             */

        }

        return list;

    }

    public ArrayList<String> addPlayerList(int i){

/**
 KeyBoard Inputs: buttonUP, buttonDown, buttonLeft, buttonRight ,buttonFire, buttonAltFire
 * Player Values: Height, Width, VSpeed (and sets the default), HSpeed (and sets the default), health, name (entirely for role play)
 *  Player reference values: FIRECOOLDOWN, ALTFIRECOOLDOWN, DefaultProjectileHeight, DefaultProjectileWidth
 */

        ArrayList<String> list = new ArrayList<>();

        list.add("buttonUP");
            list.add("buttonDown");
            list.add("buttonLeft");
            list.add("buttonRight");
            list.add("buttonFire");
            list.add("buttonAltFire");
            list.add("Height");
            list.add("Width");
            list.add("VSpeed");
            list.add("HSpeed");
            list.add("Health");
            list.add("Name");
            list.add("FIRECOOLDOWN");
            list.add("ALTFIRECOOLDOWN");
            list.add("DefaultProjectileHeight");
            list.add("DefaultProjectileWidth");
        return list;

    }

    public void initKeyListener(){
        this.addKeyListener(InputTracker);
        this.setFocusable(true);
    }

    /**
     *
     * @param name Text of the JLabel
     * @param x Pos on the x axis
     * @param y Pos on the y axis
     * @return a jlabel with the given values
     */
    public JLabel createLabel(String name, int x, int y){
        //create a J Label with the string name as given
        JLabel i = new JLabel(name);
        //set the position of the label as given x,y
        //i.setBounds(x,y);
        //i.setSize(width,height);
        i.setVisible(true);
        i.validate();
        //System.out.println("Built Label: "+name+", "+i.validate(););
        return i;
    }

    public JTextField createText(String description, int column){

        JTextField i = new JTextField(description);

        i.setColumns(column);

        return i;
    }

    public JTextField createText(int column, int row, String description){

        JTextField i = new JTextField(description);

        i.setColumns(column);


        return i;
    }

    public void onlineEXadaptations()

    {
        //Create a container of the gridbagConstraints
        GridBagConstraints cst = new GridBagConstraints();

        JPanel topPanel = createJPanelOptions(cst, "PROGRAM OPTIONS Panel 1");


        topPanel.addKeyListener(InputTracker);

        topPanel.setVisible(true);

        topPanel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        topPanel.setBackground(new Color(175,30,30));


        this.getContentPane().add(topPanel);

        this.getContentPane().setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        this.pack();
        this.setVisible(true);

        this.setSize(900,1000);


        repaint();

    }


    public JPanel createJPanelOptions(GridBagConstraints cst, String name){

        //Set the font with both the size of the text and the style of the
        // text that will be used for all subsequent items (JLabels, buttons, ect)
        Font fFont = new Font(Font.SERIF, Font.PLAIN,  25);

        Color backColor = Color.black;
        Color textColor = Color.white;


        //Create a JPanel with the GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        //set where the next component will be added on the row and column
        cst.gridx = 0; //column
        cst.gridy = 0; // row

        //add a label
        Label titleLabel = new Label(name);
        titleLabel.setBackground(backColor); //set the background colour
        titleLabel.setForeground(textColor);
        titleLabel.setFont(fFont);
        panel.add(titleLabel, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;


        int rowHeight = 50; // the row height
        int columnWidth = 300; // the row width

        /**
         * CreateRow()
         * @param list
         * @param rowPosition
         * @param cst
         * @param textColor
         * @param backColor
         * @param columnWidth
         * @param rowHeight
         * @param fFont
         * @param panel
         */
        FileReader player_Reader = new FileReader("Players Model\\Player_0\\playersettings.txt");

        createRow(addPlayerSets(1),player_Reader,0,cst,textColor,backColor,columnWidth,rowHeight,fFont,panel);

        /**
        createRow(aNames,fRead,0,cst,textColor,backColor,columnWidth,rowHeight,fFont,panel);

            //createRow(secondSet,fRead,2,cst,backColor,textColor,columnWidth,rowHeight,fFont,panel);
                String fileName = "playersettings.txt";
            for(int i = 0; i<playerCount;i++){
                String folder = "Players Model\\Player_"+i+"\\playersettings.txt";
                player_Reader.setFileName(folder+fileName);
                createRow(addPlayerList(i),player_Reader,(i+1)*2,cst,backColor,textColor,columnWidth,rowHeight,fFont,panel);

            }
         */

        //Add some additional filler to try to help with the crunch on the information
        for(int i = 1; i<4;i++) {
            cst.gridx = i; //column
            cst.gridy = aNames.size() +addPlayerSets(playerCount).size() + 2; // row

            //add a save button that will update the new values into the text file
            String filler = "                                           ";
            Label buttony = new Label(filler);

            buttony.setBackground(new Color(40, 40, 40));
            buttony.setForeground(Color.white);
            buttony.setFont(fFont);

            panel.add(buttony, cst);

            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;
        }

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column

        cst.gridx = 0; //column
        cst.gridy = aNames.size() +addPlayerSets(playerCount).size() + 4; // row

        //add a save button that will update the new values into the text file
        ButtonInputer = new Label("Button Pressed: ");

        ButtonInputer.setBackground(new Color(40, 40, 40));
        ButtonInputer.setForeground(Color.white);
        ButtonInputer.setFont(fFont);
        panel.add(ButtonInputer, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column
        cst.gridx = 1; //column
        cst.gridy = aNames.size()+addPlayerSets(playerCount).size()+4; // row

        //add a save button that will update the new values into the text file
        JButton BSave = new JButton("Save Changes");
        BSave.setActionCommand("BSave");
        BSave.addActionListener(this);

        BSave.setBackground(new Color(50,255,50));
        BSave.setFont(fFont);

        panel.add(BSave, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        return panel;

    }

    /**
     * CreateRow()
     * @param list
     * @param rowPosition
     * @param cst
     * @param textColor
     * @param backColor
     * @param columnWidth
     * @param rowHeight
     * @param fFont
     * @param panel
     */
    public void createRow(ArrayList<String> list,FileReader fRead, int rowPosition,GridBagConstraints cst, Color textColor,
                          Color backColor, int columnWidth, int rowHeight, Font fFont, JPanel panel) {
        System.out.println(fRead.getFileName());

        for (int i = 0; i < list.size(); i++) {

            //Invert the background and the text colour to allow for easily read rows
            Color temp = textColor;
            textColor = backColor;
            backColor = temp;


            String firstvalue = fRead.findActualValue(list.get(i));//try to find the string value from the saved options items

            System.out.println(": "+list.get(i)+" : "+firstvalue);

            //If the desired value is not found then add the desired value
            String test = firstvalue.replace(" ", "");
            if (test.equals("")) {
                fRead.overWriteLine(list.get(i), "NULL");
            }

            //set where the next component will be added on the row and column
            cst.gridx = rowPosition; //column

            cst.gridy = i + 1; // row

            //create a label
            Label libel = new Label(list.get(i));
            libel.setBackground(backColor);
            libel.setForeground(textColor);
            libel.setPreferredSize(new Dimension(columnWidth, rowHeight));
            libel.setFont(fFont);
            optionValue.add(libel);
            panel.add(libel, cst);

            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

            //Second Set Label

            cst.gridx = rowPosition+3; //column

            cst.gridy = i + 1; // row

            //set where the next component will be added on the row and column
            cst.gridx = rowPosition+1; //column
            cst.gridy = i + 1; // row

            //add a text field and populate with the value
            JTextField textbox = new JTextField(firstvalue);
            textbox.setBackground(backColor);
            textbox.setForeground(textColor);
            textbox.setPreferredSize(new Dimension(columnWidth, rowHeight));
            textbox.setFont(fFont);
            textbox.addKeyListener(InputTracker);
            OptionSetting.add(textbox);
            panel.add(textbox, cst);


            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;


        }

    }


    public Container testLabelList(){

        //used to contain any and all options names / short descriptions
        Container cOptionsLabels = new Container();

        return cOptionsLabels;
    }

    //called when the mainmenu needs to be drawn
    //it should clear the window and set the mainmenu up with its buttons and items

    private void OptionsMenu(){
        System.out.println("OptionsMenu Function running");


        //temp to add basic items to the array to allow for testing

        //make a new container to have the primary buttons
        Container MenuButtonsContainer = testLabelList();


        //used to contain any and all options names / short descriptions
        //Container cOptionsLabels = testLabelList();

        //Primary buttons


        //BUTTONS

        int ButtonYPos = 450;
        int ButtonWidth = 100;
        int ButtonHeight = 50;

        int ButtonXPos = this.getWidth()-(ButtonWidth + (ButtonWidth/5));

        //QUIT BUTTON
        CreateButton(0, ButtonYPos, ButtonWidth, ButtonHeight, "Exit",
                "Bquit", Color.RED, MenuButtonsContainer);


        //SAVE BUTTON
        CreateButton(ButtonXPos, ButtonYPos, ButtonWidth, ButtonHeight, "Save",
                "Save", Color.GREEN, MenuButtonsContainer);

        //Make sure the containers will be visible
        MenuButtonsContainer.setVisible(true);
        //cOptionsLabels.setVisible(true);

        //add the containers to the frame
        this.add(MenuButtonsContainer);
        // this.add(cOptionsLabels);

        repaint();
    }

    /**
     * Given Parameters this will make a button and add it to the given container
     * @param posX -
     * @param posY-
     * @param width -
     * @param length -
     * @param name -
     * @param actionCommand -
     * @param colour -
     * @param container -
     */
    public void CreateButton(int posX, int posY, int width, int length,String name, String actionCommand, Color colour, Container container){

        Button output = new Button(name);
        //set a code to check when any buttons are clicked
        output.setActionCommand(actionCommand);
        output.addActionListener(this);
        output.setVisible(true);
        output.setBackground(colour);

        output.setBounds(posX,posY, width, length);

        container.add(output);

    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);
        g.fillRect(10,10,10,10);
    }

    //this can be used to check if a button, check box or the like is used
    public void actionPerformed(ActionEvent ae) {
        FileReader player_Setting = new FileReader("Players Model\\Player_0\\playersettings.txt");

        String action = ae.getActionCommand();

        //check to see what button was pressed and execute relevant actions
        if (action.equals("BSave")) {
            System.out.println("BSave");

            //cleanly read the file and get cooresponding values back
            ArrayList<String> aFile = fRead.cleanRead();

            /**
             * Loop through the aNames and values and find the cooresponding values in the textfile
             * then override the values as long as the exisiting value is of the same type ie boolean can't be a number
             */
            for( int i = 0;i< optionValue.size();i++){

                if(!fRead.overWriteLine(optionValue.get(i).getText(),
                        OptionSetting.get(i).getText())){

                    //If the value was not found in the global settings file then check the player values
                    for(int T = 0; T<playerCount;T++) {
                        player_Setting.setFileName("Players Model\\Player_"+T+"\\playersettings.txt");
                        if (!player_Setting.overWriteLine(optionValue.get(i).getText(),
                                OptionSetting.get(i).getText())) {

                        } else {
                            //update the cooresponding text box
                            OptionSetting.get(i).setText(player_Setting.findValue(optionValue.get(i).getText()));
                            break;
                        }
                    }


                }
                else{
                    //update the cooresponding text box
                    OptionSetting.get(i).setText(fRead.findValue(optionValue.get(i).getText()));
                }


            }


        }

    }




}
