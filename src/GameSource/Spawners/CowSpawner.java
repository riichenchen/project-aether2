/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import GameSource.Assets.Spawners.AbstractMobSpawner;
import GameSource.Mob.BillysCow;

/**
 *
 * @author Shiyang
 * First implementation ever of a mobspawner.
 * Yay!!! Spawns a billys cow :33
 */
public class CowSpawner extends AbstractMobSpawner{

    public CowSpawner(float x, float y, float z, int timePerSpawn) {
        super(x, y, z, timePerSpawn);
    }

    @Override
    public AbstractMob getMob() {
        return new BillysCow(getX(),getY(),getZ());
    }
    
}
