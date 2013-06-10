/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.User.CharacterHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class AStats extends AWindow{
    
    public AStats(){
        super();
        setName("user_stats");
        setSize(192,216);
        setMoveBar(0,0,192,20);
        setImage(AImageFactory.getImage("stat_stat"));
        loadButtons();
        updateButtons();
    }
    public void loadButtons(){
        String [] stats={"attack","defense","maxhp","maxmp"};
        for (int i=0; i<4; i++){
            AButton b=new AButton(stats[i],AMessage.STAT_WINDOW,stats[i],12,12);
            b.setLocation(55,28+18*i);
            b.setImage(AImageFactory.getImage("stat_up"));
            b.setFGImage(AImageFactory.getImage("stat_up_fg"));
            b.setVisible(false);
            add(b);
        }
    }
    public void updateButtons(){
        if (CharacterHandler.getStat("statPoints")>0 && (subComponents.get(0).visible()==false)){
            System.out.println("AStats/updateButtons   Buttons are visible!");
            for (AComponent a: subComponents){
                a.setVisible(true);
            }
        }
        else if ((CharacterHandler.getStat("statPoints")<=0 && (subComponents.get(0).visible()))){
            System.out.println("AStats/updateButtons   Buttons are invisible!");
            for (AComponent a: subComponents){
                a.setVisible(false);
            }
        }
    }
    public void draw(Graphics g){
        String [] stats={"attack","defense","maxhp","maxmp","level","exp"};
        g.drawImage(bg,x,y,null);
        Image disabled=AImageFactory.getImage("stat_up_no");
        for (int i=0; i<6; i++){
            g.drawImage(disabled,x+55,y+28+18*i,null);
        }
        for (AComponent a: subComponents){
            if (a.visible())
                a.draw(g);
        }
        
        
        g.setFont(new Font("Arial",Font.PLAIN,11));
        g.setColor(new Color(0,0,0));
        for (int i=0; i<6; i++){
            g.drawString(""+CharacterHandler.getStat(stats[i]),x+71,y+38+i*18);
        }
        g.drawString(""+CharacterHandler.getStat("statPoints"), x+75, y+172);
    }
    
    public void call(){
        for (AComponent c: subComponents){
                if (c.visible() && c.callable() && ( c instanceof AContainer ||c.collidepoint(AMouseInput.mx,AMouseInput.my))){
                    c.call();
                    break;
                }
            }
    }
}
