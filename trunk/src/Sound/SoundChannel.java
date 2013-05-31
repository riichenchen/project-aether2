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
 */
public class SoundChannel extends Thread{
//    private HashMap<Integer,SoundTrack> allTracks;
    private ArrayList<SoundTrack> soundQueue;
    private int numberTracks = 5; // 5 by default
    private int trackCounter = 0;
    private boolean loop;
    private static int IDs = 0;
    
    public SoundChannel(boolean loop){
        this.soundQueue = new ArrayList<>(5);
        this.loop = loop;
    }
    
    public void addTrack(String newSound){
        if (trackCounter >= numberTracks){
            SoundTrack oldTrack = soundQueue.get(0);
            soundQueue.remove(0);
            oldTrack.interrupt();
            trackCounter--;
        }
        Player newPlayer = SoundManager.getTrack(newSound);
        trackCounter++;
        SoundTrack newTrack = new SoundTrack(newPlayer,IDs++,newSound);
        
        soundQueue.add(newTrack);

        newTrack.start();
    }
    
    public void setNumberTracks(int numberTracks){
        this.numberTracks = numberTracks;
    }
    
    public int getNumberTracks(){
        return numberTracks;
    }
    
    public void stopAll(){
        for (int i = 0; i < soundQueue.size(); i++){
            if (soundQueue.get(i) != null)
                soundQueue.get(i).interrupt();
        }
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
