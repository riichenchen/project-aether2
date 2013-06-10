package GameSource.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TestGUI extends JPanel implements MouseListener, MouseMotionListener, KeyListener{

//	private InputManager in;
//	private AMouseInput mouse;
	
	public TestGUI(){
		super();
//		in = new InputManager();
//		mouse = new AMouseInput ();
                MyGUI.init(800,600);
//		myGUI = new MyGUI(in,mouse, 800,600);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
    
    public void keyTyped(KeyEvent e) {
//        InputManager.keyTyped(e.getKeyCode());
//        System.out.println("typed " + e.getKeyCode()+":"+(char)(e.getKeyCode()));
    }

    public void keyPressed(KeyEvent e) {
    	InputManager.keyDown(e.getKeyCode());
//        System.out.println("pressed "+ e.getKeyCode()+":"+(char)(e.getKeyCode()));
//    	MyGUI.update();
    }
    
    public void keyReleased(KeyEvent e) {
        InputManager.keyUp(e.getKeyCode());
//        System.out.println("released");

        
    }
    public void paintComponent (Graphics g){
        g.drawImage(AImageFactory.getImage("bg"),0,0,null);
    	MyGUI.draw(g);
    	String k="("+AMouseInput.mx+","+AMouseInput.my+")";
    	g.drawString(k,700,10);
    }
    public void tick(){
        MyGUI.tick();
    }

    public void mouseDragged(MouseEvent e) {
    	AMouseInput.update(e);
      //  AMouseInput.press(e);
    }

    public void mouseMoved(MouseEvent e) {
        AMouseInput.update(e);
//        MyGUI.update();
    }
    
    public void update(){
    	MyGUI.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        AMouseInput.click(e);
//        System.out.println("click!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AMouseInput.press(e);
//        System.out.println("press!");
//        MyGUI.update();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        AMouseInput.release(e);
//        System.out.println("release");
//        MyGUI.update();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}