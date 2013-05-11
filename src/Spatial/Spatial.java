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
    protected int id;
    protected float mass;
    protected BoundingBox box;
    protected HashMap<Integer,AbstractControl> controls = new HashMap<>();
    
    public Spatial(float x, float y, float z, float length, float width, float height, float m,int id){
        this.location = new GamePoint(x,y,z);
        mass = m;
        box = new BoundingBox(length,width,height);
        this.id = id;
    }
    public void move(int x, int y, int z){
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
        return false;
//        float aXmin = box.getMinMaxX()[0];
//        float aXmax = box.getMinMaxX()[1];
//        float aYmin = box.getMinMaxY()[0];
//        float aYmax = box.getMinMaxY()[1];
//        float aZmin = box.getMinMaxZ()[0];
//        float aZmax = box.getMinMaxZ()[1];
//        float bXmin = s.getBoundingBox().getMinMaxX()[0];
//        float bXmax = s.getBoundingBox().getMinMaxX()[1];
//        float bYmin = s.getBoundingBox().getMinMaxY()[0];
//        float bYmax = s.getBoundingBox().getMinMaxY()[1];
//        float bZmin = s.getBoundingBox().getMinMaxZ()[0];
//        float bZmax = s.getBoundingBox().getMinMaxZ()[1];
//        if (aXmin < bXmax && aXmax > bXmin && aYmin < bYmax && aYmax > bYmin && aZmin < bZmax && aZmax > bZmin){ 
//            return true;
//        }
//        else{
//            return false;
//        }
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
    
    public void update(){
        AbstractControl[] conts = controls.values().toArray(new AbstractControl[0]);
        for (AbstractControl c: conts){
            c.update();
        }
    }
}
