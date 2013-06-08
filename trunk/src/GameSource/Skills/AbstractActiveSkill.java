/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills;

import Controls.CharacterAnimControl;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractActiveSkill extends CharacterSpatial{
    private int[] effectTimes;
    private int time = 0;
    private CharacterAnimControl control;
    protected String skillName;
    
    public abstract int[] getEffectTimes();
    public abstract void skillEffect(Spatial s);
    
    public AbstractActiveSkill(float x, float z, float l, float w,String skillName) {
        super(x, 1.5f, z, l, 100, w,0,0,0);
        this.effectTimes = getEffectTimes();
        this.control = (CharacterAnimControl)getControl(CharacterAnimControl.class);
        this.skillName = skillName;
    }

    @Override
    public void collideEffect(Spatial s) {
        for (int t: effectTimes){
            if (time == t){
                skillEffect(s);
            }
        }
    }
    
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
