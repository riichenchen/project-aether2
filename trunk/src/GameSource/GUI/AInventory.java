/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.Inventory.UseItem;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Joy
 */
public class AInventory extends AWindow{
    public static final String [] nameDirect={"equip","use","other"};
    private String activeTab;
    private AButton activeTabButton;
    
    private AButton [] tabs;
    private String [] items;
    private ArrayList<APoint> buttonLocs;
    private AContainer buttons;
    
    
    public AInventory(){
        super("invent");
        setImage(ImageFactory.getImage("invent"));
        setSize(172,393);
        setMoveBar(0,0,172,22);
        buttons=new AContainer(0,0);
        buttonLocs=new ArrayList<>();
        loadButtonLocs();
        tabs=new AButton [3];
        this.add(new AImage("invent_bg2",6,23));
        this.add(new AImage("invent_bg3",7,45));
        for (int i=0; i<3; i++){
            tabs[i]=new AButton(nameDirect[i],AMessage.INVENTORY,nameDirect[i]);
            tabs[i].setSize(31,20);
            tabs[i].setImage(ImageFactory.getImage(tabs[i].getName()));
            tabs[i].setFGImage(ImageFactory.getImage(nameDirect[i]+"_hover"));
            tabs[i].setVisible(true);
            tabs[i].setLocation(8+31*i,27);
            this.add(tabs[i]);
        }
        
        this.add(buttons);
        setVisible(true);
        activeTabButton=tabs[0];
        setPane("equip");
        
    }

    
    public void loadButtonLocs(){
        for (int tx=10; tx<148;tx+=36){
            for (int ty=51; ty<150; ty+=36){
                buttonLocs.add(new APoint(tx,ty));
            }
        }
    }
    
    public void setPane(String paneName){
        if (paneName.equals(activeTab)==false){
            items = InventoryHandler.getItemIds();
 //           System.out.println("Pane Switch to "+paneName+"Items: "+items.length);
            activeTab=paneName;
            for (int i=0; i<3; i++){
                if (nameDirect[i].equals(activeTab)){
                    activeTabButton.displayBG();
                    activeTabButton=tabs[i];
                    activeTabButton.displayFG();
                }
            }
            buttons.clear();
            int buttonNo=0;
            for (int i=0;i<Math.min(items.length,24);i++){
                String key=items[i];
                InventoryItem c=InventoryHandler.getItem(key);
                if ((c instanceof EquipItem && activeTab.equals("equip"))||
                    (c instanceof UseItem && activeTab.equals("use"))){
                    AButton b=new AButton (key,AMessage.INVENTORY_CLICK,key,33,33);
                    b.setImage(c.getImage());
                    b.setLabel(String.format("%d",InventoryHandler.getItemQuantity(key)));
                    System.out.println(String.format(key+": %d items",InventoryHandler.getItemQuantity(key)));
                    b.setLocation(buttonLocs.get(buttonNo).x, buttonLocs.get(buttonNo).y);
                    b.setVisible(true);
                    b.setParent(this);
                    buttons.add(b);
                    buttonNo++;
                }
            }
        }
    }
    
    /*
    @Override
    public void call(){
        super.call(); 
        int rx = MyGUI.mx-x;
        int ry = MyGUI.my-y;
        for (AComponent c: buttons.content()){
                if (c.collidepoint(rx,ry)){
                    c.call();
                    break;
                }
            }
    }
    * */
}

