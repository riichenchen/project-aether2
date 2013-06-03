/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.Portals.Portal;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class Stevey extends CharacterSpatial{
    private Portal currentPort = null;
    
    public Stevey(float x,float y,float z){
        super(x,y,z,51f,77f,36f,1f,1f,1);
        setProperty("currentPortal",null);
    }
    
    int n = 0;
    @Override
    public void collideEffect(Spatial s) {
//        if (s instanceof Portal){
//            System.out.println("YAY "+(n++));
//        }
    }
    @Override
    public void update(){
        super.update();
        setProperty("currentPortal",null);
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        return new CharacterAnimControl(AssetManager.getSpriteSet("Steve"));
    }
    
}