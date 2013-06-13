/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Shiyang
 * Similar to physics chunk, the render chunk stores all spatials that fall 
 * partially into it to allow for faster access time at the slight cost of memory.
 */
public class RenderChunk {
    private static int IDs = 0; // used to track the number of render chunks
    private int id;
    private HashMap<Integer,RenderSpatial> objects;
    
    public RenderChunk(){
        objects = new HashMap<>();
        this.id = IDs++;
    }
    
    public int getId(){
        return id;
    }
    
    //Adds the spatial into this chunk and binds the chunk to the spatial
    public void addObject(RenderSpatial spat){
        if (objects.containsKey(spat.getId())){
            System.out.println("Warning: Trying to add existant spatial to Map Chunk!");
            return;
        }
        objects.put(spat.getId(),spat);
        spat.getRenderChunks().put(id,this);
    }
    //Removes the spatial from this chunk and unbinds the chunk from the spatial
    public void removeObject(RenderSpatial spat){
        if (!objects.containsKey(spat.getId())){
            System.out.println("Warning: Trying to remove non existant spatial to Map Chunk!");
            System.out.println(spat.getMap().getName());
            return;
        }
        objects.remove(spat.getId());
        spat.getRenderChunks().remove(id);
    }
    //Returns all spatials in this chunk
    public HashMap<Integer,RenderSpatial> getObjects(){
        return objects;
    }
}
