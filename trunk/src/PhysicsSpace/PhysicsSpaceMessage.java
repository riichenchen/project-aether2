/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

/**
 *
 * @author Robert
 */
public class PhysicsSpaceMessage {
    public final float x,y,z;
    public final int entity;
    public final String command;
    public PhysicsSpaceMessage(float x, float z, int entity){
        this.x = x;
        y = 0;
        this.z = z;
        this.entity = entity;
        command = "move";
    }
    public PhysicsSpaceMessage(float y, int entity){
        x = 0;
        this.y = y;
        z = 0;
        this.entity = entity;
        command = "move";
    }
    public PhysicsSpaceMessage(int entity){
        x = 0;
        y = 0;
        z = 0;
        this.entity = entity;
        command = "remove";
    }
}
