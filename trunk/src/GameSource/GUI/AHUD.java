/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
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

/**
 *
 * @author Joy
 */
public class AHUD extends AWindow{
    private ArrayList<APoint> buttonLocs;
    AButton quit;
    public AHUD(){
        super();
        setLocation(0,600-85-30);
        setSize(800,85);
        setName("hud");
        setMoveable(false);
        setImage(ImageFactory.getImage("hudback"));
        
        addImages();
        AButton quit=new AButton("hud_quit",AMessage.QUIT,"die",23,22);
        quit.setLocation(543,24);
        quit.setImage(ImageFactory.getImage(quit.getName()));
        quit.setFGImage(ImageFactory.getImage(quit.getName()+"_fg"));
        quit.setVisible(true);
        this.add(quit);
        
        AButton open_chat=new AButton("hud_chat",AMessage.OPEN_WINDOW,"chat",23,22);
        open_chat.setLocation(520,24);
        open_chat.setImage(ImageFactory.getImage(open_chat.getName()));
        open_chat.setFGImage(ImageFactory.getImage(open_chat.getName()+"_fg"));
        open_chat.setVisible(true);
        this.add(open_chat);
        
        
        setVisible(true);
    }
    public void addImages(){
        this.add(new AImage("level",2,50));
        this.add(new AImage("level_fg",2+2,50));
        this.add(new AImage("chatbox_out",2,23));
        this.add(new AImage("chatbox",60,25));
        this.add(new AImage("chatbox_head",2,25));
        this.add(new AImage("chatbox_fg",2,25));
  //      ATextField text=new ATextField(442,13,65,35);
   //     text.setMoveable(false);
  //      this.add(text);
    }

    public void loadButtonLocs(){
        for (int tx=569; tx<744;tx+=98){
            for (int ty=25; ty<26; ty+=1){
                buttonLocs.add(new APoint(tx,ty));
            }
        }
    }
    
    public void draw(Graphics g){
        super.draw(g);
        drawStats(g);
 
    }
    public void drawStats(Graphics g){
        g.drawImage(ImageFactory.getImage("stat"),223,y+49,null);
        Graphics2D g2=(Graphics2D)g;
        FontMetrics fm=g2.getFontMetrics();
        int [] sizes=new int[2];
  //      BufferedImage [] labels=new BufferedImage[2];
        String [] labels=new String [2];
        sizes[0]=(140*CharacterHandler.getStat("hp")/CharacterHandler.getStat("maxhp"))-3;      //Beginning and end pieces
        
        labels[0]=String.format("[%d/%d]",CharacterHandler.getStat("hp"),CharacterHandler.getStat("maxhp"));
  //      labels[0]=TextImageFactory.create(g,String.format("[%d/%d]",CharacterHandler.getStat("hp"),CharacterHandler.getStat("maxhp")));
        sizes[1]=(140*CharacterHandler.getStat("mp")/CharacterHandler.getStat("maxmp"))-3;
        labels[1]=String.format("[%d/%d]",CharacterHandler.getStat("mp"),CharacterHandler.getStat("maxmp"));
  //      labels[1]=TextImageFactory.create(g,String.format("[%d/%d]",CharacterHandler.getStat("mp"),CharacterHandler.getStat("maxmp")));
        g.setFont(new Font ("Arial",Font.PLAIN,10));
        g.setColor(new Color(255,255,255));
        for (int i=0; i<2; i++){
            g.drawImage(ImageFactory.getImage("stat"+i+"_0"),251+171*i,y+51,null);
            Image middle= ImageFactory.getImage("stat"+i+"_1");
            for (int j=0;j<sizes[i];j++){
                g.drawImage(middle,252+j+171*i,y+51,null);
            }
            g.drawImage(ImageFactory.getImage("stat"+i+"_2"),252+sizes[i]+171*i,y+51,null);
    //        System.out.println(labels[1].getWidth(null)+","+labels[1].getHeight(null));
   //         g.drawImage(labels[i],386+171*i-labels[i].getWidth(null)+171*i,y+51,null);
            g.drawString(labels[i],390+171*i-fm.stringWidth(labels[i]) ,y+60);
   //         g.drawRect(386-labels[i].getWidth(null)+171*i,y+51,labels[i].getWidth(null),labels[i].getHeight(null));
        }
        g.drawImage(ImageFactory.getImage("stat_fg"),252,y+49,null);
    }
        
}

class TestPanel extends JPanel{
	AHUD hud;
	public TestPanel(){
		super();
		hud=new AHUD();
	}
	public void paintComponent (Graphics g){
		hud.draw(g);
	}
}
class TestHUD extends JFrame{
	TestPanel test;
	public TestHUD(){
		super("AHHHH2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(800,600);
		test=new TestPanel();
		add(test);
		repaint();
		setVisible(true);
	}
	public static void main (String [] args){
            AssetManager.init();
                ImageFactory.init();
                CharacterHandler.init();
                CharacterHandler.addStat("maxhp",500);
                CharacterHandler.addStat("maxmp",10000);
                CharacterHandler.addStat("hp",352);
                CharacterHandler.addStat("mp",10000);

		new TestHUD();
	}
	
}