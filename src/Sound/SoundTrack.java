/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Shiyang
 */
public class SoundTrack extends Thread{
    private Player player;
    private int soundId;
    private String soundName;
    
    public SoundTrack(Player player,int soundId,String soundName){
        this.player = player;
        this.soundId= soundId;
        this.soundName = soundName;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public int getSoundId(){
        return soundId;
    }
    
    public String getSoundName(){
        return soundName;
    }
    
    @Override
    public void run(){
        try {
            player.play();
        } catch (JavaLayerException e) {
            System.out.println("SEVERE: Unable to play music!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    @Override
    public void interrupt(){
        player.close();
        super.interrupt();
    }
    public boolean isPlaying(){
        return !player.isComplete();
    }
}
