/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

import Controls.AbstractControl;
import GameSource.Assets.AssetManager;
import GameSource.Assets.TerrainBlocks.Blocks.DirtBlock.Dirt_Block;
import GameSource.Game.GamePoint;
import GameSource.Input.PlayerKeyListener;
import PhysicsSpace.AVelocity;
import java.util.HashMap;
import java.awt.*;

/**
 *
 * @author Robert
 */
public abstract class Spatial {
    protected GamePoint location;
    protected float length, height, width;
    protected static int IDs = 0;
    protected int id;
//    protected float mass;
//=======
//     private int entity;
    public final float mass;
    public final float cof;      //coefficient of friction
    protected double rotation;        //in degrees clockwise
    public final int collidable;    
    //                          0 -> not collidable
    //                          1 -> totally collidable
    //                          2 -> monster collidable
    //                          3 -> player collidable
    protected HashMap<Integer,AbstractControl> controls = new HashMap<>();
    protected AVelocity velocity;

    public Spatial(float x, float y, float z, float l, float h, float w, float m, float c, int collidable){
        this.location = new GamePoint(x,y,z);
        length =l;
        height = h;
        width = w;
        this.velocity = new AVelocity(); // To add proper velocity later
        mass = m;
        cof = c;
        rotation = 0;
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
        
        boolean collide = false;
        boolean rotated = false;
        double arot = rotation;
        
        float aYmin = location.getY();
        float aYmax = location.getY() + height;
        
        float bYmin = s.getY();
        float bYmax = s.getY() + s.getHeight();
        
        if (arot != 0){
            s.setLocalRotation(-arot);
            rotated = true;
        }
        if (s.getShape().intersects((double)(location.getX()-length/2),(double)(location.getZ()-width/2),(double)length,(double)width) || s.getShape().contains((double)(location.getX()-length/2),(double)(location.getZ()-width/2),(double)length,(double)width)){
            collide = true;
        }
        if (rotated){
            s.setLocalRotation(arot);
        }
        
        if (collide){
            if (aYmin <= bYmax && aYmax >= bYmin)
            {return true;}
            else if (aYmin >= bYmin && aYmax <= bYmax)
            {return true;}
            else{}return false;
        }
        else{}return false;
//        Rectangle axz = new Rectangle ((int)(location.getX() - length/2), (int)(location.getZ() - width/2), (int)length, (int)width);
//        Rectangle bxz = new Rectangle ((int)(s.getX() - s.getLength()/2), (int)(s.getZ() - s.getWidth()/2), (int)s.getLength(), (int)s.getWidth());
//        
//        float aYmin = location.getY();
//        float aYmax = location.getY() + height;
//        
//        float bYmin = s.getY();
//        float bYmax = s.getY() + s.getHeight();
//        
//        if (axz.intersects(bxz) || axz.contains(bxz)){
//            if (aYmin <= bYmax && aYmax >= bYmin)
//            {return true;}
//            else if (aYmin >= bYmin && aYmax <= bYmax)
//            {return true;}
//            else{}return false;
//        }
//        else{}return false;
    }
    public boolean contains(Spatial s){
        Rectangle axz = new Rectangle ((int)(location.getX() - length/2), (int)(location.getZ() - width/2), (int)length, (int)width);
        Rectangle bxz = new Rectangle ((int)(s.getX() - s.getLength()/2), (int)(s.getZ() - s.getWidth()/2), (int)s.getLength(), (int)s.getWidth());
        
        float aYmin = location.getY();
        float aYmax = location.getY() + height;
        
        float bYmin = s.getY();
        float bYmax = s.getY() + s.getHeight();
        
        if (axz.contains(bxz)){
            if (aYmin >= bYmin && aYmax <= bYmax)
            {return true;}
            else{}return false;
        }
        else{}return false;
    }
    
    public GamePoint getLocation(){
        return location;
    }
    public double getRotation(){
        return Math.toDegrees(rotation);
    }
    public void setRotation(double r){
        rotation = Math.toRadians(r) % (2*Math.PI);
    }
    public void setLocalRotation(double r){
        rotation = (rotation + Math.toRadians(r)) % (2*Math.PI);
    }
    public Polygon getShape(){
        double angle1 = Math.PI/2 - rotation - Math.atan(length/width);
        double angle2 = rotation - Math.atan(length/width);
        double dis = Math.hypot(length/2,width/2);
        
        Point p1 = new Point((int)(location.getX() + dis * Math.sin(angle2)),(int)(location.getZ() - dis * Math.cos(angle2)));
        Point p2 = new Point((int)(location.getX() + dis * Math.cos(angle1)),(int)(location.getZ() - dis * Math.sin(angle1)));
        Point p3 = new Point((int)(location.getX() - dis * Math.sin(angle2)),(int)(location.getZ() + dis * Math.cos(angle2)));
        Point p4 = new Point((int)(location.getX() - dis * Math.cos(angle1)),(int)(location.getZ() + dis * Math.sin(angle1)));
      
        int[] xpoints = new int[] {p1.x,p2.x,p3.x,p4.x};
        int[] zpoints = new int[] {p1.y,p2.y,p3.y,p4.y};
        
        Polygon shape = new Polygon(xpoints, zpoints, 4);
        return shape;
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
    public float getLength(){
        return length;
    }
    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }
    public int getId(){
        return id;
    }
    
    public void addControl(AbstractControl control){
        control.bindToSpatial(this);
        controls.put(control.getControlId(),control);
    }
    
    public void removeControl(AbstractControl control){
        controls.remove(control.getControlId());
    }
    
    //Removes the first instance of a specific class seen.
    public void removeControl(Class<?> controlType){
        for (AbstractControl c: controls.values().toArray(new AbstractControl[0])){
            if (controlType.isInstance(c)){
                controls.remove(c.getControlId());
                return;
            }
        }
    }
    
    public AbstractControl getControl(){
    	if (controls.isEmpty()){
            return null;
    	}
    	else{
            return controls.values().toArray(new AbstractControl[0])[0];
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
    
    //Based on a class type, returns the first instance of the controltype found or null if it doesn't exist
    public Object getControl(Class<?> controlType){
        for (AbstractControl c: controls.values().toArray(new AbstractControl[0])){
            if (controlType.isInstance(c)){
                return controlType.cast(c);
            }
        }
        return null;
    }
    
    public void scale(float x, float y, float z){
    	length += x;
    	width += z;
    	height += y;
    }
    
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
}
