/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.Graphics;

public class AImage extends AComponent{
    public AImage(String name){
        super();
        setName(name);
        bg=ImageFactory.getImage(name);
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
    }
    public AImage(String name, int x, int y){
        super();
        setName(name);
        bg=ImageFactory.getImage(name);
        System.out.println("IMAGE CHECK:");
        System.out.println(bg==null);
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
        setLocation(x,y);
    }
    public void update(){}
    public void call(){}
    public void draw(Graphics g){
        g.drawImage(bg,parent.x+x,parent.y+y,null);
    }
}
