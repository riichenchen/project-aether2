package GameSource.GUI;

import Sound.SoundManager;
import java.awt.Graphics;
import java.awt.Image;

/*AButton.java          @Chen~
 * This class creates a button AComponent that the user can click to control
 * the game. Buttons contain AMessages to specify resultant actions, and also
 * contain all information to display different images depending on mouse position.
 */

public class AButton extends AComponent{
	protected AMessage message;
	protected String label;                 //An additional label (for the inventory)
	protected boolean displayFG;            //Whether or not to display the foreground image
	protected Image mouseHover;             //What to display when moused over
    protected boolean soundPlayed;
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Constructors
	public AButton() {          //Empty button
		setLocation(0,0);
	}
	public AButton(String name, int msgType, String msgContent){    //Button with AMessage
		setName(name);
		message=new AMessage(msgType, msgContent);
		displayFG=false;
		label="";
        soundPlayed=false;
	}
	public AButton(String name, int msgType, String msgContent,int wid, int hgt){
        //Button with AMessage and known size dimensions
		setName(name);
		setSize(wid,hgt);
		message=new AMessage(msgType, msgContent);
		displayFG=false;
		label="";
        soundPlayed=false;
	}
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void draw(Graphics g){
		g.drawImage(bg,parent.x+x,parent.y+y,null);
		if (fg!=null && (displayFG||collidepoint(AMouseInput.mx, AMouseInput.my))){
			g.drawImage(fg,parent.x+x,parent.y+y,null);
		}
		if (label!=null && label.equals("")==false)
			g.drawString(label,parent.x+x,parent.y+y+10);
		if (mouseHover!=null && collidepoint(AMouseInput.mx, AMouseInput.my)){
			g.drawImage(mouseHover,AMouseInput.mx,AMouseInput.my+20,null);
            if (soundPlayed==false){
                //Plays a sound if 1) moused over and 2) sound was not already played
                SoundManager.getChannel("UI").addTrack("mouseover");
                soundPlayed=true;
            }
		}
        if (collidepoint(AMouseInput.mx, AMouseInput.my)==false)
            soundPlayed=false;
	}
	public void update(){}
	
	public void call(){
		AProcessor.process(message);
	}
    
    //Specifier Methods
	public void displayFG(){
		displayFG=true;
	}
	public void displayBG(){
		displayFG=false;
	}
	public void setLabel(String s){
		label=s;
	}
	public void setHover(Image i){
		mouseHover=i;
	}
}