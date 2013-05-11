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
    private HashMap<Integer,RenderSpatial> objects;
    
    public RenderChunk(){
        objects = new HashMap<>();
    }
    
    public void addObject(RenderSpatial spat){
        objects.put(spat.getEntity(),spat);
        spat.getChunks().add(this);
    }
    
    public void removeObject(RenderSpatial spat){
        objects.remove(spat.getEntity());
    }
    
    public HashMap<Integer,RenderSpatial> getObjects(){
        return objects;
    }
}
