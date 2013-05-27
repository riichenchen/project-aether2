package GameSource.GUI;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
//package GUI;

//TO-DO: Allow for cursor position change from scrolling and mouse
public class ATextArea extends AComponent{
	
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
	public ATextArea(){
		//Create a default-sized TextArea in the top-left corner
    	super ();
    	content="";
    	cursorLoc=0;
    	setSize(300,200);
    	setBG(255,255,255);
    	setFG(0,0,0);
    	setVisible(true);
    }
    public ATextArea(int wid, int hgt){
    	//Create a custom-sized TextArea
    	super ();
    	content="";
    	cursorLoc=0;
    	setBG(255,255,255);
    	setFG(0,0,0);
    	setVisible(true);
    	setSize(wid,hgt);
    }
    
    public void update(){
    	for (int i=0; i<AGUI.keys.length; i++){
    		if (AGUI.keys[i] && KeyCharMap.isTypeable(i,0)){
    			insert(KeyCharMap.getChar(i,AGUI.keys[KeyEvent.VK_SHIFT]));
    		}
    	}
    	if (AGUI.keys[KeyEvent.VK_BACK_SPACE]){
    		delete();
    	}
    	if (AGUI.keys[KeyEvent.VK_ENTER]){
    		insert(255);
    	}
    	if (AGUI.keys[KeyEvent.VK_LEFT]){
    		cursorLoc=Math.max(cursorLoc-1,0);
    	}
    	if (AGUI.keys[KeyEvent.VK_RIGHT]){
    		cursorLoc=Math.min(cursorLoc+1,content.length());
    	}
    	
    	if (locked){
			lockShift();
			if (AGUI.mouseButtons[0]==AMouseInput.MOUSEBUTTONUP)
				unlock();
		}
    }
    
    public void draw(Graphics g){
    	if (visible){
	    	Graphics2D g2= (Graphics2D)g;
 	   		FontMetrics fm = g2.getFontMetrics();
    		g2.setColor(background);
    		g2.fillRect(x,y,width,height);
    		g2.setFont(font);
			g2.setColor(foreground);
    		
    		int drawY = y+10;
    		String line = "";
    		for (int i=0; i<content.length() && drawY-y<height-fm.getHeight(); i++){
    			if (content.charAt(i)==(char)255){
    				g2.drawString(line.substring(0,line.length()),x+10,drawY);
    				line="";
    				drawY+=fm.getHeight()+2;
    			}
    			else{
    				line+=content.charAt(i);
    				if (fm.stringWidth(line)>width-20){
    					g2.drawString(line.substring(0,line.length()-1),x+10,drawY);
    					line=""+content.charAt(i);
    					drawY+=fm.getHeight()+2;
    				}
    			}
    		}
    		g2.drawString(line,x+10,drawY);
    	}
    }
    public void call(){};
}