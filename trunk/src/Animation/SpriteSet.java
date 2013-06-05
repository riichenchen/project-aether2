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
    private AnimTrack[] trackArray;
    private String[] trackKeys;
    
    public SpriteSet(){
        this.allTracks = new HashMap<>();
    }
    public void addTrack(String key,AnimTrack anim){
        allTracks.put(key, anim);
        trackArray = allTracks.values().toArray(new AnimTrack[0]);
        trackKeys = allTracks.keySet().toArray(new String[0]);
    }
    public AnimTrack get(String key){
        return allTracks.get(key);
    }
    public AnimTrack get(int key){
        return trackArray[key];
    }
    public String getKey(int id){
        return trackKeys[id];
    }
    public String getDefaultTrack(){
        //default track used as a basis for initialization
        return allTracks.keySet().iterator().next();
    }
    public int getNumTracks(){
        return allTracks.size();
    }
}
