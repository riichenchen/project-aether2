package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/*AWindow.java          @Chen~
 * Creates a window that could contain anything ;the most elementary extension 
 * of AComponent that fills in the update, call, and draw methods.
 */


public class AWindow extends AComponent{
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Constructors*
	public AWindow(){
            super();
	}
	public AWindow (String name){
		super();
		setName(name);
	}
	public AWindow(int x, int y,int wid,int hgt){
		super(x,y,wid,hgt);
	}
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
	public void update(){
        if (locked){
            lockShift();
            if (AMouseInput.released(AMouseInput.LEFT))
                unlock();
        }
	}

	public void call(){
        for (AComponent c: subComponents){          //Check subComponents: were any of them clicked?
            if (c.callable()&& ( c instanceof AContainer ||c.collidepoint(AMouseInput.mx,AMouseInput.my))){
                c.call();
                break;
            }
        }
    }
	
	public void draw(Graphics g){
		if (visible){
            if (bg!=null){
                g.drawImage(bg,parent.x+x, parent.y+y, null);
            }
            else if (AImageFactory.getImage(name)!=null)
                g.drawImage(AImageFactory.getImage(name), parent.x+x, parent.y+y, null);
            for (AComponent c: subComponents){
                c.draw(g);
            }
		}
	}
}