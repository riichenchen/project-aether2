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
    private boolean loop;
    private static int IDs = 0;
    
    public SoundChannel(boolean loop){
//        this.allTracks = new HashMap<>();
        this.soundQueue = new ArrayList<>(5);
        this.loop = loop;
    }
    
    public void addTrack(String newSound){
        if (soundQueue.size() >= numberTracks){
            SoundTrack oldTrack = soundQueue.get(0);
            soundQueue.remove(0);
            oldTrack.interrupt();
            
//            allTracks.remove(oldTrack.getSoundId());
        }
        Player newPlayer = SoundManager.getTrack(newSound);
        
        SoundTrack newTrack = new SoundTrack(newPlayer,IDs,newSound);
        
        soundQueue.add(newTrack);
//        allTracks.put(IDs++,newTrack);

        newTrack.start();
    }
    
    public void setNumberTracks(int numberTracks){
        this.numberTracks = numberTracks;
    }
    
    public int getNumberTracks(){
        return numberTracks;
    }
    
    public void stopAll(){
//        allTracks.clear();
        for (SoundTrack s: soundQueue){
            s.interrupt();
        }
        soundQueue.clear();
    }
    
    //Stops the first instance of a track it finds
    public void stopTrack(int soundId){
//        allTracks.remove(soundId);
        soundQueue.get(soundId).interrupt();
        soundQueue.remove(soundId);
    }
    
    @Override
    public void run(){
        while (true){
            for (SoundTrack s: soundQueue){
                if (!s.isAlive()){
                    soundQueue.remove(s.getSoundId());
                    if (loop){
                        addTrack(s.getSoundName());
                    }
                    break;
                }
            }
        }
    }
    
}
