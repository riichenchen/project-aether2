/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

/**
 *
 * @author Joy
 */
public class AProcessor {
    private static MyGUI myGUI;
    
    public static void bindTo(MyGUI g){
        myGUI=g;
    }
    
    public static void process(AMessage m){
        switch (m.type()){
            case AMessage.QUIT: System.out.println("QUIT GAME");
                break;
            case AMessage.OPEN_WINDOW: myGUI.openWindow(m.content());
                break;
            case AMessage.CLOSE_WINDOW: myGUI.closeWindow(m.content());
                break;
            case AMessage.EQUIP: System.out.println("Equip: "+m.content());
                break;
            case AMessage.UNEQUIP: System.out.println("Unequip: "+m.content());
                break;
            case AMessage.USE_ITEM: System.out.println("Use "+m.content());
                break;
            case AMessage.NPC_CHAT: System.out.println(m.content());
                break;
            case AMessage.SEND_MESSAGE: System.out.println(m.content());
                break;
            case AMessage.INVENTORY: myGUI.changeInventPane(m.content());
                break;
            case AMessage.NULL: break;
            case AMessage.INVENTORY_CLICK: System.out.println(m.content()+" click in inventory");
                break;
            default: System.out.println("Invalid Message");
        }    
    }
}
