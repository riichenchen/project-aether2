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
 * The damage number class generates a number picture based upon the type passed in
 * and number passed in
 */
public class DamageNumber extends CharacterSpatial{

    public DamageNumber(float x, float z,int type,int digit) {
        super(x, 1000, z,37,39,0,0,0,0);
        if (type == 1){ // make a swap if we have a dif type
            removeControl((CharacterAnimControl)getControl(CharacterAnimControl.class));
            addControl(new DamageAnimControl(AssetManager.getSpriteSet("damage/purple"))); // swap to purple if necessary
        }
        ((CharacterAnimControl)getControl(CharacterAnimControl.class)).swapAnim(""+digit);
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        return new DamageAnimControl(AssetManager.getSpriteSet("damage/red")); //by default red coloured
    }

    @Override
    public void collideEffect(Spatial s) {
        //should never occur
    }
    
}
