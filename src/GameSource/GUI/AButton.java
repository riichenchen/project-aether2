package GameSource.GUI;

import Sound.SoundManager;
import java.awt.Graphics;
import java.awt.Image;

public class AButton extends AComponent{
	protected AMessage message;
	protected String label;
	protected boolean displayFG;
	protected Image mouseHover;
        protected boolean soundPlayed;
	public AButton() {
		setLocation(0,0);
	}
	public AButton(String name, int msgType, String msgContent){
		setName(name);
		message=new AMessage(msgType, msgContent);
		displayFG=false;
		label="";
                soundPlayed=false;
	}
	public AButton(String name, int msgType, String msgContent,int wid, int hgt){
		setName(name);
		setSize(wid,hgt);
		message=new AMessage(msgType, msgContent);
		displayFG=false;
		label="";
                soundPlayed=false;
	}
	
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