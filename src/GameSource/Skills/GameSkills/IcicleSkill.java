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
public class IcicleSkill extends AbstractActiveSkill{

    public IcicleSkill(float x, float z) {
        super(x, z, 132, 112,"icicle");
    }

    @Override
    public int[] getEffectTimes() {
        //return when the sprite "hits" the monster C:
        return new int[]{54,61,75};
    }

    @Override
    public void skillEffect(Spatial s) {
        if (s instanceof AbstractMob){
            ActiveSkillData dat = ((ActiveSkillData)AssetManager.getSkillData(skillName));
            int dmg = CharacterHandler.calculateDamage(dat.getDamagePercentile(CharacterHandler.getSkillLevel(skillName)),(AbstractMob)s);
            ((AbstractMob)s).addHp(dmg);
            DamageFactory.addDamage(boundMap,dmg,DamageFactory.RED,location);
        }
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        CharacterAnimControl cont = new CharacterAnimControl(AssetManager.getSpriteSet("Effects"));
        cont.swapAnim("icicle");
        return cont;
    }
    
}
