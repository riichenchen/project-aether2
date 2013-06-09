/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Quest;

/**
 *
 * @author Shiyang
 */
public class QuestRequirement {
    private String mobId;
    private String questId;
    private int number;
    private int requiredNumber;
    
    public QuestRequirement(String mobId,String questId,int number,int requiredNumber) {
        this.mobId = mobId;
        this.questId = questId;
        this.number = number;
        this.requiredNumber = requiredNumber;
    }

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
    
    public boolean isCompleted(){
        return number >= requiredNumber;
    }
    
    public void addNumber(){
        number++;
    }
    
}
