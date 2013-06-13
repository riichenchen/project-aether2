package Sound;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javazoom.jl.player.Player;

/**
 * @author Shiyang Han
 * The SoundManager manages all sound assets that are part of the game.
 * It has a handle on all channels as well as a hashmap that maps a sound key
 * to a directory.
 */
public class SoundManager {
    private static HashMap<String,SoundChannel> channels = new HashMap<>();
    private static HashMap<String,String> allSound = new HashMap<>();
    
    // The init method loads in all sound keys to their corresponding directories
    public static void init(){
        try{
            BufferedReader in = new BufferedReader(new FileReader("src/Sound/SoundFiles.txt"));
            String line;
            String directory = "src/Sound/SoundFiles/";
            while ((line = in.readLine())!= null){
                String[] data = line.split(" ");
                
                allSound.put(data[0],directory+data[1]);
            }
        } catch (IOException e){
            System.out.println("Failed to load sound data!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    //Add channel method creates a channel using a specified key and loop variable
    public static void addChannel(String identifier,boolean loop){
        SoundChannel newChannel = new SoundChannel(loop);
        if (channels.containsKey(identifier)){ // breaks if the channel already exists
            System.out.println("SEVERE: key "+identifier+" already exists!");
            System.exit(0);
        }
        channels.put(identifier,newChannel);
        newChannel.start();
    }
    
    //Returns a channel mapped to the specified key
    public static SoundChannel getChannel(String identifier){
        return channels.get(identifier);
    }
    
    //Creates a player object using the directory mapped to a passed in key
    public static Player getTrack(String identifier){
        String id = allSound.get(identifier);
        try { 
            return new Player(new FileInputStream(allSound.get(identifier)));
        } catch (Exception e){
            System.out.println("Error loading in soundtrack \""+identifier+"\"");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}

