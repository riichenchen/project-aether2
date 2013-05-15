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

    public testPanel(){
        ready = false;
        space = new PhysicsSpace(9.81f) {};
        spatials = new HashMap <> ();
        count  = 0;
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
    public void addSpatial(float x, float y,float z, float l, float w, float h, float m, float c){
        Spatial s = new Spatial (x,y,z,l,w,h,m,c) {

            @Override
            public void collideEffect(Spatial s) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
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
            g.fillRect((int)(s.getX()), (int)(s.getZ()), (int)(s.getBoundingBox().getLength()), (int)(s.getBoundingBox().getHeight()));
        }
        for (Spatial s: spatialslist){
            if (s.getZ() < 0){
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
	float mz = (float)(e.getY());
        addSpatial(mx,0f,mz,20f,20f,20f,1f,0.1f);
    }
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
}
