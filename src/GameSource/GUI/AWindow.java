package GameSource.GUI;

import java.awt.Graphics;
import java.util.ArrayList;

//Change to use HashMap instead of ArrayList?

public class AWindow extends AComponent{
	protected ArrayList<AComponent> subComponents;
	public AWindow(){
		super();
		subComponents= new ArrayList<AComponent>();
	}
	public AWindow (String name){
		super();
		setName(name);
		subComponents= new ArrayList<AComponent>();
	}
	public void update(){
		if (locked){
			lockShift();
			if (AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
				unlock();
		}
	}
	public void add(AComponent c){
		c.parent=this;
		subComponents.add(c);
	}
	public void call(){
            int rx = AGUI.mx-x;
            int ry = AGUI.my-y;
            System.out.println("rx,ry:"+rx+","+ry);
            for (AComponent c: subComponents){
                System.out.println(c.name);
                if (c.collidepoint(rx,ry)){
                    c.call();
                    break;
                }
            }
        }
	
	public void drawChildren(Graphics g){		//drawChildren or use parent field?
		for (AComponent c: subComponents){
			int ox=c.x; int oy=c.y;
			c.x+=x; c.y+=y;
			c.setVisible(true);
			c.draw(g);
			c.x=ox; c.y=oy;
		}
	}
	
	public void draw(Graphics g){
		if (visible){
			g.setColor(background);
			g.fillRect(x,y,width,height);
			drawChildren(g);
		}
	}
}