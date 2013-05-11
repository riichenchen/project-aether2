/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSyncListener;

/**
 *
 * @author Robert
 */
public class PhysicsSyncMessage {
    public final int entity;
    public final float x,y,z;
    public PhysicsSyncMessage(float x,float z,float y, int entity){
        this.x = x;
        this.y = y;
        this.z = z;
        this.entity = entity;
    }
}
