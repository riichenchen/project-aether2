/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Spawners;

import GameSource.Assets.MobData.AbstractMob;
import Spatial.Spatial;

/**
 * The tempate for a mob spawner class. A new mob instance must be provided
 * in the getmob method for all spawners as well as the time-per-spawn
 * @author Shiyang
 */
public abstract class AbstractMobSpawner extends Spatial{
    private int timeCounter = 0;
    private int timePerSpawn;
    
    public AbstractMobSpawner(float x, float y, float z,int timePerSpawn) {
        super(x, y, z,0,0,0,0,0,0);//Yeap. No dimensions C:
        this.timePerSpawn = timePerSpawn;
    }

    @Override
    //Note: this will probably throw an error if misused
    //Ie: Add it as a physics object :c (since volume is essentially 0)
    public void collideEffect(Spatial s) {}
    //Therefore, DO NOT EVER ADD TO PHYSICSSPACE :C
    
    @Override
    public void update(){
        super.update();
        timeCounter = (timeCounter+1)%timePerSpawn; // wrap around
        if (timeCounter == 0){
            //Attempt to spawn a mob -> Map determines whether it happens
            boundMap.spawnMob(this);
        }
    }
    
    public abstract AbstractMob getMob();
    
}
