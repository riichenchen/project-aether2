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
    private ArrayList <Box> spatials;

    public RotPanel(){
        ready = false;
        spatials = new ArrayList <>();
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
        for (int i = 0; i < spatials.size(); i ++){
            g.fillPolygon(spatials.get(i).getShape());
            spatials.get(i).setLocalRotation(1);
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
        Box b = new Box(mx,my,40f,100f,0f,count);
        spatials.add(b);
        count ++;
    }
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
}


class Box{
    public final float x,y,length,height;
    private double rot;
    public final int id;
    public Box(float x, float y, float l, float h, double r, int i){
        id = i;
        this.x = x;
        this.y = y;
        length = l;
        height = h;
        rot = Math.toRadians(r) % (2*Math.PI);
    }
    public void setRotation(double r){
        rot = Math.toRadians(r) % (2*Math.PI);
    }
    public void setLocalRotation(double r){
        rot = (rot + Math.toRadians(r)) % (2*Math.PI);
    }
    public double getRotation(){
        return Math.toDegrees(rot);
    }
    public Polygon getShape(){
        double angle1 = Math.PI/2 - rot - Math.atan(length/height);
        double angle2 = rot - Math.atan(length/height);
        double dis = Math.hypot(length/2,height/2);
        
//        Point p1 = new Point((int)(x - length/2 * Math.cos(rot)), (int)(y - length/2 * Math.sin(rot)));
//        Point p2 = new Point((int)(x + height/2 * Math.cos(Math.PI/2 - rot)),(int)(y - height/2 * Math.sin(Math.PI/2 -rot)));
//        Point p3 = new Point((int)(x + length/2 * Math.cos(rot)), (int)(y + length/2 * Math.sin(rot)));
//        Point p4 = new Point((int)(x - height/2 * Math.cos(Math.PI/2 - rot)),(int)(y + height/2 * Math.sin(Math.PI/2 -rot)));
        Point p1 = new Point((int)(x + dis * Math.sin(angle2)),(int)(y - dis * Math.cos(angle2)));
        Point p2 = new Point((int)(x + dis * Math.cos(angle1)),(int)(y - dis * Math.sin(angle1)));
        Point p3 = new Point((int)(x - dis * Math.sin(angle2)),(int)(y + dis * Math.cos(angle2)));
        Point p4 = new Point((int)(x - dis * Math.cos(angle1)),(int)(y + dis * Math.sin(angle1)));
//        
        int[] xpoints = new int[] {p1.x,p2.x,p3.x,p4.x};
        int[] ypoints = new int[] {p1.y,p2.y,p3.y,p4.y};
        
        Polygon polygon = new Polygon(xpoints, ypoints, 4);
        return polygon;
    }
}
