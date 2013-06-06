/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.MobData;

import ArtificialIntelligence.AIControl;
import GameSource.game.GameMap;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractMob extends CharacterSpatial{
    AIControl control;
    
    public AbstractMob(float x, float y, float z, float l, float h, float w,int hp) {
        super(x, y, z, l, h, w, 0,0,0);
        setProperty("HP",hp);
        addControl(getAIControl());
        this.control = (AIControl)getControl(AIControl.class);
    }
    
    @Override
    public void bindToMap(GameMap map){
        super.bindToMap(map);
        control.bindToMap(map);
    }
    
    
    @Override
    public void unbindFromMap(){
        super.unbindFromMap();
        control.unbindFromMap();
    }
    
    public abstract AIControl getAIControl();
    
    @Override
    public void update(){
        super.update();
        if ((int)getProperty("HP") <= 0){
//            System.out.println((int)getProperty("HP"));
            dropItems();
            boundMap.removeMob(this);
        }
    }
    public abstract void dropItems();
    public void addHp(int diff){
        setProperty("HP",(int)getProperty("HP")+diff);
    }
}
