/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

import java.util.HashMap;

/**
 *
 * @author Shiyang
 */
public class ActiveSkillData extends SkillData{
    private String activeType;
    private HashMap<Integer,Integer> mpCost;
    private HashMap<Integer,Double> damagePercentile;
    private HashMap<Integer,Integer> ranges;
    private HashMap<Integer,Integer> castTime;
    
    public ActiveSkillData(String name,String activeType) {
        super(name);
        this.activeType = activeType;
        this.mpCost = new HashMap<>();
        this.damagePercentile = new HashMap<>();
        this.ranges = new HashMap<>();
        this.castTime = new HashMap<>();
    }
    
    public void setMpCost(int level,int value){
        mpCost.put(level, value);
    }
    
    public void setDamagePercentile(int level,double value){
        damagePercentile.put(level, value);
    }
    
    public void setRange(int level,int value){
        ranges.put(level, value);
    }
    
    public void setCastTime(int level,int value){
        castTime.put(level, value);
    }
    
    public int getMpCost(int level){
        return mpCost.get(level);
    }
    public double getDamagePercentile(int level){
        return damagePercentile.get(level);
    }
    public int getRange(int level){
        return ranges.get(level);
    }
    public int getCastTime(int level){
        return castTime.get(level);
    }
    public String getActiveType(){
        return activeType;
    }
}
