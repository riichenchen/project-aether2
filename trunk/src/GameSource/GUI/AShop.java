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
    
    private AButton [] tabsL; private AButton [] tabsR;
    private String [] items; private String [] itemsR;
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
                CharacterHandler.init();
                CharacterHandler.addStat("maxhp",500);
                CharacterHandler.addStat("maxmp",10000);
                CharacterHandler.addStat("hp",352);
                CharacterHandler.addStat("mp",10000);
                EquipHandler.init();
		new TestShop();
	}
	
}