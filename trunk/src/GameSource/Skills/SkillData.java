/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

/**
 *
 * @author Shiyang
 */
public abstract class SkillData {
    protected String name;
    protected String description;
    
    public SkillData(String name){
        this.name = name;
    }
    
    public void setDescription(String descrip){
        this.description = descrip;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
}
