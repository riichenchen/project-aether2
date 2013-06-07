package GameSource.GUI;/**/

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class ALabel extends AComponent{
	private String text;
	private Image pic;
	public ALabel(){
		super();
		text="";
		pic=null;
	}
	public ALabel(String content){
	//Initialize a label with text content
		super();
		text=content;
		pic=null;
		setVisible(true);
	}
	public ALabel(Image img){
	//Initialize a label with image content
		super();
		text="";
		pic=img;
		setSize(img.getWidth(null),img.getHeight(null));
		setVisible(true);
	}
	public void setText(String content){
		text=content;
		pic=null;
	}
	public void setImage(Image img){
		pic=img;
		text="";
                setSize(pic.getWidth(null),pic.getHeight(null));
	}
	public void draw(Graphics g){
		if (pic!=null){
			g.drawImage(pic,parent.x+x,parent.y+y,background,null);
		}
                else{
                        Graphics2D g2=(Graphics2D)g;
                        FontMetrics fm=g2.getFontMetrics();
                        setSize(fm.stringWidth(text)+10,fm.getHeight()+10);
			g.setFont(font);
			g.setColor(foreground);
			g.drawString(text,parent.x+x,parent.y+y);
		}
	}
	
	public void update(){};	//Label does not need to be updated
	public void call(){};
}