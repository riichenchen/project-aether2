/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.UI;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Globals;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class DamageNumber extends CharacterSpatial{

    public DamageNumber(float x, float z,int type,int digit) {
        super(x, 1000, z,37,39,0,0,0,0);
        if (type == 1){ // make a swap if we have a dif type
            removeControl((CharacterAnimControl)getControl(CharacterAnimControl.class));
            addControl(new DamageAnimControl(AssetManager.getSpriteSet("damage/purple")));
        }
        ((CharacterAnimControl)getControl(CharacterAnimControl.class)).swapAnim(""+digit);
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        return new DamageAnimControl(AssetManager.getSpriteSet("damage/red"));
    }

    @Override
    public void collideEffect(Spatial s) {
        //should never occur
    }
    
}
