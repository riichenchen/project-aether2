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
 */
public class RenderChunk {
    private static int IDs = 0;
    private int id;
    private HashMap<Integer,RenderSpatial> objects;
    
    public RenderChunk(){
        objects = new HashMap<>();
        this.id = IDs++;
    }
    
    public int getId(){
        return id;
    }
    
    public void addObject(RenderSpatial spat){
        if (objects.containsKey(spat.getId())){
            System.out.println("Warning: Trying to add existant spatial to Map Chunk!");
            return;
        }
        objects.put(spat.getId(),spat);
        spat.getRenderChunks().put(id,this);
    }
    
    public void removeObject(RenderSpatial spat){
        if (!objects.containsKey(spat.getId())){
            System.out.println("Warning: Trying to remove existant spatial to Map Chunk!");
            return;
        }
        objects.remove(spat.getId());
        spat.getRenderChunks().remove(id);
    }
    
    public HashMap<Integer,RenderSpatial> getObjects(){
        return objects;
    }
}
