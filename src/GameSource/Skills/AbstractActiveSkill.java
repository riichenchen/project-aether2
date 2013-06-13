/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

import Controls.CharacterAnimControl;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 * @author Shiyang
 * The abstract active skill class acts as a skeleton for all active skills
 * to build off of. It requires all skills include a skill effect to the spacial
 * that it's collided with as well as a sound effect and an effects time array
 * which determines when in the frameset damage is actually dealt
 */
public abstract class AbstractActiveSkill extends CharacterSpatial{
    private int[] effectTimes;
    private int time = 0;
    private CharacterAnimControl control;
    protected String skillName;
    
    public abstract int[] getEffectTimes();
    public abstract void skillEffect(Spatial s);
    public abstract void invokeHitSound();
    
    public AbstractActiveSkill(float x, float z, float l, float w,String skillName) {
        super(x, 1.5f, z, l, w, w,0,0,0); // 1.5, meaning the effects are always drawn over everything
        this.effectTimes = getEffectTimes();
        //binds the characteranimcontrol added by CharacterSpatial to this class.
        this.control = (CharacterAnimControl)getControl(CharacterAnimControl.class);
        this.skillName = skillName;
    }
    
    //The overridden collideEffect method. Only allows skillEffects to occur
    //when the current time = an effect time
    @Override
    public void collideEffect(Spatial s) {
        for (int t: effectTimes){//O(n) runtime, although usually effects dont hit more than 3 times
            if (time == t){
                skillEffect(s);
            }
        }
    }
    
    //The overridden update method. Ticks the skill's time and removes it
    // if it's time is up
    @Override
    public void update(){
        super.update();
        time++;
        if (time == control.getLimit()){
            boundMap.removeSpatial(this);
        }
    }
    
    public String getSkillName(){
        return skillName;
    }
}
