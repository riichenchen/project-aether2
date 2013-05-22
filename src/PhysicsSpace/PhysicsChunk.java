/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsSpace;

import Spatial.Spatial;
import java.util.HashMap;

/**
 *
 * @author Robert
 */
public class PhysicsChunk {
    private static int IDs = 0;
    private int id;
    private HashMap<Integer,Spatial> objects;
    
    public PhysicsChunk(){
        objects = new HashMap<>();
        this.id = IDs++;
    }
    public int getId(){
        return id;
    }
    public void addObject(Spatial s){
        if (! objects.containsKey(s.getId())){
            objects.put(s.getId(),s);
            s.getPhysicsChunks().put(id,this);
        }
        
    }
    public void removeObject(Spatial s){
        if (objects.containsKey(s.getId())){
            objects.remove(s.getId());
            s.getPhysicsChunks().remove(id);
        }        
    }
    public HashMap<Integer,Spatial> getObjects(){
        return objects;
    }
}
