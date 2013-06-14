/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Mob.Dusknoir;

/**
 *
 * @author Angus
 */
public class DusknoirSpawner extends AbstractMobSpawner{

    public DusknoirSpawner(float x, float y, float z, int timePerSpawn) {
        super(x, y, z, timePerSpawn);
    }

    @Override
    public AbstractMob getMob() {
        return new Dusknoir(getX(),getY(),getZ());
    }
    
}
