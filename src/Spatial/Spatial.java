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
//<<<<<<< .mine
    protected static int IDs = 0;
    protected int id;
//    protected float mass;
//=======
//     private int entity;
    public final float mass;
    public final float cof;      //coefficient of friction
    public final int collidable;    
    //                          0 -> not collidable
    //                          1 -> totally collidable
    //                          2 -> monster collidable
    //                          3 -> player collidable
    protected BoundingBox box;
    protected HashMap<Integer,AbstractControl> controls = new HashMap<>();
    
    public Spatial(float x, float y, float z, float length, float width, float height, float m, float c, int collidable){
        this.location = new GamePoint(x,y,z);
        mass = m;
        cof = c;
        box = new BoundingBox(length,width,height);
        this.id = IDs++;
        this.collidable = collidable;
    }
    
    public void move(float x, float y, float z){
        location.translate(x,y,z);
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
        float aYmin = location.getY();
        float aZmin = location.getZ() - box.getWidth()/2;
        
        float aXmax = location.getY() + box.getLength()/2;
        float aYmax = location.getY() + box.getHeight();
        float aZmax = location.getZ() + box.getWidth()/2;
        
        float bXmin = s.getX() - s.getBoundingBox().getLength()/2;
        float bYmin = s.getY();
        float bZmin = s.getZ() - s.getBoundingBox().getHeight()/2;
        
        float bXmax = s.getX() + s.getBoundingBox().getLength()/2;
        float bYmax = s.getY() + s.getBoundingBox().getHeight();
        float bZmax = s.getY() + s.getBoundingBox().getWidth();
        
        if (aXmin <= bXmax && aXmax >= bXmin && aYmin <= bYmax && aYmax >= bYmin && aZmin <= bZmax && aZmax >= bZmin){ 
            return true;
        }
        else{
            System.out.println("Nope");
            return false;
        }
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
    public AbstractControl getControl(){
    	if (controls.isEmpty()){
            return null;
    		
    	}
    	else{
            return controls.get(1);
    	}
    }
    public AbstractControl getControl(int id){
    	if (controls.containsKey(id)){
            return controls.get(id);
    	}
    	else{
            return null;
    	}
    }
    
    public void scale(float x, float y, float z){
    	box.setLength(box.getLength() + x);
    	box.setWidth(box.getWidth() + y);
    	box.setHeight(box.getHeight() + z);
    }
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
    public int getId(){
        return id;
    }
}
