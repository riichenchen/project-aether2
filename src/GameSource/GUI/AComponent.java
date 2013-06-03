package GameSource.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

//CURRENT TASK AT HAND: Components containing components, AButton, Specialty Components
//KEY ISSUES TO RESOLVE: Line 95

public abstract class AComponent{
	protected int x,y, width, height, lx, ly;		//lx, ly : locked x,y position
	protected Color background, foreground;
	protected Font font;
	protected boolean visible, enabled, focused, locked;
	protected String name;
	protected int id;
	protected AComponent parent; 
        protected Image image;
	
	private static int idCounter=0;
	
	public AComponent(){
		x=0; y=0; width=0; height = 0;
		background=new Color(229,229,229);
		foreground=new Color(229,229,229);
		font = new Font ("Arial",Font.PLAIN,10);
		visible=false;
		enabled=true;
		focused=false;
		id=idCounter++;
	}
	public AComponent(int nx,int ny,int nw,int nh){
		x=nx; y=ny; width=nw; height=nh;
		background=new Color(229,229,229);
	}
	
        @Override
	public int hashCode(){
		return id;
	}
	public boolean collidepoint(int mx, int my){
		return x<=mx && mx<=x+width && y<=my && my<=y+height;
	}
	public boolean collide (AComponent a){
		return x<=a.x && a.x<=x+width && y<=a.y && a.y<=y+height;
	}

	public void setBG(int r, int g, int b){
		background=new Color(r,g,b);
	}
	public void setFG(int r, int g, int b){
		foreground=new Color(r,g,b);
	}
	public void setLocation(int x, int y) {
		this.x=x; this.y=y;
	}
	public void setSize(int wid, int hght){
		width=wid; height = hght;
	}
	public void setName(String s){
		name=s;
	}
        public void setImage (Image g){
                image=g;
        }

	public void setVisible(boolean b){
		visible=b;
	}
	public void setEnabled(boolean b){
		enabled=b;
	}
	public void setFocused(boolean b){
		focused=b;
	}
	public void lock(){
//		System.out.println(name+" locked");
		lx=AGUI.mx-x;
		ly=AGUI.my-y;
		locked = true;
		
	}
	public void unlock(){
//		System.out.println(name+" unlocked");
//		System.out.println("("+x+","+y+"),"+lx+","+ly);
		locked= false;
	}
	public void lockShift(){
		setLocation(AGUI.mx-lx, AGUI.my-ly);
	}
	public int id(){
		return id;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight (){
		return height;
	}
	public String getName(){
		return name;
	}
	public boolean focus(){
		return focused;
	}
	public boolean visible(){
		return visible;
	}
	public boolean locked(){
		return locked;
	}
	
	public abstract void draw(Graphics g);
	public abstract void update();
	public abstract void call(); 			//????
		
}
