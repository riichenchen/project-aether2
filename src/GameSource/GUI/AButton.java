package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @(#)AButton.java
 *
 *
 * @author 
 * @version 1.00 2013/5/15
 */

public class AButton extends AComponent{
    protected AMessage message;
    protected String label;
    protected boolean displayFG;
    protected Image mouseHover;
    public AButton() {
    	setLocation(0,0);
    }
    public AButton(String name, int msgType, String msgContent){
    	setName(name);
//        setImage(AssetManager.getBlockImage(label));
    	message=new AMessage(msgType, msgContent);
        displayFG=false;
        label="";
    }
    public AButton(String name, int msgType, String msgContent,int wid, int hgt){
    	setName(name);
        setSize(wid,hgt);
    	message=new AMessage(msgType, msgContent);
        displayFG=false;
        label="";
    }
    
    public void draw(Graphics g){
        g.drawImage(bg,parent.x+x,parent.y+y,null);
        if (fg!=null && (displayFG||collidepoint(AMouseInput.mx, AMouseInput.my))){
            g.drawImage(fg,parent.x+x,parent.y+y,null);
        }

        if (label!="" && label!=null)
            g.drawString(label,parent.x+x,parent.y+y+10);
        if (mouseHover!=null && collidepoint(AMouseInput.mx, AMouseInput.my)){
            g.drawImage(mouseHover,AMouseInput.mx,AMouseInput.my+20,null);
        }
  //      g.drawRect(parent.x+x,parent.y+y,width,height);
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