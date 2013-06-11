package GameSource.GUI;
import GameSource.Assets.AssetManager;
import GameSource.Script.NPCFrame;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.User.Inventory.EquipItem;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import javax.swing.*;
import java.awt.event.*;

public class Test extends JFrame implements ActionListener{
	private TestGUI testing;
	private Timer myTimer;
        public static NPCFrame john;
	public Test(){
		super("Test A GUI");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer=new Timer(10,this);
		testing=new TestGUI();
		add(testing);
		setVisible(true);
		myTimer.start();
                AImageFactory.init();
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
                testing.update();
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
                CharacterHandler.addStat("exp",5);
                CharacterHandler.addStat("statPoints",10);
                CharacterHandler.addStat("money",1000000);
            EquipHandler.init();
            EquipHandler.equip((EquipItem)(ItemFactory.getItem("trollbaithelm")));
            EquipHandler.equip((EquipItem)(ItemFactory.getItem("trollbaitarmor")));
            new Test();
	}
}