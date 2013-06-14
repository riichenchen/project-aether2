package GameSource.GUI;

import Sound.SoundManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


public abstract class AComponent{
	protected int x,y, width, height, lx, ly;		//lx, ly : locked x,y position
	protected Color background, foreground;
	protected Font font;
	protected boolean visible, callable, focused, locked, moveable;
	protected String name;
	protected int id;
	protected AComponent parent; 
	protected ArrayList<AComponent> subComponents;
	protected Image bg,fg;
	protected ARect moveBar;
	
	private static int idCounter=0;
	public static final AComponent NULL=new AWindow(0,0,0,0);
	
	public AComponent(){
		x=0; y=0; width=0; height = 0;
		background=new Color(229,229,229);
		foreground=new Color(229,229,229);
		font = new Font ("Arial",Font.PLAIN,10);
		visible=false;
		callable=true;
		focused=false;
		moveable=true;
		subComponents= new ArrayList<AComponent>();
		parent=AComponent.NULL;
		moveBar=new ARect(x,y,width,height);
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
		return parent.x+x<=mx && mx<=parent.x+x+width && parent.y+y<=my && my<=parent.y+y+height;
	}
	public boolean collide (AComponent a){
		return x<=a.x && a.x<=x+width && y<=a.y && a.y<=y+height;
	}
	public boolean moveBarCollide(){
		return moveBar==null||moveBar.collidepoint(AMouseInput.mx,AMouseInput.my,x+parent.x,y+parent.y);
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
	public void setLocation(APoint a){
		x=a.x; y=a.y;
        }
	public void setSize(int wid, int hght){
		width=wid; height = hght;
	}
	public void setName(String s){
		name=s;
	}
	public void setImage (Image g){
		bg=g;
	}
	public void setFGImage(Image g){
		fg=g;
	}
	public void setParent(AComponent c){
		parent=c;
	}
	public void setMoveBar(int x, int y, int w, int h){
		moveBar=new ARect(x,y,w,h);
	}
	public void setVisible(boolean b){
		visible=b;
	}
	public void setCallable(boolean b){
		callable=b;
	}
	public void setFocused(boolean b){
		focused=b;
	}
	public void setMoveable(boolean b){
		moveable=b;
	}
	public void lock(){
		lx=AMouseInput.mx-x;
		ly=AMouseInput.my-y;
		locked = true;
                SoundManager.getChannel("UI").addTrack("dragstart");
	}
	public void unlock(){
		locked= false;
	}
	public void lockShift(){
		setLocation(AMouseInput.mx-lx, AMouseInput.my-ly);
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
	public boolean callable(){
		return callable;
	}
	public boolean locked(){
		return locked;
	}
	public boolean moveable(){
		return moveable;
	}
	public void add(AComponent c){
		c.parent=this;
		subComponents.add(c);
	}
	public abstract void draw(Graphics g);
	public abstract void update();
	public abstract void call(); 			
}
