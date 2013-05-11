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
    public final float gravity;
    
    public PhysicsSpace(float g){
        spatials = new HashMap <>();
        msgs = new LinkedList <>();
        gravity = g;
    }
    public void update(){
        gravityEffect();
        resolveMessages();
    }
    public void resolveMessages(){
        while (msgs.peekFirst() != null){
            PhysicsSpaceMessage message = msgs.poll();
            if (message.command.equals("move")){
                if (spatials.containsKey(message.entity)){
                    moveSpatial(message.entity,message.x,message.y,message.z);
                }
            }
            if (message.command.equals("remove")){
                removeSpatial(message.entity);
            }
        }
    }
    public void addSpatial(int entity, Spatial s){
        spatials.put(entity, s);
    }
    public void moveSpatial(int entity, float x, float y, float z){
        spatials.get(entity).move(x,y,z);
    }
    public void removeSpatial(int entity){
        spatials.remove(entity);
    }
    public void addMoveMessage(float x, float y, float z, int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(x,y,z,entity);
        msgs.addLast(message);
    }
    public void addGravityMessage(float z, int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(z,entity);
        msgs.addLast(message);
    }
    public void addRemoveMessage(int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(entity);
        msgs.addLast(message);
    }
    public void gravityEffect(){
        Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
        for (Spatial s: spatialsArray){
            addGravityMessage(s.mass * gravity * -1,s.getEntity());
        }
    }
}
