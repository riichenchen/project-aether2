/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class AContainer extends AComponent{
    private ArrayList<AComponent> allContent;
    public ArrayList<AComponent> content;
    public ArrayList<APoint> buttonLocs;
    private AScrollButton [] scrollBar;
    private int scrollstart;
    private int size;
    public AContainer(int xx,int yy, int sz, ArrayList<APoint> pos){
        super();
        setName("container");
        setLocation(xx,yy);
        size=sz;
        allContent=new ArrayList<>();
        content=new ArrayList<>();
        scrollstart=0;
        buttonLocs=pos;
    }
    
    @Override
    public void add(AComponent c){
        allContent.add(c);
    }
    
    public void draw(Graphics g){
        for (AComponent a: content){
            a.draw(g);
        }
        if (scrollBar!=null){
            Image mid=AImageFactory.getImage("scroll_mid");
            for (int yy=scrollBar[0].y+12; yy<scrollBar[1].y; yy++){
                g.drawImage(mid,parent.x+scrollBar[0].x,parent.y+yy,null);
            }
            for (AScrollButton a:scrollBar){
                a.draw(g);
                g.setColor(new Color (255,255,255));
            }
        }
        /*
        for (int i=scrollstart;i<Math.min(content.size(),scrollstart+size);i++){
            AComponent b=content.get(i);
            b.setVisible(true);
            b.setLocation(buttonLocs.get(i-scrollstart));
            b.draw(g);
        }
        */
    }
    public AComponent get(int i){
        return content.get(i);
    }
    public ArrayList<AComponent> content(){
        return content;
    }
    public void clear(){
        content.clear();
        allContent.clear();
    }
    public int size(){
        return allContent.size();
    }
    public int start(){
        return scrollstart;
    }
    public void call(){
        for (AComponent a: content){
            if (a.visible() && a.collidepoint(AMouseInput.mx, AMouseInput.my)){
                a.call();
            }
        }
        if (scrollBar!=null){
            for (AScrollButton a:scrollBar){
                if (a.visible() && a.collidepoint(AMouseInput.mx, AMouseInput.my)){
                    a.call();
                } 
            }
        }
    }
    public void update(){
        if (scrollBar!=null)
            scrollBar[2].update();
    }
    public void updateActiveContent(){
        content.clear();
        for (int i=scrollstart;i<Math.min(allContent.size(),scrollstart+size);i++){
            AComponent b=allContent.get(i);
            b.setVisible(true);
            b.setLocation(buttonLocs.get(i-scrollstart));
            content.add(b);
        }
    }
    
    public void addScrollBar(int sx, int sy, int sh){
        scrollBar=new AScrollButton[3];
        scrollBar[0]=(new AScrollButton("up",sx,sy-12,this));         //12: height of up button
        scrollBar[1]=(new AScrollButton("down",sx,sy+sh,this));
        scrollBar[2]=(new AScrollButton("bar",sx+1,sy,this));           
    }
    
    public void scroll(int amount){
    }
    public void scrollDown(int amount){}
    class AScrollButton extends AButton{
        private String type;
        private int ly;
        protected AContainer cparent;
        public AScrollButton(String type, int x, int y, AContainer c){
            setName("type");
            this.type=type;
            setLocation(x,y);
            setImage(AImageFactory.getImage("scroll_"+type));
            setFGImage(AImageFactory.getImage("scroll_"+type+"_fg"));
            setSize(bg.getWidth(null),bg.getHeight(null));
            cparent=c;
            setParent(c.parent);
            System.out.println("AContainer/AScrollButton/Const.  Check Scroll Parent: ");
            System.out.println(parent.getName()+" at "+parent.x+","+parent.y);
            ly=-1;
            setVisible(true);
        }
        @Override
        public void call(){
            AScrollButton bar=scrollBar[2];
            if (type.equals("bar") && ly==-1){
                ly=AMouseInput.my-parent.y-y;
            }
            else if (type.equals("up")){
                int ny=Math.min(Math.max(scrollBar[0].y+12,bar.y-10),scrollBar[1].y-26);
                cparent.scroll(ny-y);
                bar.setLocation(bar.x,ny);
            }
            else if (type.equals("down")){
                int ny=Math.min(Math.max(scrollBar[0].y+12,bar.y+10),scrollBar[1].y-26);
                cparent.scroll(ny-y);
                bar.setLocation(bar.x,ny);
            }
        }
        @Override
        public void update(){
            if (type.equals("bar")){
                if (ly!=-1){
                    int ny=Math.min(Math.max(scrollBar[0].y+12,AMouseInput.my-parent.y-ly),scrollBar[1].y-bg.getHeight(null));
                    cparent.scroll(ny-y);
                    setLocation(x,ny);
                    if (AMouseInput.released(AMouseInput.LEFT)||AMouseInput.buttonsHeld[AMouseInput.LEFT]==AMouseInput.NO);
                        ly=-1;
                }
            }
        }
        
    }
    
    
}
