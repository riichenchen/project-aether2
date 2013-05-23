/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class Portal extends CharacterSpatial{
    public Portal(float x,float y,float z){
        super(x,y,z,89f,50f,40f,1f,1f,0);
        addControl(new CharacterAnimControl(AssetManager.getSpriteSet("Portal")));
    }
    private int n = 0;
    @Override
    public void collideEffect(Spatial s) {
        if (location.distanceSquared(s.getLocation()) <= Math.pow(15,2) && getZ()-s.getZ() >= 0){
            System.out.println("IN! "+id+" "+(n++));
        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
