package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

//Change to use HashMap instead of ArrayList?

public class AWindow extends AComponent{
	
    //Constructors....a ton of them*****************************************
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
    //*********************************************************************
        
	public void update(){
            if (locked){
		lockShift();
		if (AMouseInput.released(AMouseInput.LEFT))
                    unlock();
            }
	}

	public void call(){
            for (AComponent c: subComponents){
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
        
        public void setBackground(String name){
            bg=AImageFactory.getImage(name);
            setSize(bg.getWidth(null),bg.getHeight(null));
        }
}