package GameSource.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TestGUI extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	private MyGUI myGUI;
	private InputManager in;
//	private AMouseInput mouse;
	
	public TestGUI(){
		super();
		in = new InputManager();
//		mouse = new AMouseInput ();
                MyGUI.init(in,800,600);
//		myGUI = new MyGUI(in,mouse, 800,600);
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
//    	MyGUI.update();
    }
    
    public void keyReleased(KeyEvent e) {
        in.keyUp(e.getKeyCode());

        
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
        AMouseInput.click(e);
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