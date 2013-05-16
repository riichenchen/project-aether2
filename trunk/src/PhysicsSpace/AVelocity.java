/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

/**
 *
 * @author Shiyang
 */
public class AVelocity {
    private int x,y,z;
    public AVelocity(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public AVelocity(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getZ(){
        return z;
    }
    public void addX(int add){
        x+=add;
    }
    public void addY(int add){
        y+=add;
    }
    public void addZ(int add){
        z+=add;
    }
}
