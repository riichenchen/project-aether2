/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;

/**
 * This data contains information about a skill and it's level.
 * It is sent from client to server and vice versa to update
 * and save data regarding skills and their levels
 * @author Shiyang
 */
public class SaveSkillData implements Serializable{
    private int level;
    private String skillName;
    public SaveSkillData(String skill, int level){
        this.skillName = skill;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getSkillName() {
        return skillName;
    }
    
}
