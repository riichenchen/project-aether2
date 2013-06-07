package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

//Change to use HashMap instead of ArrayList?

public class AWindow extends AComponent{
	
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
	public void update(){
		if (locked){
			lockShift();
			if (MyGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
				unlock();
		}
	}

	public void call(){
    //        int rx = MyGUI.mx-x;
   //         int ry = MyGUI.my-y;
    //        System.out.println("rx,ry:"+rx+","+ry);
            for (AComponent c: subComponents){
    //            System.out.println(c.name);
                if (c.callable() && (c instanceof AContainer || c.collidepoint(MyGUI.mx,MyGUI.my))){
                    System.out.println(c.getName());
                    System.out.println(c.callable());
                    c.call();
                    break;
                }
            }
        }
	
	public void drawChildren(Graphics g){		//drawChildren or use parent field?
		for (AComponent c: subComponents){
			int ox=c.x; int oy=c.y;
			c.x+=x; c.y+=y;
   //                     System.out.println(c.x+","+c.y);
			c.setVisible(true);
			c.draw(g);
			c.x=ox; c.y=oy;
		}
	}
	
	public void draw(Graphics g){
		if (visible){
                    if (ImageFactory.getImage(name)!=null)
                        g.drawImage(ImageFactory.getImage(name), x, y, null);
                    else{
			g.setColor(background);
			g.fillRect(x,y,width,height);
                    }
  //                  drawChildren(g);
                    for (AComponent c: subComponents){
			c.draw(g);
                    }
		}
	}
        
        public void setBackground(String name){
            bg=ImageFactory.getImage(name);
            setSize(bg.getWidth(null),bg.getHeight(null));
        }
}