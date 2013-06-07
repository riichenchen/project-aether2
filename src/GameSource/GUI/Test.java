package GameSource.GUI;
import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;
import GameSource.User.ItemFactory;
import javax.swing.*;
import java.awt.event.*;

public class Test extends JFrame implements ActionListener{
	private TestGUI testing;
	private Timer myTimer;
	public Test(){
		super("Test A GUI");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer=new Timer(10,this);
		testing=new TestGUI();
		add(testing);
		setVisible(true);
		myTimer.start();
                ImageFactory.init();
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
                testing.tick();
	}
	
	public static void main (String [] args){
            AssetManager.init();
            ImageFactory.init();
            InventoryHandler.addItem(ItemFactory.getItem("trollbaithelm"));
            InventoryHandler.addItem(ItemFactory.getItem("redpot"));
            CharacterHandler.init();
                CharacterHandler.addStat("maxhp",500);
                CharacterHandler.addStat("maxmp",10000);
                CharacterHandler.addStat("hp",352);
                CharacterHandler.addStat("mp",10000);
            new Test();
	}
}