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
        /*
        label=ImageFactory.getImage(name);
        hoverLabel=ImageFactory.getImage(name+"_hover");
        System.out.println(label+","+hoverLabel);
//        setImage(AssetManager.getBlockImage(label));
* */
    	message=new AMessage(msgType, msgContent);
        displayFG=false;
        label="";
    }
    
    public void draw(Graphics g){
  //      System.out.println(name+" hi!");
        if (bg==null){       //label==null
            g.setColor(background);
            g.fillRect(parent.x+x,parent.y+y,width,height);
        }
        else if (fg!=null && (displayFG||collidepoint(MyGUI.mx, MyGUI.my))){
  //          System.out.println("hiii check collision");
  //          System.out.println(collidepoint(MyGUI.mx, MyGUI.my));
            g.drawImage(fg,parent.x+x+(width-bg.getWidth(null))/2,parent.y+y+(height-bg.getHeight(null))/2,null);
        }
            /*
        if (collidepoint(MyGUI.mx, MyGUI.my))
            g.drawImage(label,parent.x+x+(width-label.getWidth(null))/2,parent.y+y+(height-label.getHeight(null))/2,null);
        else
            g.drawImage(hoverLabel,parent.x+x+(width-hoverLabel.getWidth(null))/2,parent.y+y+(height-hoverLabel.getHeight(null))/2,null);
        }
        */
        else{
  //          System.out.println(bg==null);
            g.drawImage(bg,parent.x+x+(width-bg.getWidth(null))/2,parent.y+y+(height-bg.getHeight(null))/2,null);

  //          int dx=parent.x+x+(width-bg.getWidth(null))/2; int dy=parent.y+y+(width-bg.getWidth(null))/2;
  //           System.out.println(name+" button drawn at "+dx+","+dy);
        }
        if (label!="" && label!=null)
            g.drawString(label,parent.x+x,parent.y+y+10);
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
}