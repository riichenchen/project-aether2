package GameSource.GUI;

import Sound.SoundManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/*AComponent.java           @Chen
 * This class is the main abstract class that all GUI windows extend. It contains
 * fields and methods that all windows should or may have: x,y location, size, 
 * background and foreground Images and Colors, boolean fields for adjusting 
 * accessibility, a parent reference, a list of children, etc.
 */

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
	protected ARect moveBar;        //A rectangle in which the user can click to drag the AComponent
	
	private static int idCounter=0;
	public static final AComponent NULL=new AWindow(0,0,0,0);   //The default parent
	
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
    public void add(AComponent c){
		c.parent=this;
		subComponents.add(c);
	}
	public boolean collidepoint(int mx, int my){
		return parent.x+x<=mx && mx<=parent.x+x+width && parent.y+y<=my && my<=parent.y+y+height;
	}
	public boolean moveBarCollide(){
		return moveBar==null||moveBar.collidepoint(AMouseInput.mx,AMouseInput.my,x+parent.x,y+parent.y);
	}

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Specifier Methods
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
    public void setLocation(){      //Sets the location at the center
        x=400-(width/2); y=300-(height/2)-30;
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
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Accessor Methods
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
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    //These three methods work to allow an AComponent to be "locked" to the mouse.
    //When AComponent is initially locked, the difference between the mouse location
    //and top left corner is noted. The lockShift() is then repeatedly called while
    //the AComponent is locked in order to update its loaction.
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
	
	
	public abstract void draw(Graphics g);
	public abstract void update();
	public abstract void call();                //Invoked when AComponent is clicked
}
