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
 * The PhysicsChunk class manages a chunk of the overall map.
 * Each chunk contains spatials that overlap into the chunk. This provides
 * a virtual O(1) access time for modification and grabbing when regarding
 * collision detection. Although spatials may overlap in chunks, it's still
 * more efficient to remove when updating than an O(n^2) algorithm.
 */

public class PhysicsChunk {
    private static int IDs = 0; // ids used to track chunks made
    private int id;
    private HashMap<Integer,Spatial> objects;
    
    public PhysicsChunk(){
        objects = new HashMap<>();
        this.id = IDs++;
    }
    
    public int getId(){
        return id;
    }
    
    //Adds a spatial to this chunk
    public void addObject(Spatial s){
        if (! objects.containsKey(s.getId())){
            objects.put(s.getId(),s);
            s.getPhysicsChunks().put(id,this);
        } else {
            System.out.println("Warning: trying to add existing spatial to physics chunk!");
        }
        
    }
    //Removes a spatial from this chunk
    public void removeObject(Spatial s){
        if (!objects.containsKey(s.getId())){
            System.out.println("Warnind: Trying to remove non existant spatial from PhysicsChunk!");
            return;
        }        
        objects.remove(s.getId());
        s.getPhysicsChunks().remove(id);
    }
    //returns all spatials in this chunk
    public HashMap<Integer,Spatial> getObjects(){
        return objects;
    }
}
