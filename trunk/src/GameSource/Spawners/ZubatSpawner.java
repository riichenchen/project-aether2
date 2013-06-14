/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Mob.Zubat;

/**
 *
 * @author Angus
 */
public class ZubatSpawner extends AbstractMobSpawner{

    public ZubatSpawner(float x, float y, float z, int timePerSpawn) {
        super(x, y, z, timePerSpawn);
    }

    @Override
    public AbstractMob getMob() {
        return new Zubat(getX(),getY(),getZ());
    }
    
}
