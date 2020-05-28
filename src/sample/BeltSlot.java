package sample;

import java.awt.*;

public class BeltSlot {

    /**
     * The interger representing the keyEvent - key code that is this belt item's activation number
     */
    private int keyCmd;

    private Building defaultBuilding;//The default values of the building that should be used when called, eg width, height, image


    /**
     * @param keyCmd - the interger value of the keyboard keyevent keycode number
     * @param color the background color of the beltItem
     */
    public BeltSlot(int keyCmd, Color color, Building defaultBuilding, boolean isMiner) {
        this.keyCmd = keyCmd;
        this.defaultBuilding = defaultBuilding;

        if(this.defaultBuilding!=null)this.defaultBuilding.setObjColour(color);
    }

    public void DrawBeltItem(Graphics2D gg, int itemX, int belty, int itemWidth, int beltheight){
        gg.setColor(defaultBuilding.getObjColour());
        gg.fillRect(itemX, belty,
                itemWidth, beltheight);
    }

    public int getKeyCmd() {
        return keyCmd;
    }

    public void setKeyCmd(int keyCmd) {
        this.keyCmd = keyCmd;
    }

    public Color getColor() {
        return defaultBuilding.getObjColour();
    }

    public void setColor(Color color) {
        defaultBuilding.setObjColour(color);
    }


    public Building getDefaultBuilding() {
        Building output = new Building(0,0,0,0,0,
                0, null, false);
        OverridingValuesClass.OverrideBuilding(output,defaultBuilding);
        return output;
    }

    public void setDefaultBuilding(Building defaultBuilding) {
        this.defaultBuilding = defaultBuilding;
    }

}
