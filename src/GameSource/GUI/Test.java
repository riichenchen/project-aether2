package GameSource.GUI;
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
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public static void main (String [] args){
		new Test();
	}
}