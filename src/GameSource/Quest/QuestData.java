/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Quest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Shiyang
 */
public class QuestData implements Serializable{
    private ArrayList<QuestRequirement> allRequirements;
    private String questId;
    
    public QuestData(String questId){
        this.questId = questId;
        this.allRequirements = new ArrayList<>();
    }
    
    public void addRequirement(QuestRequirement req){
        this.allRequirements.add(req);
    }
    
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
    
}
