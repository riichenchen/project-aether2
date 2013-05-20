/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import Controls.AbstractControl;
import GameSource.Game.GamePoint;
import PhysicsSpace.AVelocity;
import java.io.Serializable;
import java.util.HashMap;
import java.awt.*;

/**
 *
 * @author Robert
 */
public abstract class Spatial {
    protected GamePoint location;
    protected static int IDs = 0;
    protected int id;
//    protected float mass;
//=======
//     private int entity;
    public final float mass;
    public final float cof;      //coefficient of friction
    protected int rotation;        //in degrees clockwise
    protected Point p1,p2,p3,p4;
    public final int collidable;    
    //                          0 -> not collidable
    //                          1 -> totally collidable
    //                          2 -> monster collidable
    //                          3 -> player collidable
    protected BoundingBox box;
    protected HashMap<Integer,AbstractControl> controls = new HashMap<>();
    protected AVelocity velocity;
    
    public Spatial(float x, float y, float z, float length, float height, float width, int r, float m, float c, int collidable){
        this.location = new GamePoint(x,y,z);
        this.velocity = new AVelocity(); // To add proper velocity later
        mass = m;
        cof = c;
        rotation = r;
        p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        p4 = new Point();
        box = new BoundingBox(length,height,width);
        this.id = IDs++;
        this.collidable = collidable;
    }
    /*SHOULD ONLY BE CALLED BY SERVER REGISTER!!!!!!!!!!!*/
    public void setId(int id){
        this.id = id;
    }
    
    public void move(float x, float y, float z){
        location.translate(x,y,z);
    }
    public abstract void collideEffect(Spatial s);
    
    public boolean collide(Spatial s){
        //if collision occurs: call the collideListener(pass in colliding object);
        //return false;
        Rectangle axz = new Rectangle ((int)(location.getX() - box.getLength()/2), (int)(location.getZ() - box.getWidth()/2), (int)box.getLength(), (int)box.getWidth());
        Rectangle bxz = new Rectangle ((int)(s.getX() - s.getBoundingBox().getLength()/2), (int)(s.getZ() - s.getBoundingBox().getWidth()/2), (int)s.getBoundingBox().getLength(), (int)s.getBoundingBox().getWidth());
        
        float aYmin = location.getY();
        float aYmax = location.getY() + box.getHeight();
        
        float bYmin = s.getY();
        float bYmax = s.getY() + s.getBoundingBox().getHeight();
        
        if (axz.intersects(bxz) || axz.contains(bxz)){
            if (aYmin <= bYmax && aYmax >= bYmin)
            {return true;}
            else if (aYmin >= bYmin && aYmax <= bYmax)
            {return true;}
            else{}return false;
        }
        else{}return false;
    }
    public boolean contains(Spatial s){
        Rectangle axy = new Rectangle ((int)(location.getX() - box.getLength()/2), (int)location.getY(), (int)box.getLength(), (int)box.getHeight());
        Rectangle ayz = new Rectangle ((int)location.getY(), (int)(location.getZ() - box.getWidth()), (int)location.getY(), (int)box.getWidth());
        Rectangle axz = new Rectangle ((int)(location.getX() - box.getLength()/2), (int)(location.getZ() - box.getWidth()/2), (int)box.getLength(), (int)box.getWidth());
        
        Rectangle bxy = new Rectangle ((int)(s.getX() - s.getBoundingBox().getLength()/2), (int)s.getY(), (int)(s.getX() + s.getBoundingBox().getLength()/2), (int)(s.getY() +  s.getBoundingBox().getHeight()));
        Rectangle byz = new Rectangle ((int)s.getY(), (int)(s.getZ() - s.getBoundingBox().getWidth()), (int)(s.getY() + s.getBoundingBox().getHeight()), (int)(s.getZ() + s.getBoundingBox().getWidth()/2));
        Rectangle bxz = new Rectangle ((int)(s.getX() - s.getBoundingBox().getLength()/2), (int)(s.getZ() - s.getBoundingBox().getWidth()/2), (int)s.getBoundingBox().getLength(), (int)s.getBoundingBox().getWidth());
        
        float aYmin = location.getY();
        float aYmax = location.getY() + box.getHeight();
        
        float bYmin = s.getY();
        float bYmax = s.getY() + s.getBoundingBox().getHeight();
        
        if (axz.intersects(bxz) || axz.contains(bxz)){
            if (aYmin >= bYmin && aYmax <= bYmax)
            {return true;}
            else{}return false;
        }
        else{}return false;
    }
    
    public GamePoint getLocation(){
        return location;
    }
    public BoundingBox getBoundingBox(){
        return box;
    }
    public int getRotation(){
        return rotation;
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
    public int getId(){
        return id;
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
}
