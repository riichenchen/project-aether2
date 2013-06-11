/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Skills.GameSkills;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.DamageFactory;
import GameSource.Assets.MobData.AbstractMob;
import GameSource.Skills.AbstractActiveSkill;
import GameSource.Skills.ActiveSkillData;
import GameSource.User.CharacterHandler;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class ArrowEruptionSkill extends AbstractActiveSkill{

    public ArrowEruptionSkill(float x, float z) {
        super(x, z, 800, 1200, "arroweruption");
    }
    
    @Override
    public int[] getEffectTimes() {
        return new int[]{60,72,84};
    }

    @Override
    public void skillEffect(Spatial s) {
        if (s instanceof AbstractMob){
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData(skillName));
            int dmg = CharacterHandler.calculateDamage(dat.getDamagePercentile(CharacterHandler.getSkillLevel(skillName)),(AbstractMob)s);
            ((AbstractMob)s).addHp(-dmg);
            DamageFactory.addDamage(boundMap,dmg,DamageFactory.RED,s.getLocation());
            invokeHitSound();
        }
    }

    @Override
    public void invokeHitSound() {
        //to be done
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        CharacterAnimControl cont = new CharacterAnimControl(AssetManager.getSpriteSet("Effects"));
        cont.swapAnim("arroweruption");
        return cont;
    }
    
}
