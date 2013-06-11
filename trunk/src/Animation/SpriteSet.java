/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Animation;

import java.util.HashMap;

/**
 * The SpriteSet class keeps a handle on all AnimTracks that belong to it.
 * It is the medium by which the characteranimcontrol can manage an animation.
 * It provides an array of keys which correspond to tracks, as well as a hashmap
 * of all the tracks for O(1) access time
 * @author Shiyang
 */
public class SpriteSet {
    private HashMap<String,AnimTrack> allTracks;
    private AnimTrack[] trackArray;
    private String[] trackKeys;
    
    public SpriteSet(){
        this.allTracks = new HashMap<>();
    }
    //Store a track using String key k
    public void addTrack(String key,AnimTrack anim){
        allTracks.put(key, anim);
        trackArray = allTracks.values().toArray(new AnimTrack[0]);
        trackKeys = allTracks.keySet().toArray(new String[0]);
    }
    //get methods for getting track
    public AnimTrack get(String key){
        return allTracks.get(key);
    }
    public AnimTrack get(int key){
        return trackArray[key];
    }
    //Returns the key at id int
    //Note: the order of this array is dependant upon each individual
    //run.
    public String getKey(int id){
        return trackKeys[id];
    }
    //Returns the first track found in this set
    //Used by characteranimcontrol to make sure at least 1
    //track is being used
    public String getDefaultTrack(){
        //default track used as a basis for initialization
        return allTracks.keySet().iterator().next();
    }
    //Returns the number of tracks in this SpriteSet
    public int getNumTracks(){
        return allTracks.size();
    }
}
