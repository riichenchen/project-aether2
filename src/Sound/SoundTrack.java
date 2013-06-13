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
 * The sound track class encapsulates a player object as well as
 * provides each player it's own thread to play on.
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
    
    // standard get methods
    public Player getPlayer(){
        return player;
    }
    
    public int getSoundId(){
        return soundId;
    }
    
    public String getSoundName(){
        return soundName;
    }
    
    //The run method simply plays a player
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
    //Overridden interrupt - closes the player then interrupts the thread
    @Override
    public void interrupt(){
        player.close();
        super.interrupt();
    }
    //Returns whether the track is still playing or not
    public boolean isPlaying(){
        return !player.isComplete();
    }
}
