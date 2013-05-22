/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import java.util.HashMap;

/**
 *
 * @author Shiyang
 */
public class SpriteSet {
    private HashMap<String,AnimTrack> allTracks;
    public SpriteSet(){
        this.allTracks = new HashMap<>();
    }
    public void addTrack(String key,AnimTrack anim){
        allTracks.put(key, anim);
    }
    public AnimTrack get(String key){
        return allTracks.get(key);
    }
    public String getDefaultTrack(){
        //default track used as a basis for initialization
        return allTracks.keySet().iterator().next();
    }
}
