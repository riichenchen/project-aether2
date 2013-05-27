package GameSource.GUI;/**/

import java.awt.Graphics;
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
	}
	public void draw(Graphics g){
		if (pic!=null){
			g.drawImage(pic,x,y,background,null);
		}
		else{
			g.setFont(font);
			g.setColor(foreground);
			g.drawString(text,x,y);
		}
	}
	
	public void update(){};	//Label does not need to be updated
	public void call(){};
}