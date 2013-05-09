/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Renderer;

import java.util.HashMap;

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
    }
    public void removeObject(RenderSpatial spat){
        objects.remove(spat.getId());
    }
}
