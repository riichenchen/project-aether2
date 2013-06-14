/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.User.Inventory.EquipItem;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Joy
 */
public class AEquip extends AWindow{
    public AEquip(){
        super();
        setName("equipment");
        setImage(AImageFactory.getImage("equipment"));
        setSize(233,214);
        setMoveBar(0,0,233,20);
        loadImages();
    }
    public void loadImages(){
        subComponents.clear();
        String [] parts={"head","body","feet","weapon"};
        for (int i=3;i>=0;i--){
            EquipItem item= (EquipItem)EquipHandler.getItem(parts[i]);
            if (item!=null){
                Image back=TextImageFactory.createEquipLabel(item,parts[i]);
                String des= AssetManager.getItemData(item.getKey()).itemDescription;
                Image front=TextImageFactory.createDes(item.getItemId(),des);
                add(new AImage(parts[i],16,34+42*i,back,front));
            }
        }
    }
    public void update(){
        super.update();
        loadImages();
    }
    
//    public void draw(Graphics g){
//        
//        Image back=TextImageFactory.createEquipLabel(ItemFactory.getItem("trollbaithelm"),"TROLL");
//        Image front=TextImageFactory.createDes("Trollbaithelm","filler description");
//        add(new AImage("troll",16,34,back,front));
//        super.draw(g);
//        g.drawImage(TextImageFactory.createEquipLabel(ItemFactory.getItem("trollbaithelm"),"TROLL"),400,400,null);
//        
//    }
}
class TestEPanel extends JPanel{
	AEquip equip;
	public TestEPanel(){
		super();
		equip=new AEquip();
                equip.setVisible(true);
	}
	public void paintComponent (Graphics g){
		equip.draw(g);
                g.drawImage(TextImageFactory.createDes("YOLO","420-baylife-swag"),300,100,null);
	}
}
class TestEquip extends JFrame{
	TestEPanel test;
	public TestEquip(){
		super("AHHHH3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800,600);
		test=new TestEPanel();
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
		new TestEquip();
	}
	
}