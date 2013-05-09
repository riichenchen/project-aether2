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
    private LinkedList <PhysicsMessage> msgs;
    private PhysicsSpace space; 
    
    public PhysicsSyncListener(float g){
        msgs = new LinkedList <PhysicsMessage>();
        space = new PhysicsSpace(g);
    }
    public void update(){
        if (message.peek() != null){
            PhysicsMessage message = msgs.poll();
            if (message.){
                
            }
        }
    }
    public void moveSpatial()
}
