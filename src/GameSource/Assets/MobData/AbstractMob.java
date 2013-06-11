/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.MobData;

import ArtificialIntelligence.AIControl;
import GameSource.Quest.QuestManager;
import GameSource.User.CharacterHandler;
import GameSource.game.GameMap;
import Spatial.CharacterSpatial;

/**
 * The abstract mob class provides a template for every mob in the game
 * Any instance must provide for an update method as well as specific stats pertaining to the monster
 * as well as an AI control
 * @author Shiyang
 */
public abstract class AbstractMob extends CharacterSpatial{
    private AIControl control;
    
    public AbstractMob(float x, float y, float z, float l, float h, float w,int hp) {
        super(x, y, z, l, h, w, 0,0,0); // ignore mass and other defining physics traits
        setProperty("HP",hp); // mobs must have an hp, may add more later
        addControl(getAIControl());
        this.control = (AIControl)getControl(AIControl.class); // ai control to this
    }
    
    @Override
    public void bindToMap(GameMap map){
        super.bindToMap(map); // bind control to map as well
        control.bindToMap(map);
    }
    
    
    @Override
    public void unbindFromMap(){
        super.unbindFromMap(); // unbind (remove spat from map as well)
        control.unbindFromMap();
    }
    
    //Abstract methods that children must implement
    public abstract AIControl getAIControl();
    public abstract String getName();
    public abstract void dropItems();
    public abstract int getExp();
    
    @Override
    public void update(){
        super.update();
        if ((int)getProperty("HP") <= 0){ // Remove this mob if it's hp is less than 0
//            System.out.println((int)getProperty("HP"));
            CharacterHandler.addStat("exp",getExp());
            QuestManager.addMobKill(this);
            dropItems();
            boundMap.removeMob(this);
        }
    }
    
    //The addHP method. Self explanatory
    public void addHp(int diff){
        setProperty("HP",(int)getProperty("HP")+diff);
    }
}
