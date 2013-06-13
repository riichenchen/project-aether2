/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Quest;

import java.io.Serializable;

/**
 * @author Shiyang
 * This class is essentially a data class that contains information regarding
 * a certain requirement of a quest. All requirements tracked this way count
 * mobs, as items can be tracked through the inventory handler.
 */
public class QuestRequirement implements Serializable{
    private String mobId;
    private String questId;
    private int number;
    private int requiredNumber;
    
    //constructor takes in mob, questname, current number and the number required
    //and makes a QuestRequirement object.
    public QuestRequirement(String mobId,String questId,int number,int requiredNumber) {
        this.mobId = mobId;
        this.questId = questId;
        this.number = number;
        this.requiredNumber = requiredNumber;
    }

    
    //Standard get and set methods below :DD
    public String getMobId() {
        return mobId;
    }

    public String getQuestId() {
        return questId;
    }

    public int getNumber() {
        return number;
    }

    public int getRequiredNumber() {
        return requiredNumber;
    }
    
    //Returns true if this requirement has been fulfilled
    public boolean isCompleted(){
        return number >= requiredNumber;
    }
    
    public void addNumber(){
        number++;
    }
    
}
