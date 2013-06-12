/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Effects;

import Effects.AbstractEffect;
import GameSource.Assets.DamageFactory;
import GameSource.Assets.MobData.AbstractMob;
import Spatial.Spatial;
import java.util.Random;

/**
 * a test icy effect in the game. (The first abstracteffect prototype)
 * Not exactly used in the game.
 * @author Shiyang
 */
public class IceyEffect extends AbstractEffect{

    public IceyEffect(float x, float y, float z, float l, float h, float w, String effectName) {
        super(x, y, z, l, h, w, effectName);
    }

    public IceyEffect(float x, float y, float z) {
        super(x, y, z, 181, 102, 50, "icysprite");
    }

    @Override
    public void collideEffect(Spatial s) { // testing for damage
        if (limit<=time+1 && s instanceof AbstractMob){
            Random myrandom = new Random();
            int dmg = myrandom.nextInt(100000)+100;
            ((AbstractMob)s).addHp(-dmg);
            DamageFactory.addDamage(boundMap, dmg, DamageFactory.RED, location);
        }
    }
    
}
