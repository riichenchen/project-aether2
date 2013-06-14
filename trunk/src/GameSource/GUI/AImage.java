package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;

/* AImage.java          @Chen~
 * This class extends AComponent to create a custom Image object.
 * This allows for Images to be added to AComponent and AContainers
 * and also adds the additional function of displaying and additional
 * imaged when moused over.
 */

public class AImage extends AComponent{
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Constructors
    public AImage(String name, int x, int y){
        //Creates an AImage at position (x,y). It assumes the background image
        //can be retrieved from AImageFactory using the name paratemer, and no
        //separate image for mouseovers.
        super();
        setName(name);
        setCallable(false);
        bg=AImageFactory.getImage(name);
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
        setLocation(x,y);
        
    }
    public AImage(String name, int x, int y, Image i, Image j){
        //Creates an AImage at position (x,y) with a preloaded foreground and 
        //background images rather than loading from AImageFactory
        super();
        setCallable(false);
        setName(name);
        bg=i; fg=j;
        setSize(bg.getWidth(null),bg.getHeight(null));
        setLocation(x,y);
        setVisible(true);
    }
     //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 
    public void update(){}
    public void call(){}
    public void draw(Graphics g){
        if (visible() && parent.visible()){
            g.drawImage(bg,parent.x+x,parent.y+y,null);
            if (fg!=null && (collidepoint(AMouseInput.mx, AMouseInput.my))){
                g.drawImage(fg,AMouseInput.mx,AMouseInput.my+20,null);
            }
        }
    }
}