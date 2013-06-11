/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.Assets.Shop.ShopItemData;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.Inventory.UseItem;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    private String buyItem="";
    private String sellItem="";
    private String buyWarning="";
    private String sellWarning="";
    
    private String shopDataKey="trollbaitshop";     //changeable
    
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
        loadHeader();
//        System.out.println("AShop/Constr.   tabsR[0]:"+tabsR[0]);
        
        //Containers come last so that they don't take all of the mousecalls.
        //Check to make sure that having two will work out. You may need to 
        //overwrite call.
        this.add(buttonsR);
        this.add(buttonsL);
        
        activeTabButtonL=tabsL[0];
        activeTabButtonR=tabsR[0];
//        System.out.println("AShop/Constr.   activeTabButton:"+activeTabButtonR);
        activeTabButtonR.setVisible(true);
        setRightPane("equip");
        loadLeftButtons();
        
        
        
        setVisible(true);
    }
    public void setBuyItem(String s){
        buyItem=s;
    }
    public void setSellItem(String s){
        sellItem=s;
    }
    
    public void loadHeader(){
        buyNum=new ATextField(84,14,21,50);
        buyNum.setCallable(true);
        add(buyNum);
        sellNum=new ATextField(84,14,252,50);
        sellNum.setCallable(true);
        add(sellNum);
        buyConfirm=new AButton("shop_buy",AMessage.SHOP_BUY_CONFIRM,"buying!");
        buyConfirm.setSize(64,16);
        buyConfirm.setImage(AImageFactory.getImage("shop_buy"));
        buyConfirm.setFGImage(AImageFactory.getImage("shop_buy_fg"));
        buyConfirm.setLocation(140,50);
        buyConfirm.setVisible(true);
        add(buyConfirm);
        sellConfirm=new AButton("shop_sell",AMessage.SHOP_SELL_CONFIRM,"selling!");
        sellConfirm.setSize(64,16);
        sellConfirm.setImage(AImageFactory.getImage("shop_sell"));
        sellConfirm.setFGImage(AImageFactory.getImage("shop_sell_fg"));
        sellConfirm.setLocation(140+231,50);
        sellConfirm.setVisible(true);
        add(sellConfirm);
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
        tabsL[0]=new AButton("shop_all",AMessage.NULL,"shop_all");
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
//                    System.out.println("AShop/setRightPane    activeTabButtonR:"+activeTabButtonR);
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
        LinkedList<ShopItemData> nitemsL=AssetManager.getShopData(shopDataKey).getShopItems();
        buttonsL.clear();
        for (int i=0;i<nitemsL.size();i++){
            InventoryItem c=nitemsL.get(i).item;
            String key=nitemsL.get(i).item.getKey();
            AButton b=new AButton (key,AMessage.SHOP_BUY,key,202,35);
            b.setImage(TextImageFactory.createShopLabel(c));
            b.setFGImage(AImageFactory.getImage("shop_item_fg"));
            b.setHover(TextImageFactory.createDes(c));
            b.setParent(this);
                buttonsL.add(b);
        }
        buttonsL.updateActiveContent();
    } 
    
    public void update(){
        super.update();
        if (buyNum.focus())
            buyNum.update();
        if (sellNum.focus())
            sellNum.update();
        loadLeftButtons();
        loadRightButtons();
    }
    public void call(){
        buyNum.setFocused(false);
        sellNum.setFocused(false);
        for (AComponent c: subComponents){
                if (c.callable()&& (c instanceof AContainer||c.collidepoint(AMouseInput.mx,AMouseInput.my))){
                    c.call();
                    break;
                }
       }
       buttonsL.call();
    }
     
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Arial",Font.BOLD,11));
        
        g.drawString (""+CharacterHandler.getStat("money"),x+372,y+103);
        
        String buy="";
        String sell="";
        if (buyItem!=""){
            buy=ItemFactory.getItem(buyItem).getItemId();
        }
        if (sellItem!=""){
            sell=ItemFactory.getItem(sellItem).getItemId();
        }
        g.drawString("Buying: "+buy,x+20,y+27);
        g.drawString("Selling: "+sell,x+20+231,y+27);
        
        g.setColor(new Color(255,0,0));
        g.setFont(new Font ("Arial",Font.PLAIN,9));
        g.drawString(buyWarning, x+120, y+40);
        g.drawString(sellWarning, x+120+231, y+40);
    }
    public boolean isNum(String s){
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i)<48 || s.charAt(i)>57)
                return false;
        }
        return s.length()>0;
    }
    public void buy() {
        if (buyItem.equals(""))
            return;
        String qnt=buyNum.getText();
        if (isNum(qnt)){
            int q=Integer.parseInt(qnt);
            int price=2*AssetManager.getItemData(buyItem).sellPrice;
            if (price*q>CharacterHandler.getStat("money")){
                buyWarning="Too poor!";
                buyNum.setText(""+CharacterHandler.getStat("money")/price);
            }
            else{
                CharacterHandler.addStat("money",-1*price*q);
                for (int a=0; a<q; a++){
                    InventoryHandler.addItem(ItemFactory.getItem(buyItem));
                }
                buyWarning="Thank you!";
                buyItem="";
                buyNum.setText("");
            }
        }
        else{
            buyWarning="Invalid number";
            buyNum.setText("");
        }
    }

    public void sell() {
        if (sellItem.equals(""))
            return;
        String qnt=sellNum.getText();
        if (isNum(qnt)){
            int q=Integer.parseInt(qnt);
            InventoryItem c=ItemFactory.getItem(sellItem);
            int price=AssetManager.getItemData(sellItem).sellPrice;
            if (q>InventoryHandler.getItemQuantity(sellItem)){
                sellWarning="Not enough!";
                sellNum.setText(""+InventoryHandler.getItemQuantity(sellItem));
            }
            else{
                CharacterHandler.addStat("money", price*q);
                for (int a=0; a<q; a++)
                    InventoryHandler.removeItem(ItemFactory.getItem(sellItem));
                sellWarning="Thank you!";
                sellItem="";
                sellNum.setText("");
            }
        }
        else{
            sellWarning="Invalid number";
            sellNum.setText("");
        }
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