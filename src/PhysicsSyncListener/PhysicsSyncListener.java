/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSyncListener;

import java.util.*;
import PhysicsSpace.PhysicsSpace;

/**
 *
 * @author Robert
 */
public class PhysicsSyncListener {
    private LinkedList <PhysicsSyncMessage> msgs;
    private PhysicsSpace space;   
    
    public PhysicsSyncListener(float g, int l, int w){
        msgs = new LinkedList <>();
        space = new PhysicsSpace(g,l,w) {};
    }
    public void update(){
        if (msgs.peek() != null){
            PhysicsSyncMessage message = msgs.poll();
            //space.addMoveMessage(message.x,message.y,message.z,message.entity);
        }
    }
    public void moveSpatial(float x, float y, float z, int entity){
        PhysicsSyncMessage msg = new PhysicsSyncMessage(x,y,z,entity);
        msgs.addLast(msg);
    }
}
