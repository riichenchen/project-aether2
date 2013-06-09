/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import GameSource.User.CharacterHandler;
import java.util.ArrayList;

/**
 *
 * @author Shiyang
 */
public class FrameCase {
    private ArrayList<FrameData> caseData;
    private int toFrame;
    
    public FrameCase() {
        this.caseData = new ArrayList<>();
    }
    
    public void addData(String dataType, String[] values){
        caseData.add(new FrameData(dataType,values));
    }
    
    public void setToFrame(int frame){
        this.toFrame = frame;
    }
    
    public int getToFrame(){
        return toFrame;
    }
    
    public boolean checkCaseData(int frame){
        for (FrameData dat: caseData){
            if(dat.dataType.equals("frame") && frame != Integer.parseInt(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("level") && CharacterHandler.getStat("level") < Integer.parseInt(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("money") && CharacterHandler.getStat("money") < Integer.parseInt(dat.values[0])){
                return false;
            } //else if (dat.dataType.equals("item") && InventoryHandler.countItem(dat.values))
        }
        return true;
    }
    
}
