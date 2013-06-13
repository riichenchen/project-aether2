/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.util.ArrayList;
import java.util.HashMap;
import javazoom.jl.player.Player;

/**
 *
 * @author Shiyang
 * The sound channel class organizes and contains each track
 * it also provides a way to control the number of tracks being 
 * played at any given time for a particular area (such as effects)
 */
public class SoundChannel extends Thread{
    private ArrayList<SoundTrack> soundQueue; // all tracks being played
    private int numberTracks = 5; // 5 by default
    private int trackCounter = 0;
    private boolean loop;
    private static int IDs = 0;
    
    //takes in a boolean representing whether the tracks in this
    //channel loop or not
    public SoundChannel(boolean loop){
        this.soundQueue = new ArrayList<>(5);
        this.loop = loop;
    }
    
    public void addTrack(String newSound){
        if (trackCounter >= numberTracks){ // if there are too many tracks, pop out the last one
            SoundTrack oldTrack = soundQueue.get(0);
            soundQueue.remove(0);
            oldTrack.interrupt(); //stop the last track
            trackCounter--;
        }
        Player newPlayer = SoundManager.getTrack(newSound);//get a new Player object
        trackCounter++;
        SoundTrack newTrack = new SoundTrack(newPlayer,IDs++,newSound);
        
        soundQueue.add(newTrack);

        newTrack.start();
    }
    
    //Standard set and get methods
    public void setNumberTracks(int numberTracks){
        this.numberTracks = numberTracks;
    }
    
    public int getNumberTracks(){
        return numberTracks;
    }
    
    //this method stops all tracks playing in the channel and removes them.
    public void stopAll(){
        for (int i = 0; i < soundQueue.size(); i++){
            if (soundQueue.get(i) != null)
                soundQueue.get(i).interrupt();
        }
        trackCounter = 0;
        soundQueue.clear();
    }
    
    //Stops the first instance of a track it finds
    public void stopTrack(int soundId){
        for (int i = 0; i < soundQueue.size(); i++) 
            if (soundQueue.get(i) != null && soundQueue.get(i).getSoundId() == soundId){ 
                soundQueue.get(i).interrupt();
                soundQueue.remove(i); 
                trackCounter--;
                break;
            }
    }
    
    //The run method continually checks if a track needs to be refreshed (this channel loops)
    @Override
    public void run(){
        while (true){
            for (int i = 0; i < soundQueue.size(); i++){
                if (soundQueue.get(i) != null && !soundQueue.get(i).isPlaying()){
                    String name = soundQueue.get(i).getSoundName();
                    soundQueue.remove(i);
                    trackCounter--;
                    if (loop){
                        addTrack(name);
                    }
                    break;
                }
            }
        }
    }
    
}
