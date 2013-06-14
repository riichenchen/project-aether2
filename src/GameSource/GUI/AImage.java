package GameSource.GUI;

import java.awt.Graphics;
import java.awt.Image;

public class AImage extends AComponent{
    public AImage(String name){
        super();
        setCallable(false);
        setName(name);
        bg=AImageFactory.getImage(name);
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
    }
    public AImage(String name,Image i){
        super();
        setCallable(false);
        setName(name);
        bg=i;
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
    }
    public AImage(String name, int x, int y, Image i, Image j){
        super();
        setCallable(false);
        setName(name);
        bg=i; fg=j;
        setSize(bg.getWidth(null),bg.getHeight(null));
        setLocation(x,y);
        setVisible(true);
    }
    public AImage(String name, int x, int y){
        super();
        setName(name);
        setCallable(false);
        bg=AImageFactory.getImage(name);
        setSize(bg.getWidth(null),bg.getHeight(null));
        setVisible(true);
        setLocation(x,y);
        
    }
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