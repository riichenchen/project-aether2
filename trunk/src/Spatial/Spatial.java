/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

/**
 *
 * @author Robert
 */
public abstract class Spatial {
    private float x,z,y;
    private int id;
    private float mass;
    private BoundingBox box;
    
    public Spatial(float x, float y, float z, float length, float width, float height, float m){
        this.x = x;
        this.y = y;
        this.z = z;
        mass = m;
        box = new BoundingBox(x,y,z,length,width,height);
    }
    public void move(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        box.translate(x,y,z);
    }
    public BoundingBox getBoundingBox(){
        return box;
    }
    public abstract collideListener()
    public boolean collide(Spatial s){
        float aXmin = box.getMinMaxX()[0];
        float aXmax = box.getMinMaxX()[1];
        float aYmin = box.getMinMaxY()[0];
        float aYmax = box.getMinMaxY()[1];
        float aZmin = box.getMinMaxZ()[0];
        float aZmax = box.getMinMaxZ()[1];
        float bXmin = s.getBoundingBox().getMinMaxX()[0];
        float bXmax = s.getBoundingBox().getMinMaxX()[1];
        float bYmin = s.getBoundingBox().getMinMaxY()[0];
        float bYmax = s.getBoundingBox().getMinMaxY()[1];
        float bZmin = s.getBoundingBox().getMinMaxZ()[0];
        float bZmax = s.getBoundingBox().getMinMaxZ()[1];
        if (aXmin < bXmax && aXmax > bXmin && aYmin < bYmax && aYmax > bYmin && aZmin < bZmax && aZmax > bZmin){ 
            return true;
        }
        else{
            return false;
        }
    }
}
