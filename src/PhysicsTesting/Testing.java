/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsTesting;

import PhysicsSpace.PhysicsSpace;
import Spatial.Spatial;

/**
 *
 * @author Robert
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class Testing extends JFrame implements ActionListener{
    Timer myTimer;   
    testPanel game;

    public Testing(){
        super("Physics test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,650);

        myTimer = new Timer(10, this);	 // trigger every 100 ms
        myTimer.start();

        game = new testPanel();
        add(game);

        setResizable(false);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent evt){
        if(game!= null && game.ready){
            game.repaint();
        }
    }

    public static void main(String[] args){
        Testing t = new Testing();
    }
}
class testPanel extends JPanel implements MouseMotionListener, MouseListener{
    public boolean ready;
    private PhysicsSpace space;
    private HashMap <Integer,Spatial> spatials;
    int count;
    int colidecount = 0;
    public testPanel(){
        ready = false;
        space = new PhysicsSpace(1.81f,800,600) {};
        spatials = new HashMap <> ();
        Spatial a = new Spatial(400f,100f,15f, 800f,30f,30f, 0f,0f,0) {
            @Override
            public void collideEffect(Spatial s) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Spatial b = new Spatial(400f,300f,15f, 800f,30f,30f, 0f,0f,1) {
            @Override
            public void collideEffect(Spatial s) {
                System.out.println("colliding!"+(colidecount++));
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        spatials.put(0,a);
        space.addSpatial(0,a);
        spatials.put(1,b);
        space.addSpatial(1,b);
        
        count  = 2;
        addMouseMotionListener(this);
        addMouseListener(this);
        setSize(800,600);
    }
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public void addSpatial(float x, float y,float z, float l, float h, float w, float m, float c, int collide){
        Spatial s = new Spatial (x,y,z,l,h,w,m,c,collide) {

            @Override
            public void collideEffect(Spatial s) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        s.setRotation(10);
        spatials.put(s.getId(),s);
        space.addSpatial(s.getId(), s);
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        space.update();
        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        Spatial[] spatialslist = spatials.values().toArray(new Spatial[0]);
        for (Spatial s: spatialslist){
            g.fillRect((int)(s.getX() - s.getLength()/2),(int)(s.getY()-s.getHeight()/2),(int)s.getLength(), (int)s.getHeight());
        }
        for (Spatial s: spatialslist){
            if (s.getY() < 0){
                spatials.remove(s.getId());
                space.addRemoveMessage(s.getId());
            }
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}    
    @Override
    public void mouseClicked(MouseEvent e){}  
    @Override
    public void mousePressed(MouseEvent e){
        float mx = (float)(e.getX());
	float my = (float)(e.getY());
        addSpatial(mx,my,10f, 20f,20f,20f, 1f,0.1f,0);
    }
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
}
