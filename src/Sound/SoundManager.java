package Sound;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javazoom.jl.player.Player;

/**
 * Author: Shiyang Han
 */
public class SoundManager {
    private static HashMap<String,SoundChannel> channels = new HashMap<>();
    private static HashMap<String,String> allSound = new HashMap<>();
    
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
    
    public static void addChannel(String identifier,boolean loop){
        SoundChannel newChannel = new SoundChannel(loop);
        if (channels.containsKey(identifier)){
            System.out.println("Warning: key "+identifier+" already exists!");
            System.exit(0);
        }
        channels.put(identifier,newChannel);
        newChannel.start();
    }
    
    public static SoundChannel getChannel(String identifier){
        return channels.get(identifier);
    }
    
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
    
    public static void main(String[] args){
        SoundManager.init();
        SoundManager.addChannel("MyChannel", false);
        SoundChannel MyChannel = SoundManager.getChannel("MyChannel");
        MyChannel.setNumberTracks(1);
        MyChannel.addTrack("soundTest1");
        while (true){
            //Add some delay in (for the effect)
            for (int i = 0; i < 1000000000; i++){
                for (int j = 0; j < 10; j++){
                    int blah = i;
                }
            }
            
            MyChannel.addTrack("soundTest1");
        }
    }
}

