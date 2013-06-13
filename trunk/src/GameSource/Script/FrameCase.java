/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import GameSource.Quest.QuestManager;
import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;
import java.util.ArrayList;

/**
 * @author Shiyang
 * The framecase class contains information about 1 case that is
 * present inside an npc script. Cases are used to determine whether
 * and which frame of an npc chat to direct the game to.
 */

public class FrameCase {
    //An arraylist of all framedata requirements for this case
    private ArrayList<FrameData> caseData;
    private int toFrame;
    
    public FrameCase() {
        this.caseData = new ArrayList<>();
    }
    
    //Standard add and bind methods
    public void addData(String dataType, String[] values){
        caseData.add(new FrameData(dataType,values));
    }
    
    public void setToFrame(int frame){
        this.toFrame = frame;
    }
    //Which frame does this case lead to when true?
    public int getToFrame(){
        return toFrame;
    }
    
    //the check case data method takes in a frame id and based on the information
    //found in the inventory and mapdata, checks each caseData requirement.
    //It then returns true or false based upon whether all requirements have
    //been fulfilled.
    public boolean checkCaseData(int frame){
        for (FrameData dat: caseData){
            if(dat.dataType.equals("frame") && frame != Integer.parseInt(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("level") && CharacterHandler.getStat("level") < Integer.parseInt(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("money") && CharacterHandler.getStat("money") < Integer.parseInt(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("item") && !InventoryHandler.hasItem(dat.values[0],Integer.parseInt(dat.values[1]))){
                return false;
            } else if (dat.dataType.equals("clearmap") && CharacterHandler.getPlayer().getMap().getMobCount() != 0){
                return false;
            } else if (dat.dataType.equals("questcomplete") && !QuestManager.checkQuest(dat.values[0])){
                return false;
            } else if (dat.dataType.equals("queststatus") && QuestManager.getQuestStatus(dat.values[0]) != Integer.parseInt(dat.values[1])){
                return false;
            }
        }
        return true;
    }
    
}
