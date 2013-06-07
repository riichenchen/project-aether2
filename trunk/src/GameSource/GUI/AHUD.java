/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Joy
 */
public class AHUD extends AComponent{
    public AHUD(){
        super();
        setLocation(0,0);
        setSize(800,20);
        setName("hud");
        AButton quit=new AButton("quit",AMessage.QUIT,"die");
        quit.setBG(255,255,0);
        quit.setSize(50,20);
        quit.setLocation(0,0);
        this.add(quit);
        
    }
    public void update(){}
    public void call(){
        int rx = MyGUI.mx-x;
            int ry = MyGUI.my-y;
            System.out.println("rx,ry:"+rx+","+ry);
            for (AComponent c: subComponents){
                System.out.println(c.name);
                if (c.collidepoint(rx,ry)){
                    c.call();
                    break;
                }
            }
    }
    public void draw(Graphics g){
        g.setColor(new Color(0,0,0));
        g.fillRect(20, 20, 70, 70);     //Replace with player image
        g.setColor(new Color(255,0,0));
        g.fillRect(90, 20, (int)(150*0.8), 10);   //Replace with player.health/maxhealth
        g.setColor(new Color(0,255,255));
        g.fillRect(90, 40, (int)(150*0.9), 10);   //Replace with player.mana/maxmana
        g.setColor(background);
        g.fillRect(0,0,800,20);
        for (AComponent c: subComponents){
		int ox=c.x; int oy=c.y;
		c.x+=x; c.y+=y;
		c.setVisible(true);
		c.draw(g);
		c.x=ox; c.y=oy;
	}
    }
        
}
