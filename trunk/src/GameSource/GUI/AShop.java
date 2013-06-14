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
import Sound.SoundManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*AShop.java            @Chen~
 * Specialty window that displays a shop inventory, as well as a player inventory
 * and allows for sale of items between the two.
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
    private String buyItem="";              //The items that will be sold or bought
    private String sellItem="";             //upon clicking the buttons
    private String buyWarning="";                   //Error messages to be shown
    private String sellWarning="";                  //when attempting to buy
    
    private String shopDataKey="trollbaitshop";     
    
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
        
        //Containers come last so that they don't take all of the mousecalls.
        this.add(buttonsR);
        this.add(buttonsL);
        
        activeTabButtonL=tabsL[0];      //Set the active tbas
        activeTabButtonR=tabsR[0];
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
    public void setShopId(String s){
        shopDataKey=s;
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
    public void loadTabs(){         //Load the four tabs
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
        //Sets the right active tab to the parameter taken in and reloads the 
        //buttons as needed
        if (paneName.equals(activeTabR)==false){
            activeTabR=paneName;
            for (int i=0; i<3; i++){
                if (nameDirect[i].equals(activeTabR)){
                    activeTabButtonR.displayBG();
                    activeTabButtonR=tabsR[i];
                    activeTabButtonR.displayFG();
                }
            }
            loadRightButtons();
            try{
                SoundManager.getChannel("UI").stopAll();
                SoundManager.getChannel("UI").addTrack("tabswap");
            }catch(NullPointerException e){}
        }
    }
    public void loadRightButtons(){
        //Gets an Array of inventory items and adds them to the AContainer
        itemsR = InventoryHandler.getItemIds();
        buttonsR.clear();
        for (int i=0;i<itemsR.length;i++){
            String key=itemsR[i];
            InventoryItem c=InventoryHandler.getItem(key);
            if ((c instanceof EquipItem && activeTabR.equals("equip"))||
                (c instanceof UseItem && activeTabR.equals("use"))){
                AButton b=new AButton (key,AMessage.SHOP_SELL,key,202,35);
                b.setImage(TextImageFactory.createShopLabel(c,1));      //1: sell
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
        //Gets a LinkedList of items available in the shop based on the shopData
        //key, loads the buttons for the items, and adds them to the AContainer
        LinkedList<ShopItemData> nitemsL=AssetManager.getShopData(shopDataKey).getShopItems();
        buttonsL.clear();
        for (int i=0;i<nitemsL.size();i++){
            InventoryItem c=nitemsL.get(i).item;
            String key=nitemsL.get(i).item.getKey();
            AButton b=new AButton (key,AMessage.SHOP_BUY,key,202,35);
            b.setImage(TextImageFactory.createShopLabel(c,2));              //buy=2;
            b.setFGImage(AImageFactory.getImage("shop_item_fg"));
            b.setHover(TextImageFactory.createDes(c));
            b.setParent(this);
            buttonsL.add(b);
        }
        buttonsL.updateActiveContent();
    } 
    @Override
    public void update(){
        super.update();
        if (buyNum.focus())         //Call the textbox's update functions if
            buyNum.update();        //they are in focus (have been clicked)
        if (sellNum.focus())
            sellNum.update();
        loadLeftButtons();
        loadRightButtons();
    }
    @Override
    public void call(){
        buyNum.setFocused(false);
        sellNum.setFocused(false);
        for (AComponent c: subComponents){
            if (c.callable()&& (c instanceof AContainer||c.collidepoint(AMouseInput.mx,AMouseInput.my))){
                c.call();
                break;
            }
       }
       buttonsL.call();         //The above loop will definitely call buttonsR, so we force a call of buttonsL
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Arial",Font.BOLD,11));
        
        g.drawString (""+CharacterHandler.getStat("money"),x+372,y+103);    //Draw the money text
        
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
        //Check if a string can be parsed into an integer by looking for non-digit
        //characters.
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i)<48 || s.charAt(i)>57)
                return false;
        }
        return s.length()>0;
    }
    public void buy() {
        //This method is invoked when the buyConfirm button is clicked by the
        //AProcessor. It parses the text in the quantity textfield, gives feedback
        //if the input is invalid, and also processes  the sale if the number is valid.
        if (buyItem.equals(""))
            return;                     //Do nothing if no item is selected
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
        //This method is invoked when the sellConfirm button is clicked by the
        //AProcessor. It parses the text in the quantity textfield, gives feedback
        //if the input is invalid, and also processes  the sale if the number is valid.
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