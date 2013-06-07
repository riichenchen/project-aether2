package GameSource.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TestGUI extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	private MyGUI myGUI;
	private InputManager in;
	private AMouseInput mouse;
	
	public TestGUI(){
		super();
		in = new InputManager();
		mouse = new AMouseInput ();
		myGUI = new MyGUI(in,mouse, 800,600);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
    	in.keyDown(e.getKeyCode());
    	myGUI.update();
    }
    
    public void keyReleased(KeyEvent e) {
        in.keyUp(e.getKeyCode());
        
    }
    public void paintComponent (Graphics g){
    	myGUI.draw(g);
    	String k="("+MyGUI.mx+","+MyGUI.my+")";
    	g.drawString(k,700,10);
    }


    public void mouseDragged(MouseEvent e) {
    	mouse.update(e);
    	myGUI.update();
    }

    public void mouseMoved(MouseEvent e) {
        mouse.update(e);
        myGUI.update();
    }
    
    public static void main (String [] args){
    	TestGUI frame = new TestGUI();
    }
    public void update(){
    	myGUI.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouse.press(e);
        myGUI.update();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse.release(e);
        myGUI.update();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}