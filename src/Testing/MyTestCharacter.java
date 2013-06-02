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
public class MyTestCharacter extends CharacterSpatial{
    private int n = 0;
    public MyTestCharacter(float x,float y,float z){
        super(x,y,z,78f,95f,20f,1f,1f,0);
    }
    @Override
    public void collideEffect(Spatial s) {
//        if (AssetManager.SpatialToType(s)==AssetManager.SpatialToType(this)){
//            System.out.println("collide "+id+" "+(n++));   
//            System.out.println(getX()+" "+getZ()+","+s.getX()+" "+s.getZ());
//        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void update(){
        super.update();
//        System.out.println(location);
    }

    @Override
    public CharacterAnimControl getAnimControl() {
        return new CharacterAnimControl(AssetManager.getSpriteSet("MyTestAnimation"));
    }
    
}
