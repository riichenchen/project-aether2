package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import GameSource.User.LevelManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*AHUD.java         @Chen~
 * A window to display vital game information (stats, HP, etc) at the bottom of
 * the gamescreen. The HUD is immutable and has highest priority.
 */
public class AHUD extends AWindow{
	public AHUD(){
		super();
		setLocation(0,600-85-30);
		setSize(800,85);
		setName("hud");
		setMoveable(false);
		setImage(AImageFactory.getImage("hudback"));
		addImages();
		
		
		
		setVisible(true);
	}
    public void loadButtons(){
        //Loads the quit and chat buttons on the HUD
		AButton quit=new AButton("hud_quit",AMessage.QUIT,"die",23,22);
		quit.setLocation(543,24);
		quit.setImage(AImageFactory.getImage(quit.getName()));
		quit.setFGImage(AImageFactory.getImage(quit.getName()+"_fg"));
		quit.setVisible(true);
		this.add(quit);
		
		AButton open_chat=new AButton("hud_chat",AMessage.OPEN_WINDOW,"chat",23,22);
		open_chat.setLocation(520,24);
		open_chat.setImage(AImageFactory.getImage(open_chat.getName()));
		open_chat.setFGImage(AImageFactory.getImage(open_chat.getName()+"_fg"));
		open_chat.setVisible(true);
		this.add(open_chat);
    }
	public void addImages(){
		this.add(new AImage("level",2,50));
		this.add(new AImage("level_fg",2+2,50));
		this.add(new AImage("chatbox_out",2,23));
		this.add(new AImage("chatbox",60,25));
		this.add(new AImage("chatbox_head",2,25));
		this.add(new AImage("chatbox_fg",2,25));
	}
	
	public void draw(Graphics g){
		super.draw(g);
		drawStats(g);
	}
	
	public void drawStats(Graphics g){
        //Draws in the HP, MP, EXP stat meters by calculating the length of the
        //bar, then blitting the appropriate images in, then adds a text label.
		g.drawImage(AImageFactory.getImage("stat"),223,y+49,null);
		Graphics2D g2=(Graphics2D)g;
		FontMetrics fm=g2.getFontMetrics();
		
		int [] sizes=new int[3];    //HP: 0, MP: 1, EXP: 2
		String [] labels=new String [3];
		sizes[0]=(140*CharacterHandler.getStat("hp")/CharacterHandler.getStat("maxhp"))-3;      //3=width of beginning and end pieces
		labels[0]=String.format("[%d/%d]",CharacterHandler.getStat("hp"),CharacterHandler.getStat("maxhp"));
		sizes[1]=(140*CharacterHandler.getStat("mp")/CharacterHandler.getStat("maxmp"))-3;
		labels[1]=String.format("[%d/%d]",CharacterHandler.getStat("mp"),CharacterHandler.getStat("maxmp"));
		int maxexp=LevelManager.requiredExp(CharacterHandler.getStat("level"));
		sizes[2]=(310*CharacterHandler.getStat("exp")/maxexp)-3;
		labels[2]=String.format("[%d/%d]",CharacterHandler.getStat("exp"),maxexp);
		g.setFont(new Font ("Arial",Font.PLAIN,10));
		g.setColor(new Color(255,255,255));
		
		for (int i=0; i<2; i++){        //Draw HP and MP
			g.drawImage(AImageFactory.getImage("stat"+i+"_0"),251+171*i,y+51,null);     //Gauge head
			Image middle= AImageFactory.getImage("stat"+i+"_1");                        //Middle piece
			for (int j=0;j<sizes[i];j++){
				g.drawImage(middle,252+j+171*i,y+51,null);
			}
			g.drawImage(AImageFactory.getImage("stat"+i+"_2"),252+sizes[i]+171*i,y+51,null);    //End piece
			g.drawString(labels[i],390+171*i-fm.stringWidth(labels[i]) ,y+60);
		}
		g.drawImage(AImageFactory.getImage("stat2_0"),251,y+67,null);       //Draw EXP
		Image middle= AImageFactory.getImage("stat2_1");
		for (int j=0;j<sizes[2];j++){
			g.drawImage(middle,252+j,y+67,null);
		}
		g.drawImage(AImageFactory.getImage("stat2_2"),252+sizes[2],y+67,null);
		g.drawString(labels[2],561-fm.stringWidth(labels[2]) ,y+76);
        
		g.drawImage(AImageFactory.getImage("stat_fg"),252,y+49,null);       //Draw the cover
	}
		
}