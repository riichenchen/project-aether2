/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spatial;

/**
 *
 * @author Robert
 */
public class BoundingBox {
    float length,width,height;
    //float maxX , minX, maxY, minY, maxZ, minZ;
    
    public BoundingBox(float l, float h, float w){
        length = l;
        width = w;
        height = h;
    }
    public void setLength(float l){
        length = l;
    }
    public void setWidth(float w){
        width = w;
    }
    public void setHeight(float h){
        height = h;
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
}
