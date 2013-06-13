/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;
//HEYEYYYYYYYYY THIS IS THE FILE IN CHEN'S USB!!
import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Joy
 */
public class ASkills extends AWindow{
    public static final String [] nameDirect={"0","1","2"};
    private int activeTab;
    private AButton activeTabButton;
    private AButton [] buttons;
    
    private AButton [] tabs;
    private int skillPoints;
    private int job;
//    private ArrayList<APoint> buttonLocs;
//    private AContainer buttons;
    private ArrayList<APoint> labelLocs;
    private AContainer labels;
    private ArrayList<String[]> tiers;
    
    public ASkills(){
        super("skills");
        setImage(AImageFactory.getImage("skills_main"));
        setSize(174,299);
        setMoveBar(0,0,174,23);
//        buttonLocs=new ArrayList<>()/;
        
        labelLocs=new ArrayList<>();
        loadLocs();
        tiers=new ArrayList<String []>();
        loadTiers();
//        buttons=new AContainer(0,0,4, buttonLocs);
        labels=new AContainer (0,0,4,labelLocs);
        skillPoints=CharacterHandler.getStat("skillPoints");
        
        loadTabs();//Load the tabs
        job=CharacterHandler.getStat("job");
        activeTabButton=tabs[0];
        activeTab=-1;
        
        
        buttons=new AButton[4];
        loadButtons();
   //     updateButtons();
        setPane("0");
        
        loadLabels();
        labels.setVisible(true);
        this.add(labels);
        setVisible(true);
 //       setPane("equip");
 //       buttons.addScrollBar(153,65,183);
        
    }
    
    public void loadTabs(){
        tabs=new AButton [3];
        for (int i=0; i<3; i++){
            tabs[i]=new AButton(""+i,AMessage.SKILLS,""+i);
            tabs[i].setSize(30,18);
            tabs[i].setImage(AImageFactory.getImage("skill_"+tabs[i].getName()));
            tabs[i].setFGImage(AImageFactory.getImage("skill_"+tabs[i].getName()+"_fg"));
            tabs[i].setVisible(false);
            tabs[i].setLocation(7+30*i,30);
            this.add(tabs[i]);
        }
    }
    public void updateTabs(){
        for (int i=0; i<3; i++){
            tabs[i].setVisible(false);
        }
        for (int i=0; i<=job; i++){
            tabs[i].setVisible(true);
        }
    }
    public void loadTiers(){
        try{
            BufferedReader in=new BufferedReader(new FileReader("src/GameSource/Assets/skilltiers.txt"));
            int nTiers=Integer.parseInt(in.readLine());
            for (int i=0; i<nTiers; i++){
                String [] line=in.readLine().split(" ");
                tiers.add(line);
            }
        }
        catch(IOException e){System.out.println("Load shop tiers failed!");};
        
    }
    public void loadButtons(){
        for (int i=0; i<4;i++){
            AButton b=new AButton("skill_"+i,AMessage.SKILL_UP,""+i,12,12);
            b.setLocation(134,114+39*i);
            b.setImage(AImageFactory.getImage("stat_up"));
            b.setFGImage(AImageFactory.getImage("stat_up_fg"));
            b.setVisible(false);
            add(b);
            buttons[i]=b;
        }
    }
   public void loadLocs(){
        for (int i=0; i<4;i++){
//            buttonLocs.add(new APoint(134,114+40*i));
            labelLocs.add(new APoint(11,94+38*i));
        }
    }
    public void increase(int a){
        CharacterHandler.addSkillLevel(labels.get(a).getName(),1);
        CharacterHandler.addStat("skillPoints", -1);
    }
    public void loadLabels(){
        String [] skillNames=tiers.get(activeTab);
        labels.clear();
        for (int i=0;i<skillNames.length;i++){
            String key=skillNames[i];
            Image back=TextImageFactory.createSkillsLabel(key);
            Image front=TextImageFactory.createSkillDes(key,AssetManager.getSkillData(key).getDescription());
            AImage a=new AImage(key,labelLocs.get(i).x,labelLocs.get(i).y,back,front);
            a.setParent(this);
            labels.add(a);
        }
        labels.updateActiveContent();
    }
    
    public void setPane(String paneName){
        int newPane=Integer.parseInt(paneName);
        if (newPane!=activeTab){
 //           System.out.println("Pane Switch to "+paneName+"Items: "+items.length);
            activeTab=newPane;
            activeTabButton.displayBG();
            activeTabButton=tabs[newPane];
            activeTabButton.displayFG();
            loadLabels();
            updateButtons();
        }
    }
    public void updateButtons(){
            for (int i=0; i<4; i++){
                System.out.println(labels.get(i).getName());
                int skill=0;
                try{
                    skill=CharacterHandler.getSkillLevel(labels.get(i).getName());
                }catch (NullPointerException e){}
                buttons[i].setVisible(false);
                if (skillPoints>0 && skill<10)
                    buttons[i].setVisible(true);
            }
    }
    @Override
    public void update(){
        super.update();
        if (CharacterHandler.getStat("skillPoints")!=skillPoints){
            skillPoints=CharacterHandler.getStat("skillPoints");
//            System.out.println("ASkills/update   Buttons updated!");
            updateButtons();
        }
        if (CharacterHandler.getStat("job")!=job){
            job=CharacterHandler.getStat("job");
            updateTabs();
        }
        loadLabels();
    }
    
    @Override
    public void call(){
        for (AComponent c: subComponents){
                if (c.visible() && c.callable() && ( c instanceof AContainer ||c.collidepoint(AMouseInput.mx,AMouseInput.my))){
                    c.call();
                    break;
                }
            }
    }   

    @Override
    public void draw(Graphics g){
        g.drawImage(bg,x,y,null);
        for (int i=0; i<4;i++){
            Image tmp=AImageFactory.getImage("skill_up_disable");
            g.drawImage(tmp,x+134,y+114+39*i,null);
        }
        for (AComponent a: subComponents){
            if (a.visible()){
                a.draw(g);
            }
        }
        
        
        g.setFont(new Font("Arial",Font.PLAIN,11));
        g.setColor(new Color(0,0,0));
        g.drawString(""+CharacterHandler.getStat("skillPoints"),x+139,y+269);
        g.drawImage(AImageFactory.getImage("skills_main_fg"),x,y,null);
    }
}
