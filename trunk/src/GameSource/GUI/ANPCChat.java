/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.Script.NPCFrame;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joy
 */
public class ANPCChat extends AWindow{
    public static final int PREV=0;
    public static final int NEXT=1;
    public static final int END=2;
    
    private Map<String,AButton> buttons;
    private NPCFrame frame;
    private String content;
    
    public ANPCChat(){
        super("npcchat");
        buttons= new HashMap<>();
        loadButtons();
        setFG(0,0,0);
        setMoveBar(0,0,519,28);
    }
    public void loadButtons(){
        AButton next=new AButton("next",AMessage.NPC_CHAT,"next",40,16);
        next.setImage(AImageFactory.getImage("npc_next"));
        next.setFGImage(AImageFactory.getImage("npc_next_fg"));
        buttons.put("next",next);
        AButton prev=new AButton("prev",AMessage.NPC_CHAT,"prev",40,16);
        prev.setImage(AImageFactory.getImage("npc_previous"));
        prev.setFGImage(AImageFactory.getImage("npc_previous_fg"));
        buttons.put("prev",prev);
        AButton end=new AButton("end",AMessage.NPC_CHAT,"end",91,16);
        end.setImage(AImageFactory.getImage("npc_end"));
        end.setFGImage(AImageFactory.getImage("npc_end_fg"));
        buttons.put("end",end);
        AButton yes=new AButton("yes",AMessage.NPC_CHAT,"yes",57,16);
        yes.setImage(AImageFactory.getImage("npc_yes"));
        yes.setFGImage(AImageFactory.getImage("npc_yes_fg"));
        buttons.put("yes",yes);
        AButton no=new AButton("no",AMessage.NPC_CHAT,"no",57,16);
        no.setImage(AImageFactory.getImage("npc_no"));
        no.setFGImage(AImageFactory.getImage("npc_no_fg"));
        buttons.put("no",no);
        AButton ok=new AButton("ok",AMessage.NPC_CHAT,"ok",40,16);
        ok.setImage(AImageFactory.getImage("npc_ok"));
        ok.setFGImage(AImageFactory.getImage("npc_ok_fg"));
        buttons.put("ok",ok);
    }
    public void setContent(String c){
        content=c;
    }
    public void setFrame(NPCFrame f){
        frame=f;
    }
    @Override
    public void draw(Graphics g){
        if (visible){
           Graphics2D g2=(Graphics2D)g;
           int ySize=((calcHeight(g2)+13)/13)*13;
           setSize(519,28+44+ySize);
           String [] stuff={"null","prev","no"};
 //          setButtons(stuff);
           setButtons(frame.getButtons());
           drawBG(g);
           g2.setFont(new Font ("Arial",Font.PLAIN,10));
           g2.setColor(foreground);
           drawText(g2);
           for (AComponent c: subComponents){
                c.draw(g);
           }
        }
    }
    public void drawBG(Graphics g){
        g.drawImage(AImageFactory.getImage("npc_top"),x,y,null);
        Image mid=AImageFactory.getImage("npc_mid");
        for (int i=0; i<(height-28-44)/13; i++){
            g.drawImage(mid,x,y+28+13*i,null);
        }
        g.drawImage(AImageFactory.getImage("npc_bottom"), x, y+height-44, null);
    }
    public void drawText(Graphics2D g){
        FontMetrics fm=g.getFontMetrics();
        String [] lines=content.split("\\r?\\n");
        int drawY=y+10+27;          //Y offset from top-left corner
        for (String line: lines){
            String [] words=line.split(" ");
            String dline="";
            int lineWidth=0;
            for (String word:words){
    		lineWidth+=fm.stringWidth(word);
                if (lineWidth>340){
                    g.drawString(dline,x+5+160,drawY);
                    lineWidth=fm.stringWidth(word);
                    dline="";
                    drawY+=fm.getHeight();
                }
                lineWidth+=fm.stringWidth(" ");
                dline+=word+" ";
            }
            g.drawString(dline,x+5+160,drawY);
            drawY+=fm.getHeight();
        }
    }
    
    public int calcHeight(Graphics2D g){
        FontMetrics fm=g.getFontMetrics();
        String [] lines=content.split("\\r?\\n");
        int ans=lines.length;
        for (String line: lines){
            String [] words=line.split(" ");
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
        return Math.min(130,ans*fm.getHeight()+20);
    }
    
    public void setButtons(String[]info){
        subComponents.clear();
        if (info[0]!="null"){
            AButton end=buttons.get("end");
            end.setLocation(new APoint(9,height-23));
            end.setVisible(true);
            add(end);
        }
        AButton b1=buttons.get(info[1]);
        AButton b2=buttons.get(info[2]);
        if (b2!=null){
            b2.setLocation(new APoint(519-28-b2.width,height-57));
            b2.setVisible(true);
            add(b2);
            if (b1!=null){
                b1.setLocation(new APoint(519-28-b2.width-8-b1.width,height-57));
                b1.setVisible(true);
                add(b1);
            }
        }
        else if (b1!=null){
            b1.setLocation(new APoint(519-28-b1.width,y+height-57));
            b1.setVisible(true);
            add(b1);
        }
    }

    public void next() {
        setContent(frame.next());
    }
    public void prev() {
        setContent(frame.prev());
    }
    public void end() {
        frame.endChat();
    }
}
