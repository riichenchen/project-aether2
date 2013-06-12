/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

/**
 *
 * @author Joy
 */
public class AMessage {
    public static final int QUIT=0;
    public static final int OPEN_WINDOW=1;
    public static final int CLOSE_WINDOW=2;
    public static final int EQUIP = 3;
    public static final int UNEQUIP = 4;
    public static final int USE_ITEM=5;
    public static final int NPC_CHAT=6;
    public static final int CHANGE_PANE=7;
    public static final int INVENTORY=8;
    public static final int NULL=9;
    public static final int INVENTORY_CLICK=10;
    public static final int SCROLL=11;
    public static final int STAT_WINDOW=12;
    public static final int SKILLS=13;
    public static final int SKILL_UP=20;
    public static final int SEND_MESSAGE=100;
    public static final int SHOP=14;
    public static final int SHOP_SELL=15;
    public static final int SHOP_BUY=16;
    public static final int SHOP_CLICK=17;
    public static final int SHOP_BUY_CONFIRM=18;
    public static final int SHOP_SELL_CONFIRM=19;
    private int type;
    private String content;
    
    public AMessage (String type, String content){
        this.content=content;
        this.type=SEND_MESSAGE;
        if (type.equals("open")){
            this.type=OPEN_WINDOW;
        }
        else if (type.equals("close")){
            this.type=CLOSE_WINDOW;
        }
    }
    public AMessage (int type, String content){
        this.content=content;
        this.type=type;
    }
    
    public int type(){
        return type;
    }
    public String content(){
        return content;
    }
    
}
