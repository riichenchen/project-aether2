package GameSource.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
//package GUI;

//TO-DO: Allow for cursor position change from mouse
public class ATextField extends AComponent{
	//Single-lined TextField that allows and displays keyboard input.
	//If the line is too long, a portion is trimmed from the beginning.
	//Key states are received from the AGUI public static field 'keys'.
	
	public static final int FULL_SCREEN_WIDTH=-10000;
	public static final int FULL_SCREEN_HEIGHT=-10001;
	
	private String content;
	private int cursorLoc;		//0 means before 1st character, 1 before 2nd, etc.
	
	public void insert(String ins){
		content=content.substring(0,cursorLoc)+ins+
				content.substring(cursorLoc,content.length());
		cursorLoc+=ins.length();
	}
	public void insert(int c){
		content=content.substring(0,cursorLoc)+(char)c+
				content.substring(cursorLoc,content.length());
		cursorLoc++;
	}
	public void delete(int start, int end){
	//Delete the start-th to end-th characters
		content=content.substring(0,start)+content.substring(end-1,content.length());
		cursorLoc=start;
	}
	public void delete(){
	//Deletes the character in front of the cursor
		if (cursorLoc>0){
			content=content.substring(0,cursorLoc-1)+content.substring(cursorLoc,content.length());
			cursorLoc--;
		}
	}
	public ATextField() {
		//Create a default-sized TextField in the top-left corner
    	super ();
    	content="";
    	cursorLoc=0;
    	setSize(400,30);
    	setBG(255,255,255);
    	setFG(0,0,0);
    	setName("TextBox"+this.id());
    	setVisible(true);
    }
    public ATextField(int wid, int hgt){
    	//Create a custom-sized TextField
    	super ();
    	content="";
    	cursorLoc=0;
    	setSize(400,30);
    	setBG(255,255,255);
    	setFG(0,0,0);
    	setName("TextBox"+this.id());
    	setVisible(true);
    	setSize(wid,hgt);
    }
    
    public void update(){
    	for (int i=0; i<MyGUI.keys.length; i++){
    		if (MyGUI.keys[i] && KeyCharMap.isTypeable(i,0)){
    			insert(KeyCharMap.getChar(i,MyGUI.keys[KeyEvent.VK_SHIFT]));
    		}
    	}
    	if (MyGUI.keys[KeyEvent.VK_BACK_SPACE]){
    		delete();
    	}
    	if (MyGUI.keys[KeyEvent.VK_ENTER]){
    		content="";
    		cursorLoc=0;
    	}
    	if (MyGUI.keys[KeyEvent.VK_LEFT]){
    		cursorLoc=Math.max(cursorLoc-1,0);
    	}
    	if (MyGUI.keys[KeyEvent.VK_RIGHT]){
    		cursorLoc=Math.min(cursorLoc+1,content.length());
    	}
    }
    
    public void draw(Graphics g){
    	if (visible){
	    	Graphics2D g2= (Graphics2D)g;
 	   	FontMetrics fm = g2.getFontMetrics();
 		String line = content;			//The portion to draw
    		while (fm.stringWidth(line)>width-20){
                    //Remove chars from head if string is too long
                    line=line.substring(1,line.length());
    		}
    		g2.setColor(background);
    		g2.fillRect(parent.x+x,parent.y+y,width,height);
    		g2.setFont(font);
			g2.setColor(foreground);
    		g2.drawString(line,parent.x+x+10,parent.y+y+10);
    	}
    }
    public void call(){};
    /*
    public static void main (String [] args){
    	ATextField test=new ATextField();
    	test.insert("01234");
    	test.insert(97);
    	test.insert(KeyCharMap.getChar(65,true));
    	System.out.println(test.content);
    }
    */
}