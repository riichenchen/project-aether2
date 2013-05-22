/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PhysicsTesting;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Robert
 */
public class RotationTesting extends JFrame implements ActionListener{
    Timer myTimer;   
    RotPanel game;
    
    public RotationTesting(){
        super("Rotation test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,650);

        myTimer = new Timer(10, this);	 // trigger every 100 ms
        myTimer.start();

        game = new RotPanel();
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
        RotationTesting t = new RotationTesting();
    }
}
class RotPanel extends JPanel implements MouseListener, MouseMotionListener{
    public boolean ready;
    private int count;
    private ArrayList <Box> boxes;

    public RotPanel(){
        ready = false;
        boxes = new ArrayList <>();
        count = 0;
        
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
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        boolean collide = false;
        for (int i = 0; i < boxes.size(); i ++){
            g.fillPolygon(boxes.get(i).getShape());
            if (boxes.size() > 1){
                for (int j = 0; j < boxes.size(); j++){
                    if (! boxes.get(i).equals(boxes.get(j))){
                        if (boxes.get(i).collide(boxes.get(j)) || boxes.get(j).collide(boxes.get(i))){
                            collide = true;
                        }
                    }
                }
            }
            if (collide == false){
                boxes.get(i).setLocalRotation(1);
            }
            collide = false;
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
        Box box = new Box(mx,my,30f,50f,count);
        boxes.add(box);
        count ++;
    }
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
}
class Box{
    static int id;
    public float x,y,l,h;
    private double r;
    public Box(float x, float y, float l, float h,int i){
        this.x = x;
        this.y = y;
        this.l = l;
        this.h = h;
        r = 0;
        id = i;
    }
    public boolean collide(Box s){
        //if collision occurs: call the collideListener(pass in colliding object);
        //return false;
        
        boolean collide = false;
        boolean rotated = false;
        double arot = r;
        
        if (arot != 0){
            s.setLocalRotation(-arot);
            rotated = true;
        }
        if (s.getShape().intersects((double)(x-l/2),(double)(y-h/2),(double)l,(double)h) || s.getShape().contains((double)(x-l/2),(double)(y-h/2),(double)l,(double)h)){
            collide = true;
        }
        if (rotated){
            s.setLocalRotation(arot);
        }
        if (collide){
            return true;
        }
        else{}return false;
    }
    public Polygon getShape(){
        double angle1 = Math.PI/2 - r - Math.atan(l/h);
        double angle2 = r - Math.atan(l/h);
        double dis = Math.hypot(l/2,h/2);
        
        Point p1 = new Point((int)(x + dis * Math.sin(angle2)),(int)(y - dis * Math.cos(angle2)));
        Point p2 = new Point((int)(x + dis * Math.cos(angle1)),(int)(y - dis * Math.sin(angle1)));
        Point p3 = new Point((int)(x - dis * Math.sin(angle2)),(int)(y + dis * Math.cos(angle2)));
        Point p4 = new Point((int)(x - dis * Math.cos(angle1)),(int)(y + dis * Math.sin(angle1)));
      
        int[] xpoints = new int[] {p1.x,p2.x,p3.x,p4.x};
        int[] ypoints = new int[] {p1.y,p2.y,p3.y,p4.y};
        
        Polygon shape = new Polygon(xpoints, ypoints, 4);
        return shape;
    }
    public void setLocalRotation(double rot){
        r = (r + Math.toRadians(rot)) % (2*Math.PI);
    }
    public void setRotation(double rot){
        r = Math.toRadians(rot) % (2*Math.PI);
    }
}
