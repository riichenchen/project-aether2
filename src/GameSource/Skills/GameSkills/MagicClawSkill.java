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
import Sound.SoundManager;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 * The magicclawskill class. Standard implementation of an abstractactiveskill.
 */
public class MagicClawSkill extends AbstractActiveSkill{

    public MagicClawSkill(float x, float z) {
        super(x, z, 100, 200, "magicclaw");
    }

    @Override
    public int[] getEffectTimes() {
        return new int[]{0,25};
    }

    @Override
    public void skillEffect(Spatial s) {
        if (s instanceof AbstractMob){
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData(skillName));
            int dmg = CharacterHandler.calculateDamage(dat.getDamagePercentile(CharacterHandler.getSkillLevel(skillName)),(AbstractMob)s);
            ((AbstractMob)s).addHp(-dmg);
            DamageFactory.addDamage(boundMap,dmg,DamageFactory.RED,location);
            invokeHitSound();
        }
    }

    @Override
    public void invokeHitSound() {
        SoundManager.getChannel("Effects").addTrack("magicclaw");
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        CharacterAnimControl cont = new CharacterAnimControl(AssetManager.getSpriteSet("Effects"));
        cont.swapAnim("magicclaw");
        return cont;
    }
    
}
