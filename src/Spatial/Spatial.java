/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import Controls.AbstractControl;
import GameSource.Game.GamePoint;
import java.util.HashMap;

/**
 *
 * @author Robert
 */
public abstract class Spatial {
    protected GamePoint location;
<<<<<<< .mine
    protected static int IDs = 0;
    protected int id;
    protected float mass;
=======
     private int entity;
    public final float mass;
    public final float cof;      //coefficient of friction
>>>>>>> .r28
    protected BoundingBox box;
    protected HashMap<Integer,AbstractControl> controls = new HashMap<>();
    
<<<<<<< .mine
    public Spatial(float x, float y, float z, float length, float width, float height, float m){
=======
    public Spatial(float x, float y, float z, float length, float width, float height, float m, float c, int entity){
>>>>>>> .r28
        this.location = new GamePoint(x,y,z);
        mass = m;
        cof = c;
        box = new BoundingBox(length,width,height);
<<<<<<< .mine
        this.id = IDs++;
=======
        this.entity = entity;
>>>>>>> .r28
    }
    public void move(float x, float y, float z){
        location.translate(x,y,z);
        //box.translate(x,y,z);
    }
    public GamePoint getLocation(){
        return location;
    }
    public BoundingBox getBoundingBox(){
        return box;
    }
    public abstract void collideEffect(Spatial s);
    
    public boolean collide(Spatial s){
        //if collision occurs: call the collideListener(pass in colliding object);
        //return false;
        float aXmin = location.getX() - box.getLength()/2;
        float aXmax = location.getY() - box.getWidth()/2;
        float aYmin = location.getZ();
        float aYmax = location.getX() + box.getLength()/2;
        float aZmin = location.getY() + box.getWidth()/2;
        float aZmax = location.getZ() + box.getHeight();
        
        float bXmin = s.getX() - s.getBoundingBox().getLength()/2;
        float bXmax = s.getY() - s.getBoundingBox().getWidth()/2;
        float bYmin = s.getZ();
        float bYmax = s.getX() + s.getBoundingBox().getLength()/2;
        float bZmin = s.getY() + s.getBoundingBox().getWidth()/2;
        float bZmax = s.getZ() - s.getBoundingBox().getHeight();
        
        if (aXmin < bXmax && aXmax > bXmin && aYmin < bYmax && aYmax > bYmin && aZmin < bZmax && aZmax > bZmin){ 
            return true;
        }
        else{
            return false;
        }
    }
    
    public int getEntity(){
        return entity;
    }
    public float getX(){
        return location.getX();
    }
    public float getY(){
        return location.getY();
    }
    public float getZ(){
        return location.getZ();
    }
    
    public void addControl(AbstractControl control){
        controls.put(control.getControlId(),control);
        control.bindToSpatial(this);
    }
    
    public void removeControl(AbstractControl control){
        controls.remove(control.getControlId());
    }
    
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
}
