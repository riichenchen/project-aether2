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
    protected Image label;
    protected Image hoverLabel;
    protected boolean displayFG;
    public AButton() {
    	setLocation(0,0);
    }
    public AButton(String name, int msgType, String msgContent){
    	setName(name);
//        setImage(AssetManager.getBlockImage(label));
    	message=new AMessage(msgType, msgContent);
        displayFG=false;
    }
    public AButton(String name, int msgType, String msgContent,int wid, int hgt){
    	setName(name);
        setSize(wid,hgt);
        /*
        label=ImageFactory.getImage(name);
        hoverLabel=ImageFactory.getImage(name+"_hover");
        System.out.println(label+","+hoverLabel);
//        setImage(AssetManager.getBlockImage(label));
* */
    	message=new AMessage(msgType, msgContent);
        displayFG=false;
    }
    
    public void draw(Graphics g){
  //      System.out.println(name+" hi!");
        if (bg==null){       //label==null
            g.setColor(background);
            g.fillRect(parent.x+x,parent.y+y,width,height);
        }
        else if (displayFG|| collidepoint(MyGUI.mx, MyGUI.my)){
            g.drawImage(fg,parent.x+x+(width-bg.getWidth(null))/2,parent.y+y+(width-bg.getHeight(null))/2,null);
        }
            /*
        if (collidepoint(MyGUI.mx, MyGUI.my))
            g.drawImage(label,parent.x+x+(width-label.getWidth(null))/2,parent.y+y+(height-label.getHeight(null))/2,null);
        else
            g.drawImage(hoverLabel,parent.x+x+(width-hoverLabel.getWidth(null))/2,parent.y+y+(height-hoverLabel.getHeight(null))/2,null);
        }
        */
        else{
            g.drawImage(bg,parent.x+x+(width-bg.getWidth(null))/2,parent.y+y+(width-bg.getHeight(null))/2,null);

     //       int dx=parent.x+x+(width-bg.getWidth(null))/2; int dy=parent.y+y+(width-bg.getWidth(null))/2;
  //           System.out.println(name+" button drawn at "+dx+","+dy);
        }
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
}