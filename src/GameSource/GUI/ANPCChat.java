/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Joy
 */
public class ANPCChat extends AWindow{
    private AButton next;
    private String content;
    public ANPCChat(){
        super("npcchat");
        setFG(0,0,0);
        next=new AButton("NPCnext",AMessage.NPC_CHAT,"next block of text");
        next.setSize(50,20);
        next.setBG(255,236,139);
        this.add(next);
    }
    public void setContent(String c){
        content=c;
    }

   
    @Override
    public void draw(Graphics g){
        if (visible){
           Graphics2D g2=(Graphics2D)g;
//            System.out.println(calcHeight(g2)+40);
            setSize(350,calcHeight(g2)+40);
            setLocation(225,300-(height/2));
    //        System.out.println(y+".");
            next.setLocation(290, height-35);
            super.draw(g);
            g.setColor(foreground);
            drawText(g2);
        }
    }
    public void drawText(Graphics2D g){
        FontMetrics fm=g.getFontMetrics();
        String [] lines=content.split("\\r?\\n");
        int drawY=y+10;
        for (String line: lines){
            String [] words=line.split(" ");
            String dline="";
            int lineWidth=0;
            for (String word:words){
    		lineWidth+=fm.stringWidth(word);
                if (lineWidth>340){
                    g.drawString(dline,x+5,drawY);
                    lineWidth=fm.stringWidth(word);
                    dline="";
                    drawY+=fm.getHeight();
                }
                lineWidth+=fm.stringWidth(" ");
                dline+=word+" ";
            }
            g.drawString(dline,x+5,drawY);
            drawY+=fm.getHeight();
        }
    }
    
    public int calcHeight(Graphics2D g){
        FontMetrics fm=g.getFontMetrics();
        String [] lines=content.split("\\r?\\n");

   //     System.out.print("\n");
        int ans=lines.length;
        for (String line: lines){
            String [] words=line.split(" ");
   //                 for (String s: words){
    //        System.out.print(s+"|");
    //    }
            int lineWidth=0;
            for (String word:words){
    		lineWidth+=fm.stringWidth(word);
                if (lineWidth>340){
                    lineWidth=fm.stringWidth(word);
                    ans++;
                }
                lineWidth+=fm.stringWidth(" ");
            }
        }
        return ans*fm.getHeight();
    }
}
