package GameSource.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TestGUI extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	private AGUI myGUI;
	private InputManager in;
	private AMouseInput mouse;
	
	public TestGUI(){
		super();
		in = new InputManager();
		mouse = new AMouseInput ();
		myGUI = new AGUI(in,mouse, 800,600);
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
        if (e.getKeyCode()!=KeyEvent.VK_ESCAPE)
        myGUI.showNPC("\"We faeries don't talk about them much, but \\nthe Underclouds are a place hidden deep \\nwithin the clouds you can normally see up \\nhere. Strange creatures and dark faeries live \\nthere, but they've always kept to themselves. \\n\n" +
"There's even a rumor that there's some kind of city down there, but nobody who's gone to look has ever returned. I think there might be a path through there that lets you get to Faerie City, but I don't know. But it's probably your only choice.\" ");
    }
    
    public void keyReleased(KeyEvent e) {
        in.keyUp(e.getKeyCode());
        
    }
    public void paintComponent (Graphics g){
    	myGUI.draw(g);
    	String k="("+AGUI.mx+","+AGUI.my+")";
    	g.drawString(k,700,10);
    }


    public void mouseDragged(MouseEvent e) {
    	mouse.update(e);
    	myGUI.update();
    }

    public void mouseMoved(MouseEvent e) {
        mouse.update(e);
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