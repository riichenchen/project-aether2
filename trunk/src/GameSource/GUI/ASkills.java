/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

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
    
    private AButton [] tabs;
    private int skillPoints;
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
        
        //Load the tabs
        tabs=new AButton [3];
        for (int i=0; i<3; i++){
            tabs[i]=new AButton(""+i,AMessage.SKILLS,""+i);
            tabs[i].setSize(30,18);
            tabs[i].setImage(AImageFactory.getImage("skill_"+tabs[i].getName()));
            tabs[i].setFGImage(AImageFactory.getImage("skill_"+tabs[i].getName()+"_fg"));
            tabs[i].setVisible(true);
            tabs[i].setLocation(7+30*i,30);
            this.add(tabs[i]);
        }
        activeTabButton=tabs[0];
        activeTab=0;
        setPane("0");
        
        loadLabels();
        this.add(labels);
        setVisible(true);
 //       setPane("equip");
 //       buttons.addScrollBar(153,65,183);
        
    }
    
    public void loadTiers(){
        try{
            BufferedReader in=new BufferedReader(new FileReader("src/GameSource/Assets/skilltiers.txt"));
            int nTiers=Integer.parseInt(in.readLine());
            for (int i=0; i<nTiers; i++){
                String [] line=in.readLine().split(" ");
                tiers.add(line);
                System.out.println("ASkills/loadTiers  "+line.toString());
            }
        }
        catch(IOException e){System.out.println("Load shop tiers failed!");};
        
    }
    public void loadButtons(){
        for (int i=0; i<4;i++){
            AButton b=new AButton("skill_"+i,AMessage.SKILL_UP,""+i,12,12);
            b.setLocation(134,114+40*i);
            b.setImage(AImageFactory.getImage("stat_up"));
            b.setFGImage(AImageFactory.getImage("stat_up_fg"));
            b.setVisible(false);
            add(b);
        }
    }
   public void loadLocs(){
        for (int i=0; i<4;i++){
//            buttonLocs.add(new APoint(134,114+40*i));
            labelLocs.add(new APoint(11,94+40*i));
        }
    }
    public void increase(int a){
        CharacterHandler.addSkillLevel(labels.get(a).getName(),1);
        CharacterHandler.addStat("skillPoints", 0);
    }
    public void loadLabels(){
        String [] skillNames=tiers.get(activeTab);
        labels.clear();
        for (int i=0;i<skillNames.length;i++){
            String key=skillNames[i];
            Image back=TextImageFactory.createSkillsLabel(key);
            Image front=TextImageFactory.createDes(AssetManager.getSkillData(key).getName(),AssetManager.getSkillData(key).getDescription());
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
        }
    }
    public void updateButtons(){
        if (skillPoints>0 && (subComponents.get(0).visible()==false)){
//            System.out.println("AStats/updateButtons   Buttons are visible!");
            for (AComponent a: subComponents){
                a.setVisible(true);
            }
        }
        else if ((skillPoints<=0 && (subComponents.get(0).visible()))){
//            System.out.println("AStats/updateButtons   Buttons are invisible!");
            for (AComponent a: subComponents){
                a.setVisible(false);
            }
        }
    }
    @Override
    public void update(){
        super.update();
        if (CharacterHandler.getStat("skillPoints")!=skillPoints){
            skillPoints=CharacterHandler.getStat("skillPoints");
            updateButtons();
        }
        loadLabels();
    }
    
//    public void draw(Graphics g){
//        super.draw(g);
//        g.drawString(""+CharacterHandler.getStat("skillPoints"),x+139,y+269);
//        g.drawImage(AImageFactory.getImage("skills_main_fg"),x,y,null);
//    }
//    
    @Override
    
    public void draw(Graphics g){
        g.drawImage(bg,x,y,null);
//        Image disabled=AImageFactory.getImage("skill_up_no");
//        for (int i=0; i<6; i++){
//            g.drawImage(disabled,x+55,y+28+18*i,null);
//        }
        for (AComponent a: subComponents){
            if (a.visible()){
                a.draw(g);
                System.out.println("ASkills/draw  "+a.getName()+" drawn!");
            }
        }
        g.setFont(new Font("Arial",Font.PLAIN,11));
        g.setColor(new Color(0,0,0));
        g.drawString(""+CharacterHandler.getStat("skillPoints"),x+139,y+269);
        g.drawImage(AImageFactory.getImage("skills_main_fg"),x,y,null);
    }
}
