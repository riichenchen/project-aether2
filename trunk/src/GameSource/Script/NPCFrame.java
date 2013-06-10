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
 *
 * @author Shiyang
 */
public class NPCFrame {
    private ScriptData data;
    private int currentFrameNumber = -1;
    private LinkedList<Integer> history = new LinkedList<>();
    private ScriptFrame currentFrame;
    private String key;
    
    public NPCFrame(String framename) {
        this.key = framename;
        this.data = AssetManager.getScriptData(framename);
    }
    public String [] getButtons(){
        return currentFrame.getButtons();
    }
    public String getText(){
        return currentFrame.getText();
    }
    
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
    
    public String prev(){
        if (currentFrameNumber == -2){
            System.out.println("Warning: this NPCFrame instance has finished!");
            return null;
        }
        if (!currentFrame.getButtons()[1].equals("null")){
            currentFrameNumber = history.pollLast();
            currentFrame = data.getFrame(currentFrameNumber);
            currentFrame.doOutcomes();
            return currentFrame.getText();
        }
        System.out.println("Warning: this frame doesn't support a previous button!");
        return null;
    }
    
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
    
    public static void main(String[] args){
        AssetManager.init();
        CharacterHandler.init();
        CharacterHandler.addStat("level", 11);
        InventoryHandler.addItem(ItemFactory.getItem("trollbaithelm"),10);
        NPCFrame myframe = new NPCFrame("john");
        while (myframe.next() != null);
        System.out.println(CharacterHandler.getStat("exp"));
        System.out.println(CharacterHandler.getStat("money"));
        System.out.println(InventoryHandler.getItemQuantity("trollbaithelm"));
    }
    public String getKey(){
        return key;
    }
}
