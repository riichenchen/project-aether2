/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Portals;

import Controls.CharacterAnimControl;
import GameSource.Assets.AssetManager;
import GameSource.Game.GamePoint;
import GameSource.User.CharacterHandler;
import Spatial.CharacterSpatial;
import Spatial.Spatial;
import Testing.PlayerSpatial;

/**
 * The portal class creates a portal object. No specific classes should
 * extend this portal, as portals generally only perform 1 task -
 * take a player to a position on another map.
 * @author Shiyang
 */
public class Portal extends CharacterSpatial{
    private String toMap;

    private float tx,ty,tz; //the location on the new map
    
    public Portal(float x,float y,float z,String toMap,float tx,float ty,float tz){
        super(x,y,z,89f,50f,40f,1f,1f,0);
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.toMap = toMap;
    }
    
    public String getToMap() {
        return toMap;
    }
    
    private int n = 0;
    
    //Assuming only players can enter portals, the object checks if the Player collides
    //and sends that information to the static CharacterHandler class.
    @Override
    public void collideEffect(Spatial s) {
        if (location.distanceSquared(s.getLocation()) <= Math.pow(20,2) && getZ()-s.getZ() >= 0){
            if (s instanceof PlayerSpatial)
                CharacterHandler.setCurrentPortal(this);
        }
    }
    
    public GamePoint getNewPos(){
        return new GamePoint(tx,ty,tz);//return a new instance as keeping a pointer would be dangerous
    }

    @Override //All portals have the same animation effect
    public CharacterAnimControl getAnimControl() {
        return new CharacterAnimControl(AssetManager.getSpriteSet("Portal"));
    }
}
