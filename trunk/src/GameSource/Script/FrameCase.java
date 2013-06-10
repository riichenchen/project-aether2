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