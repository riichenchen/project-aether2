/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

/**
 * @author Shiyang
 * The Abstract skilldata class, providing a template for all skilldata classes
 * Future: add different types of skills such as passive and buff
 */
public abstract class SkillData {
    protected String name;
    protected String description;
    
    public SkillData(String name){
        this.name = name;
    }
    //All skills require a description and name
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
