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
    public static final int EQUIP=0;
    public static final int USE=1;
    public static final int OTHER=2;
    public static final String [] nameDirect={"equip","use","other"};
    private String activeTab;
    private AButton [] tabs;
    private AButton [] tabsHover;
    private String [] items;
    private ArrayList<APoint> buttonLocs;
    private AContainer buttons;
    
    public AInventory(){
        super("invent");
        
        setImage(ImageFactory.getImage("invent"));
        setSize(172,393);
        buttons=new AContainer(0,0);
        buttonLocs=new ArrayList<>();
        loadButtonLocs();
        tabs=new AButton [3];
        tabsHover=new AButton[3];
        for (int i=0; i<3; i++){
   //         System.out.println(nameDirect[i]);
            tabs[i]=new AButton(nameDirect[i],AMessage.INVENTORY,nameDirect[i]);
            tabs[i].setSize(31,20);
            tabs[i].setImage(ImageFactory.getImage(tabs[i].getName()));
            tabs[i].setLocation(8+31*i,26);
 //           System.out.println(tabs[i]);
            tabsHover[i]=new AButton(nameDirect[i]+"_hover",AMessage.NULL,"");
            tabsHover[i].setSize(31,20);
            tabsHover[i].setImage(ImageFactory.getImage(tabsHover[i].getName()));
            tabsHover[i].setLocation(8+31*i,25);
 //           System.out.println(tabsHover[i]);
        }
        
        this.add(new AImage("invent_bg2",6,23));
        this.add(new AImage("invent_bg3",7,45));
        this.add(buttons);
        setVisible(true);
        setPane("equip");
        System.out.println("YOOOOOO "+buttons.size());
        System.out.println(buttons.content().get(0).getName());
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(background);
        g.fillRect(20,20,20,20);
        for (int i=0; i<3; i++){
            AButton tabButton=tabs[i];
            if (tabButton.getName().equals(activeTab) || tabButton.collidepoint(MyGUI.mx, MyGUI.my)){
                tabButton=tabsHover[i];
            }
            g.drawImage(tabButton.bg,x+tabButton.x, y+tabButton.y,null);
        }
        //drawButtons(g);
    }
    public void drawButtons(Graphics g){
       for (int i=0;i<Math.min(buttons.size(),24);i++){
            buttons.get(i).draw(g);
        }
    }
    
    public void loadButtonLocs(){
        for (int tx=10; tx<148;tx+=36){
            for (int ty=51; ty<150; ty+=36){
                buttonLocs.add(new APoint(tx,ty));
            }
        }
    }
    
    public void setPane(String paneName){
        items = InventoryHandler.getItemIds();
        System.out.println(items.length);
        activeTab=paneName;
        buttons.clear();
        int buttonNo=0;
        for (int i=0;i<Math.min(items.length,24);i++){
            System.out.println("item:"+i);
            String key=items[i];
            InventoryItem c=InventoryHandler.getItem(key);
            if ((c instanceof EquipItem && activeTab.equals("equip"))||
                    (c instanceof UseItem && activeTab.equals("use"))){
      //          System.out.println("hii");
                AButton b=new AButton (key,AMessage.INVENTORY_CLICK,key,33,33);
                b.setImage(c.getImage());
                b.setLocation(buttonLocs.get(buttonNo).x, buttonLocs.get(buttonNo).y);
                b.setVisible(true);
                b.setParent(this);
                buttons.add(b);
  //              add(b);
                buttonNo++;
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

class TestPanel extends JPanel{
	AInventory invent;
	public TestPanel(){
		super();
		invent=new AInventory();
	}
	public void paintComponent (Graphics g){
		invent.draw(g);
	}
}
class TestInvent extends JFrame{
	TestPanel test;
	public TestInvent(){
		super("AHHHH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800,600);
		test=new TestPanel();
		add(test);
		repaint();
		setVisible(true);
	}
	public static void main (String [] args){
            AssetManager.init();
                ImageFactory.init();
                InventoryHandler.addItem(ItemFactory.getItem("trollbaithelm"));
                System.out.println("item test");
                System.out.println(ItemFactory.getItem("trollbaithelm") instanceof EquipItem);
		new TestInvent();
	}
	
}