/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import GameSource.Quest.QuestManager;
import GameSource.User.CharacterHandler;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import java.util.ArrayList;

/**
 * @author Shiyang
 * The ScriptFrame class handles a single frame
 * without considering any cases. If this frame has been hit,
 * it also provides an outcome - which are the effects which occur
 * when this frame has been browsed. It also holds the text that
 * this frame would say as well as this frame's buttons.
 */
public class ScriptFrame {
    private ArrayList<FrameData> outcomes;
    private String[] buttons;
    private String text = "";
    
    public ScriptFrame(String[] buttons) {
        this.buttons = buttons;
        this.outcomes = new ArrayList<>();
    }
    
    public void setText(String txt){
        this.text = txt;
    }
    
    public String getText(){
        return text;
    }
    
    public void addOutcome(FrameData outcome){
        this.outcomes.add(outcome);
    }
    
    //reads from all outcome data and applies them
    //one by one depending on the type of outcome
    //Fairly self explanatory by key
    public void doOutcomes(){
        for (FrameData outcome: outcomes){
            if (outcome.dataType.equals("additem")){
                InventoryItem temp = ItemFactory.getItem(outcome.values[0]);
                for (int i = 0; i < Integer.parseInt(outcome.values[1]);i++){
                    InventoryHandler.addItem(temp);
                }
            } else if (outcome.dataType.equals("takeitem")){
                InventoryItem temp = ItemFactory.getItem(outcome.values[0]);
                for (int i = 0; i < Integer.parseInt(outcome.values[1]);i++){
                    InventoryHandler.removeItem(temp);
                }
            } else if (outcome.dataType.equals("addexp")){
                CharacterHandler.addStat("exp", Integer.parseInt(outcome.values[0]));
            } else if (outcome.dataType.equals("addmoney")){
                CharacterHandler.addStat("money", Integer.parseInt(outcome.values[0]));
            } else if (outcome.dataType.equals("setqueststatus")){
                QuestManager.setQuestStatus(outcome.values[0],Integer.parseInt(outcome.values[1]));
            }
        }
    }
    
    //Returns all buttons on this frame
    public String[] getButtons(){
        return buttons;
    }
}
