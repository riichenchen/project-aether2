package GameSource.GUI;

import GameSource.Assets.AssetManager;
import java.awt.Graphics;

/**
 * @(#)AButton.java
 *
 *
 * @author 
 * @version 1.00 2013/5/15
 */

public class AButton extends AComponent{
    private AMessage message;
    public AButton() {
    	setLocation(0,0);
    }
    public AButton(String label, int msgType, String msgContent){
    	setName(label);
//        setImage(AssetManager.getBlockImage(label));
    	message=new AMessage(msgType, msgContent);
    }
    public void draw(Graphics g){
        g.setColor(background);
    	g.fillRect(x,y,width,height);
    }
    public void update(){}
    
    public void call(){
        AProcessor.process(message);
    }
}