/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Spatial.Spatial;
import java.util.*;

/**
 *
 * @author Robert
 */
public abstract class PhysicsSpace {
    public HashMap <Integer,Spatial> spatials;
    public LinkedList <PhysicsSpaceMessage> msgs;
    private double gravity;
    
    public PhysicsSpace(double g){
        spatials = new HashMap <Integer,Spatial>();
        msgs = new LinkedList <PhysicsSpaceMessage> ();
        gravity = g;
    }
    public void update(){
        while (msgs.peek() != null){
            PhysicsSpaceMessage message  = msgs.poll();
//            if (message.){
//                
//            }
        }
    }
    public void setGravity(float g){
        gravity = g;
    }
    public void addSpatial(int id, Spatial s){
        spatials.put(id, s);
    }
    public void moveSpatial(int id, float x, float y, float z){
        spatials.get(id).move(x,y,z);
    }
    public void removeSpatial(int id){
        spatials.remove(id);
    }
}
