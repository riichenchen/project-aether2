/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Quest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Shiyang
 * The quest data class packages quest data and is the bridge that allows
 * the server and client to communicate quest logic.
 */
public class QuestData implements Serializable{
    private ArrayList<QuestRequirement> allRequirements; // all requirements
    private String questId; //an id representing it's name
    private int status; //0 = not started, 1 = started, 2 = complete
    
    public QuestData(String questId,int status){
        this.questId = questId;
        this.status = status;
        this.allRequirements = new ArrayList<>();
    }
    
    public void addRequirement(QuestRequirement req){
        this.allRequirements.add(req);
    }
    //Checks through all requirements and only returns true if all requirements
    //have successfully been achieved. In addition, an npc may check for whether
    //the player has a quantity of items or not.
    public boolean isComplete(){
        for (QuestRequirement req: allRequirements){
            if (!req.isCompleted()){
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<QuestRequirement> getRequirements(){
        return allRequirements;
    }
    
    public String getQuestId(){
        return questId;
    }
    
    public int getStatus(){
        return status;
    }
    
    public void setStatus(int newStatus){
        this.status = newStatus;
    }
}
