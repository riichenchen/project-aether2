/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import java.util.LinkedList;

/**
 * @author Shiyang
 * The NPCFrame combines all scriptdata, frame case as well as scriptframe into
 * a single, simple to manage class that handles all npc chatting. The GUI
 * simply needs to call upon this class and display the returned information
 * and all quest effects and frame flow is handled independently.
 */
public class NPCFrame {
    private ScriptData data;
    private int currentFrameNumber = -1; //start at frame -1 (no frame)
    private LinkedList<Integer> history = new LinkedList<>();
    private ScriptFrame currentFrame;
    private String key;
    
    public NPCFrame(String framename) { //takes in a frame name and retrieves that frame's script data
        this.key = framename;
        this.data = AssetManager.getScriptData(framename);
    }
    
    public String [] getButtons(){
        return currentFrame.getButtons();
    }
    public String getText(){ //returns the current frame's text
        return currentFrame.getText();
    }
    
    //if the next button is clicked, attempt to advance a frame only if the button is allowed
    public String next(){
        if (currentFrameNumber == -1 || !currentFrame.getButtons()[2].equals("null")){ // first frame guarantees it can go further
            history.add(currentFrameNumber);
            currentFrameNumber = data.getFrameNumber(currentFrameNumber);
            if (currentFrameNumber == -2){
                System.out.println("Warning: this NPCFrame instance has finished!");
                return null;
            }
            currentFrame = data.getFrame(currentFrameNumber);
            currentFrame.doOutcomes();
            return currentFrame.getText();
        }
        System.out.println("Warning: this frame doesn't support a next button!");
        return null;
    }
    
    //if the previous button is clicked, return a frame in history if the button is allowed
    public String prev(){
        if (currentFrameNumber == -2){
            System.out.println("Warning: this NPCFrame instance has finished!");
            return null;
        }
        if (!currentFrame.getButtons()[1].equals("null")){
            currentFrameNumber = history.pollLast(); // pop from the frame stack
            currentFrame = data.getFrame(currentFrameNumber);
            currentFrame.doOutcomes();
            return currentFrame.getText();
        }
        System.out.println("Warning: this frame doesn't support a previous button!");
        return null;
    }
    
    //the end chat button ends the chat if it's supported
    public String endChat(){
        if (currentFrameNumber == -2){
            System.out.println("Warning: this NPCFrame instance has fininished!");
            return null;
        }
        if (!currentFrame.getButtons()[0].equals("null")){
            currentFrameNumber = data.getFinalFrame();
            currentFrame = data.getFrame(currentFrameNumber);
            currentFrame.doOutcomes();
            return currentFrame.getText();
        }
        System.out.println("Warning: this frame doesn't support a endChat button!");
        return null;
    }
    //The getkey method returns this object's npc script's key
    public String getKey(){
        return key;
    }
}
