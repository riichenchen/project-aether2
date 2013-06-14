/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Mob.Arcanine;

/**
 *
 * @author Angus
 */
public class ArcanineSpawner extends AbstractMobSpawner{

    public ArcanineSpawner(float x, float y, float z, int timePerSpawn) {
        super(x, y, z, timePerSpawn);
    }

    @Override
    public AbstractMob getMob() {
        return new Arcanine(getX(),getY(),getZ());
    }
    
}
