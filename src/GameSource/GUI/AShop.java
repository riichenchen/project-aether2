/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
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
public class AShop extends AWindow{
    public static final String [] nameDirect={"equip","use","other"};
    private String activeTabL;
    private AButton activeTabButtonL;
    private String activeTabR;
    private AButton activeTabButtonR;
    private AButton sellConfirm;
    private AButton buyConfirm;
    private ATextField sellNum;
    private ATextField buyNum;
    
    private AButton [] tabsL; private AButton [] tabsR;
    private String [] itemsL; private String [] itemsR;
    private ArrayList<APoint> buttonLocsL; private ArrayList<APoint> buttonLocsR;
    private AContainer buttonsL; private AContainer buttonsR;
    
    public AShop(){
        super("shop");
        setImage(AImageFactory.getImage("shop_main"));
        setSize(465,328);
        setMoveBar(0,0,465,20);
        buttonLocsL=new ArrayList<>();
        buttonLocsR=new ArrayList<>();
        loadButtonLocs();
        buttonsL=new AContainer(0,0,5, buttonLocsL);
        buttonsR=new AContainer(0,0,5, buttonLocsR);
        
        tabsR=new AButton [3];
        tabsL=new AButton [1];
        loadTabs();
        System.out.println("AShop/Constr.   tabsR[0]:"+tabsR[0]);
        this.add(buttonsR);
        this.add(buttonsL);
        
        activeTabButtonL=tabsL[0];
        activeTabButtonR=tabsR[0];
        System.out.println("AShop/Constr.   activeTabButton:"+activeTabButtonR);
        activeTabButtonR.setVisible(true);
        setRightPane("equip");
  
        buyNum=new ATextField(84,14,21,50);
        add(buyNum);
        sellNum=new ATextField(84,14,252,50);
        add(sellNum);
        buyConfirm=new AButton("shop_buy",AMessage.SHOP_BUY,"buying!");
        buyConfirm.setImage(AImageFactory.getImage("shop_buy"));
        buyConfirm.setFGImage(AImageFactory.getImage("shop_buy_fg"));
        buyConfirm.setLocation(140,50);
        add(buyConfirm);
        sellConfirm=new AButton("shop_sell",AMessage.SHOP_SELL,"selling!");
        sellConfirm.setImage(AImageFactory.getImage("shop_sell"));
        sellConfirm.setFGImage(AImageFactory.getImage("shop_sell_fg"));
        sellConfirm.setLocation(140+231,50);
        add(sellConfirm);
        
        
        setVisible(true);
    }
    public void loadTabs(){
        for (int i=0; i<3; i++){
            tabsR[i]=new AButton(nameDirect[i],AMessage.SHOP,nameDirect[i]);
            tabsR[i].setSize(31,20);
            tabsR[i].setImage(AImageFactory.getImage(tabsR[i].getName()));
            tabsR[i].setFGImage(AImageFactory.getImage(nameDirect[i]+"_hover"));
            tabsR[i].setVisible(true);
            tabsR[i].setLocation(238+31*i,89);
            add(tabsR[i]);
        }
        tabsL[0]=new AButton("shop_all",AMessage.SHOP,"shop_all");
        tabsL[0].setSize(31,20);
        tabsL[0].setImage(AImageFactory.getImage("shop_all"));
        tabsL[0].setFGImage(AImageFactory.getImage("shop_all_fg"));
        tabsL[0].setVisible(true);
        tabsL[0].setLocation(7,90);
        add(tabsL[0]);
    }
    public void loadButtonLocs(){
        for (int i=0; i<5; i++){
            buttonLocsL.add(new APoint(10,114+43*i));
            buttonLocsR.add(new APoint(241,114+43*i));
        }
    }
    public void setRightPane(String paneName){
        if (paneName.equals(activeTabR)==false){
            activeTabR=paneName;
            for (int i=0; i<3; i++){
                if (nameDirect[i].equals(activeTabR)){
                    System.out.println("AShop/setRightPane    activeTabButtonR:"+activeTabButtonR);
                    activeTabButtonR.displayBG();
                    activeTabButtonR=tabsR[i];
                    activeTabButtonR.displayFG();
                }
            }
            loadRightButtons();
        }
    }
    
    public void loadRightButtons(){
        itemsR = InventoryHandler.getItemIds();
        buttonsR.clear();
        for (int i=0;i<itemsR.length;i++){
            String key=itemsR[i];
            InventoryItem c=InventoryHandler.getItem(key);
            if ((c instanceof EquipItem && activeTabR.equals("equip"))||
                (c instanceof UseItem && activeTabR.equals("use"))){
                AButton b=new AButton (key,AMessage.SHOP_SELL,key,202,35);
                b.setImage(TextImageFactory.createShopLabel(c));
                b.setFGImage(AImageFactory.getImage("shop_item_fg"));
                b.setHover(TextImageFactory.createDes(c));
                b.setLabel(String.format("%d",InventoryHandler.getItemQuantity(key)));
                b.setParent(this);
                buttonsR.add(b);
            }
        }
        buttonsR.updateActiveContent();
    } 
    
     public void loadLeftButtons(){
        itemsL = InventoryHandler.getItemIds();
        buttonsL.clear();
        for (int i=0;i<itemsL.length;i++){
            String key=itemsL[i];
            InventoryItem c=InventoryHandler.getItem(key);
            if ((c instanceof EquipItem && activeTabL.equals("equip"))||
                (c instanceof UseItem && activeTabL.equals("use"))){
                AButton b=new AButton (key,AMessage.SHOP_SELL,key,202,35);
                b.setImage(TextImageFactory.createShopLabel(c));
                b.setFGImage(AImageFactory.getImage("shop_item_fg"));
                b.setHover(TextImageFactory.createDes(c));
                b.setLabel(String.format("%d",InventoryHandler.getItemQuantity(key)));
                b.setParent(this);
                buttonsL.add(b);
            }
        }
        buttonsL.updateActiveContent();
    } 
    

}





class TestSPanel extends JPanel{
	AShop equip;
	public TestSPanel(){
		super();
		equip=new AShop();
                equip.setVisible(true);
	}
	public void paintComponent (Graphics g){
		equip.draw(g);
	}
}
class TestShop extends JFrame{
	TestSPanel test;
	public TestShop(){
		super("AHHHH3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800,600);
		test=new TestSPanel();
		add(test);
		repaint();
		setVisible(true);
	}
	public static void main (String [] args){
            AssetManager.init();
                AImageFactory.init();
            InventoryHandler.addItem(ItemFactory.getItem("trollbaithelm"));
            InventoryHandler.addItem(ItemFactory.getItem("redpot"));
            InventoryHandler.addItem(ItemFactory.getItem("redpot"));
            InventoryHandler.addItem(ItemFactory.getItem("redpot"));
                CharacterHandler.init();
                CharacterHandler.addStat("maxhp",500);
                CharacterHandler.addStat("maxmp",10000);
                CharacterHandler.addStat("hp",352);
                CharacterHandler.addStat("mp",10000);
                EquipHandler.init();
		new TestShop();
	}
	
}