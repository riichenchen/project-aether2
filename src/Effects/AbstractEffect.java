/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Effects;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import Spatial.CharacterSpatial;

/**
 *
 * @author Shiyang
 */
public abstract class AbstractEffect extends CharacterSpatial{
    private int limit;
    private int time = 0;
    public AbstractEffect(float x,float y,float z,float l,float h,float w,String effectName){
        super(x,y,z,l,h,w,0,0,0);
        CharacterAnimControl myEffect = new CharacterAnimControl(AssetManager.getSpriteSet("Effects"));
        myEffect.swapAnim(effectName);
        limit = myEffect.getLimit();
        addControl(myEffect);
    }
    
    @Override
    public void update(){
        super.update();
        time++;
        if (limit <= time){
            boundMap.removeSpatial(this);
        }
    }
}