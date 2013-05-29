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
public class Stevey extends CharacterSpatial{
    public Stevey(float x,float y,float z){
        super(x,y,z,51f,77f,36f,1f,1f,1);
        addControl(new CharacterAnimControl(AssetManager.getSpriteSet("Steve")));
    }
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
//        if (s instanceof Portal){
//            System.out.println("YAY "+(n++));
//        }
    }
    
}
