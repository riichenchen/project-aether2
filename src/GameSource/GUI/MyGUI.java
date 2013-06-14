package GameSource.GUI;

import GameSource.Script.NPCFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;

public class MyGUI extends AGUI{
    private static ANPCChat npcchat;
    private static AInventory invent;
    private static AHUD hud;
    private static ATextField chat;
    private static AEquip equip;
    private static AStats stats;
    private static ASkills skills;
    private static AShop shop;
    private static BufferedReader in;
    private static Image cursorimage;
    private static Image cursordown;
    private static Image cursorup;
    public static boolean passShift;


    public static void init(int wid, int hgt){
        AGUI.init(wid,hgt);
        passShift=false;
        cursorimage=cursorup;
        inputSets.put("normal",new NormalInputSet());
	inputSet=inputSets.get("normal");
		
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
        npcchat.setContent(f.next());
        openWindow("npcchat");
    }
    public static void showNPC(String text){
        npcchat.setContent(text);
        openWindow("npcchat");
    }
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
    public static void setShopBuyItem(String i){
        shop.setBuyItem(i);
    }
    public static void setShopSellItem(String i){
        shop.setSellItem(i);
    }
    public static void updateItems(){
        if (invent!=null){
            invent.loadButtons();
        }
    }
    public static void increaseSkill(int i){
        skills.increase(i);
    }
    public static void drawCursor(Graphics g){
        if (AMouseInput.clicked(AMouseInput.LEFT)||AMouseInput.held(AMouseInput.LEFT)){
            g.drawImage(cursordown,AMouseInput.mx,AMouseInput.my,null);
        }
        else{
            g.drawImage(cursorup,AMouseInput.mx,AMouseInput.my,null);
        }
    }
    public static void draw(Graphics g){
        
        
        AGUI.draw(g);
        hud.draw(g);
        chat.draw(g);
        g.drawImage(cursorimage,AMouseInput.mx, AMouseInput.my,null);
    }

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

    public static void updateStatWindow() {
        stats.updateButtons();
    }

    public static void shopBuyItem(String content) {
        shop.buy();
    }

    public static void shopSellItem(String content) {
        shop.sell();
    }
    
    public static void setShopData(String key){}
    
    public static void update(){
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