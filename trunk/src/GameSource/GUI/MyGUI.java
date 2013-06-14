package GameSource.GUI;

import GameSource.Script.NPCFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;

/* MyGUI.java           @Chen~
 * This class extends MyGUI and contains all the specific function needed for the
 * Aether GUI. It loads all the windows and contains all the methods to call various
 * methods within the windows themselves.
 */

public class MyGUI extends AGUI{
    private static ANPCChat npcchat;        //
    private static AInventory invent;       //
    private static AHUD hud;                //
    private static ATextField chat;         ///..  Windows
    private static AEquip equip;            //
    private static AStats stats;            //
    private static ASkills skills;          //
    private static AShop shop;              //

    private static Image cursorimage;       //cursor images
    private static Image cursordown;
    private static Image cursorup;
    public static boolean passShift;        //Whether or not to call Game's shift button


    public static void init(int wid, int hgt){
        AGUI.init(wid,hgt);
        passShift=false;
        cursorimage=cursorup;
        inputSets.put("normal",new NormalInputSet());
        inputSet=inputSets.get("normal");
		
        //Loading all the windows
        npcchat=new ANPCChat();
        npcchat.setVisible(false);
        windows.put(npcchat.getName(),npcchat);
        windowNames.add(npcchat.getName());
		
        hud=new AHUD();
        windows.put(hud.getName(),hud);
        windowNames.add(hud.getName());
		
        invent=new AInventory();
        invent.setVisible(false);
        windows.put(invent.getName(),invent);
        windowNames.add(invent.getName());
		
        equip=new AEquip();
        equip.setVisible(false);
        windows.put(equip.getName(),equip);
        windowNames.add(equip.getName());
		
        stats=new AStats();
        stats.setVisible(false);
        windows.put(stats.getName(),stats);
        windowNames.add(stats.getName());
		
        skills=new ASkills();
        skills.setVisible(false);
        windows.put(skills.getName(),skills);
        windowNames.add(skills.getName());
		
        shop=new AShop();
        shop.setVisible(false);
        windows.put(shop.getName(),shop);
        windowNames.add(shop.getName());
        
        chat=new ATextField(440,13,65,30);
        chat.setName("chat");
        chat.setMoveable(false);
        chat.setParent(hud);
        windows.put(chat.getName(),chat);
        windowNames.add(chat.getName());
        
        cursordown=AImageFactory.getImage("cursor_down");
        cursorup=AImageFactory.getImage("cursor_up");
    }

    public static void showNPC(NPCFrame f,String n){
        npcchat.setName(n);
        npcchat.setFrame(f);
        String s=f.next();
        if (s!=null){
            npcchat.setContent(s);
            openWindow("npcchat");
        }
    }

    //The next three methods change varies panes in inventory, skills, and shop
    public static void changeInventPane(String newPane){
        if (invent!=null){
            invent.setPane(newPane);
        }
    }
    public static void changeShopInventPane(String newPane){
        if (shop!=null){
            shop.setRightPane(newPane);
        }
    }
    public static void changeSkillsPane(String newPane){
        if (skills!=null){
            skills.setPane(newPane);
        }
    }
    
    //These two methods set the active sell or buy items as instructed by AProcessor,
    //or buy or sell the active Items as instructed by AProcessor.
    public static void setShopBuyItem(String i){
        shop.setBuyItem(i);
    }
    public static void setShopSellItem(String i){
        shop.setSellItem(i);
    }
    public static void shopBuyItem(String content) {
        shop.buy();
    }
    public static void shopSellItem(String content) {
        shop.sell();
    }
    public static void setShopData(String key){
        shop.setShopId(key);
    }
    
  

    public static void draw(Graphics g){
        AGUI.draw(g);
        hud.draw(g);
        chat.draw(g);
        g.drawImage(cursorimage,AMouseInput.mx, AMouseInput.my,null);
    }

    //These three methods are invoked to trigger the NPC chat box to change frames
    public static void npc_next() {
        npcchat.next();
    }
    public static void npc_prev() {
        npcchat.prev();
    }
    public static void npc_end() {
        npcchat.end();
        closeWindow("npcchat");
    }

    //These methods keep the GUI windows up-to-date with other changes made in the
    //program buy recalculating the dependent components.
    public static void updateItems(){
        if (invent!=null){
            invent.loadButtons();
        }
    }
    public static void updateStatWindow() {
        stats.updateButtons();
    }
    public static void increaseSkill(int i){
        skills.increase(i);
    }
    public static void update(){
        //This update loops runs the AGUI main update loop, blits the cursor, and
        //checks for changews that can be made. It also checks whether or not a
        //Shift key press is to be pressed at i=a
        passShift=InputManager.down(KeyEvent.VK_SHIFT);
        if (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT)){
            cursorimage=cursordown;
        }
        if (AMouseInput.released(AMouseInput.LEFT)){
            cursorimage=cursorup;
        }
        AGUI.update();
        if (equip.visible()){
            equip.update();
        }
        if (InputManager.down(KeyEvent.VK_SHIFT)&&passShift){
            Input.InputManager.keyDown(KeyEvent.VK_SHIFT);
        }
    }
}