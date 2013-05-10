/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Shiyang
 */
public class RenderChunk {
    private HashMap<Integer,RenderSpatial> objects;
    
    public RenderChunk(){
        objects = new HashMap<Integer,RenderSpatial>();
    }
    
    public void addObject(RenderSpatial spat){
        objects.put(spat.getId(),spat);
        spat.getChunks().add(this);
    }
    
    public void removeObject(RenderSpatial spat){
        objects.remove(spat.getId());
    }
    
    public HashMap<Integer,RenderSpatial> getObjects(){
        return objects;
    }
}
