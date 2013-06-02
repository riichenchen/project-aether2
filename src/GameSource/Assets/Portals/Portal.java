/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Portals;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Game.GamePoint;
import Spatial.CharacterSpatial;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class Portal extends CharacterSpatial{
    private String toMap;
    private float tx,ty,tz;
    
    public Portal(float x,float y,float z,String toMap,float tx,float ty,float tz){
        super(x,y,z,89f,50f,40f,1f,1f,0);
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        addControl(new CharacterAnimControl(AssetManager.getSpriteSet("Portal")));
    }
    private int n = 0;
    
    @Override
    public void collideEffect(Spatial s) {
        if (location.distanceSquared(s.getLocation()) <= Math.pow(20,2) && getZ()-s.getZ() >= 0){
//            System.out.println("IN! "+id+" "+(n++));
        }
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public GamePoint getNewPos(){
        return new GamePoint(tx,ty,tz);
    }
    
}
