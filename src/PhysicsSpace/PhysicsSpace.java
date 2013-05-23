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
public class PhysicsSpace {
    public HashMap <Integer,Spatial> spatials;
    public LinkedList <PhysicsSpaceMessage> msgs;
    private PhysicsChunk[][] physicsMap;
    public final float gravity;
    public final int length, width;
    
    //Constants represent a scale factor by which to divide the entire "map" into
    //and organize spatials by      -taken from Renderer.java (ask shiyang)
    private final float S_QUAD = 0.05f;
    
    public PhysicsSpace(float g, int l, int w){
        length = l;
        width = w;
        gravity = g;
        spatials = new HashMap <>();
        msgs = new LinkedList <>();
        this.physicsMap = new PhysicsChunk[(int)(w*S_QUAD)][(int)(l*S_QUAD)];
        for (int i = 0; i < (int)(w*S_QUAD);i++){
            for (int j = 0; j < (int)(l*S_QUAD);j++){
                physicsMap[i][j] = new PhysicsChunk();
            }
        }
    }
    public void gravityEffect(){
        Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
        for (Spatial s: spatialsArray){
            addGravityMessage(s.mass * gravity * -1,s.getId());
        }
    }
    public boolean checkCollision(Spatial a){
    	Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
        for (Spatial b: spatialsArray){
            if (! a.equals(b)){
                if (a.collide(b) || b.collide(a)){
                    a.collideEffect(b);
                    b.collideEffect(a);
                    if (a.collidable == b.collidable){
                        
//                        System.out.println("collided");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void update(){
        Spatial[] spatialsArray = spatials.values().toArray(new Spatial[0]);
        for (Spatial a: spatialsArray){
            checkCollision(a);
        }
//        gravityEffect();
        resolveMessages();
    }
    public void resolveMessages(){
        while (msgs.peekFirst() != null){
            PhysicsSpaceMessage message = msgs.poll();
            if (message.command.equals("move")){
                if (spatials.containsKey(message.entity)){
                    if (! checkCollision(spatials.get(message.entity))){
                        moveSpatial(message.entity,message.x,message.y,message.z);
                    }
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
    public void addMoveMessage(float x, float z, int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(x,z,entity);
        msgs.addLast(message);
    }
    public void addGravityMessage(float y, int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(y,entity);
        msgs.addLast(message);
    }
    public void addJumpMessage(float y, int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(y,entity);
        msgs.addLast(message);
    }
    public void addRemoveMessage(int entity){
        PhysicsSpaceMessage message = new PhysicsSpaceMessage(entity);
        msgs.addLast(message);
    }
}
