/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Script;

import GameSource.User.CharacterHandler;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import java.util.ArrayList;

/**
 *
 * @author Shiyang
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
    
    public void doOutcomes(){
        for (FrameData outcome: outcomes){
            if (outcome.dataType.equals("additem")){
                InventoryItem temp = ItemFactory.getItem(outcome.values[0]);
                for (int i = 0; i < Integer.parseInt(outcome.values[1]);i++){
                    InventoryHandler.addItem(temp);
                }
            } else if (outcome.dataType.equals("exp")){
                CharacterHandler.addStat("exp", Integer.parseInt(outcome.values[0]));
            } //else if (outcome.dataType )
            //Todo: add alot more shit, complete quests, etc
        }
    }
    
    public String[] getButtons(){
        return buttons;
    }
}
