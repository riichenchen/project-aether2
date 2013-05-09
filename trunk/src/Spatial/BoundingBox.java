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
    float x,y,z;
    float length,width,height;
    float maxX , minX, maxY, minY, maxZ, minZ;
    
    public BoundingBox(float x, float y, float z, float l, float w, float h){
        this.x = x;
        this.y = y;
        this.z = z;
        length = l;
        width = w;
        height = h;
        minX = x - length/2;
        minY = y - width/2;
        minZ = z;
        maxX = x + length/2;
        maxY = y + length/2;
        maxZ = z + length;
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
    public void translate(float x,float y,float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public float[] getMinMaxX(){
        return new float[]{minX,maxX};
    }
    public float[] getMinMaxY(){
        return new float[]{minY,maxY};
    }
    public float[] getMinMaxZ(){
        return new float[]{minZ,maxZ};
    }
}
