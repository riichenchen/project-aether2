/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Mob.Empoleon;

/**
 *
 * @author Angus
 */
public class EmpoleonSpawner extends AbstractMobSpawner{

    public EmpoleonSpawner(float x, float y, float z, int timePerSpawn) {
        super(x, y, z, timePerSpawn);
    }

    @Override
    public AbstractMob getMob() {
        return new Empoleon(getX(),getY(),getZ());
    }
    
}
