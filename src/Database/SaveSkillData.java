/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;

/**
 *
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
