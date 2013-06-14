package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.Script.NPCFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/*ANPCChat.java             @Chen~
 * A window to display text from a non-player character. When the window is
 * opened, it is bound to an NPCFrame object through which the window navigates.
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
        //Loads all the possible buttons on the frame and sets their sizes and
        //Images so that location is the only field left to specify.
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
		
        AButton ok=new AButton("okay",AMessage.NPC_CHAT,"okay",40,16);
        ok.setImage(AImageFactory.getImage("npc_ok"));
        ok.setFGImage(AImageFactory.getImage("npc_ok_fg"));
        buttons.put("okay",ok);
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
           setSize(519,28+44+ySize);                        //Resize the window
           setLocation();
                           
           drawBG(g);                               //Get & draw buttons and bg
           setButtons(frame.getButtons());  
           for (AComponent c: subComponents){
                c.draw(g);
           }
           
           g2.setFont(new Font ("Arial",Font.PLAIN,10));    
           g2.setColor(foreground);
           drawText(g2);
           
           Image cImg=AssetManager.getImage(frame.getKey());    //Draw NPC image & name
           g.drawImage(cImg,x+27,y+27,null);
           System.out.println(AssetManager.getImage("npc_name"));
           g.drawImage(AssetManager.getImage("npc_name"), x+27,y+27+cImg.getHeight(null), null);
           g2.setColor(new Color(255,255,255));
           Image cname=TextImageFactory.create(g, frame.getKey());
           g.drawImage(cname,x+17+54-cname.getWidth(null)/2,y+27+10+cImg.getHeight(null)-cname.getHeight(null)/2,null);
        }
    }
    public void drawBG(Graphics g){
        //Draws the background of the NPC chat window, which is composed of a header,
        //a footer, and a middle segment that is repeatedly drawn to fill in the 
        //center depending on how large the window is.
        g.drawImage(AImageFactory.getImage("npc_top"),x,y,null);
        Image mid=AImageFactory.getImage("npc_mid");
        for (int i=0; i<(height-28-44)/13; i++){
            g.drawImage(mid,x,y+28+13*i,null);
        }
        g.drawImage(AImageFactory.getImage("npc_bottom"), x, y+height-44, null);
    }
    public void drawText(Graphics2D g){
        //Draws a string of text, wrapping it by word to make the text fit within
        //the boundaries of the box.
        FontMetrics fm=g.getFontMetrics();
        String [] lines=content.split("\\r?\\n");
        int drawY=y+10+27;          //Y offset from top-left corner
        for (String line: lines){
            String [] words=line.split(" ");
            String dline="";
            int lineWidth=0;
            for (String word:words){                
                lineWidth+=fm.stringWidth(word);    //Add words until the line is
                if (lineWidth>340){                          //too wide, then
                    g.drawString(dline,x+5+160,drawY);       //draw the line
                    lineWidth=fm.stringWidth(word);         //and reset
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
            int lineWidth=0;
            String [] words=line.split(" ");        
            for (String word:words){
                lineWidth+=fm.stringWidth(word);    //Add words until the line is
                if (lineWidth>340){                 //too wide, then reset the
                    lineWidth=fm.stringWidth(word); //line and add one to
                    ans++;                          //the number of lines.
                }
                lineWidth+=fm.stringWidth(" ");
            }
        }
        return Math.max(130,ans*fm.getHeight()+20);     //Minimum height is 130px
    }
    
    public void setButtons(String[]info){
        //Every frame of an NPC chat box has up to three buttons specified by 
        //an Array in the NPCFrame. This method gets that Array, then loops
        //through and maps the three given buttons to their appropriate locations
        subComponents.clear();
        if (info[0]!="null"){               //Button 1 is either "end" or nothing       
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

    //These functions are eventually called by AProcessor upon button clicks to
    //load the text for the next frame.
    public void next() {
        String s=frame.next();
        if (s!=null){
            setContent(s);
        }
        else{
            end();
        }
    }
    public void prev() {
        setContent(frame.prev());
    }
    public void end() {
        MyGUI.closeWindow("npcchat");
    }
}
