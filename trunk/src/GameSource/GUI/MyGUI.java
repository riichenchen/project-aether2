/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Script.NPCFrame;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Joy
 */
public class MyGUI extends AGUI{
 //   private LinkedList<String> windowNames;
    private static ANPCChat npcchat;
    private static AInventory invent;
    private static AHUD hud;
    private static ATextField chat;
    private static AEquip equip;
    private static AStats stats;
    private static ASkills skills;
    private static AShop shop;
    private static BufferedReader in;


    public static void init(int wid, int hgt){
        AGUI.init(wid,hgt);
//        inputSets.put("battle",new BattleInputSet());
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
    
    public static void draw(Graphics g){
        AGUI.draw(g);
        hud.draw(g);
        chat.draw(g);
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
        AGUI.update();
        if (equip.visible()){
            equip.update();
        }
    }
}


/*
 *     public static AButton createAButton(String name, int x,int y,int wid,int hgt,int fr,int fg,int fb,int br,int bg,int bb,int type,String message){
        AButton out=new AButton(name,type,message);
        out.setSize(wid,hgt);
        out.setLocation(x,y);
        out.setFG(fr,fg,fb);
        out.setBG(br,bg,bb);
        return out;
    }
    public static AWindow createAWindow(String name, int x,int y,int wid,int hgt,int fr,int fg,int fb,int br,int bg,int bb){
        AWindow out=new AWindow(name);
        out.setSize(wid,hgt);
        out.setLocation(x,y);
        out.setFG(fr,fg,fb);
        out.setBG(br,bg,bb);
        out.setVisible(false);
        return out;
    }
    public static ATextArea createATextArea(String name, int x,int y,int wid,int hgt,int fr,int fg,int fb,int br,int bg,int bb){
        ATextArea  out=new ATextArea (name);
        out.setSize(wid,hgt);
        out.setLocation(x,y);
        out.setFG(fr,fg,fb);
        out.setBG(br,bg,bb);
        out.setVisible(false);
        return out;
    }
    public static AComponent createComponent(String line) throws IOException{
        System.out.println(line);
        int x,y,wid,hgt,fr,fg,fb,br,bg,bb;
        int type;
        String message;
        String name=in.readLine();
        String [] size=in.readLine().split(" ");
        wid=Integer.parseInt(size[0]); hgt=Integer.parseInt(size[1]);
        
        String [] bgCol=in.readLine().split(" ");
        br=Integer.parseInt(bgCol[0]); bg=Integer.parseInt(bgCol[1]);bb=Integer.parseInt(bgCol[2]);
        String [] fgCol=in.readLine().split(" ");
        fr=Integer.parseInt(fgCol[0]); fg=Integer.parseInt(fgCol[1]);fb=Integer.parseInt(fgCol[2]);
        String [] pos=in.readLine().split(" ");
        x=Integer.parseInt(pos[0]); y=Integer.parseInt(pos[1]);
        if (line.equals("<AButton")){
            type=Integer.parseInt(in.readLine());
            message=in.readLine();
            System.out.println ("yoooo"+in.readLine());
            return createAButton(name,x,y,wid,hgt,fr,fg,fb,br,bg,bb,type,message);
        }
        if (line.equals("<ATextArea")){
            in.readLine();
            return createATextArea(name,x,y,wid,hgt,fr,fg,fb,br,bg,bb);
        }
        AWindow out=createAWindow(name,x,y,wid,hgt,fr,fg,fb,br,bg,bb);
        String nextLine=in.readLine();
        System.out.println("hiii!" + nextLine);
        while (nextLine.equals("/>")==false){
            AComponent c=createComponent(nextLine);
            out.add(c);
            nextLine=in.readLine();
        }
        System.out.println(name+" done!");
        return out;
    }
 */

        
        /*
        try{
            in=new BufferedReader(new FileReader("src/GameSource/Assets/GUI/TestLayout.txt"));
            String line=in.readLine();
            while (line.equals("")==false){
                AComponent c= createComponent(line);
                System.out.println(c.getName());
                addNewWindow(c);
                line=in.readLine();
            }
        }
        catch (IOException e){System.out.println("File not found");}
        System.out.println(windowNames.size());
        for (String s: windowNames){
            System.out.println(s);
        }
        */