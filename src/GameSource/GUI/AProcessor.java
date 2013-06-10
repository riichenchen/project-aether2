/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;

/**
 *
 * @author Joy
 */
public class AProcessor {
    
    
    public static void process(AMessage m){
        switch (m.type()){
            case AMessage.QUIT: System.out.println("QUIT GAME");
                break;
            case AMessage.OPEN_WINDOW: MyGUI.openWindow(m.content());
                break;
            case AMessage.CLOSE_WINDOW: MyGUI.closeWindow(m.content());
                break;
            case AMessage.EQUIP: System.out.println("Equip: "+m.content());
                break;
            case AMessage.UNEQUIP: System.out.println("Unequip: "+m.content());
                break;
            case AMessage.USE_ITEM: 
                break;
            case AMessage.NPC_CHAT: process_npcchat(m);
                break;
            case AMessage.SEND_MESSAGE: System.out.println(m.content());
                break;
            case AMessage.INVENTORY: MyGUI.changeInventPane(m.content());
                break;
            case AMessage.SKILLS: MyGUI.changeSkillsPane(m.content());
                break;
            case AMessage.NULL: break;
            case AMessage.INVENTORY_CLICK: process_invent(m);
                break;
            case AMessage.STAT_WINDOW: CharacterHandler.addStat(m.content(), 1);
                CharacterHandler.addStat("statPoints", -1);
                MyGUI.updateStatWindow();
                break;
            default: System.out.println("uselessButton");
        }    
    }
    private static void process_invent(AMessage m){
        if (AMouseInput.doubleclick[AMouseInput.LEFT]==AMouseInput.YES){
            System.out.println("Use "+m.content());
        }
        else{
            MyGUI.bindToMouse(InventoryHandler.getItem(m.content()));
        }
    }
    public static void process_npcchat(AMessage m){
        if (m.content().equals("next") ||m.content().equals("okay")){
            MyGUI.npc_next();
        }else if (m.content().equals("prev")){
            MyGUI.npc_prev();
        }else if (m.content().equals("end")){
            MyGUI.npc_end();
            MyGUI.closeWindow("npcchat");
        }
        else{
            System.out.println(m.content());
        }
    }
}
