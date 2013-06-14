package GameSource.GUI;

import GameSource.Assets.AssetManager;
import GameSource.User.CharacterHandler;
import Sound.SoundManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*ASkills.java          @Chen~
 * Specialty window to display the avaible skillset to a player and allow them to
 * upgrade skill levels if they have enough skillPoints. Skills are available in
 * increasing tiers (i.e. if tier 2 is available, so are tier 0,1).
 */

public class ASkills extends AWindow{
    public static final String [] nameDirect={"0","1","2"};
    private int activeTab;
    private AButton activeTabButton;
    private AButton [] buttons;             //The buttons for upgrading skillLevels
    
    private AButton [] tabs;
    private int skillPoints;
    private int job;                        //The highest tier available
    private ArrayList<APoint> labelLocs;
    private AContainer labels;              //The info displays in each tier
    private ArrayList<String[]> tiers;      //The skills available in each tier
    private String [] descriptions;         //The description of each tier
    
    
    public ASkills(){
        super("skills");
        setImage(AImageFactory.getImage("skills_main"));
        setSize(174,299);
        setMoveBar(0,0,174,23);
        
        loadDescriptions();
        labelLocs=new ArrayList<>();
        loadLocs();
        tiers=new ArrayList<String []>();
        loadTiers();
        labels=new AContainer (0,0,4,labelLocs);
        skillPoints=CharacterHandler.getStat("skillPoints");
        
        loadTabs();
        job=CharacterHandler.getStat("job");
        updateTabs();
        activeTabButton=tabs[0];
        activeTab=-1;
        
        
        buttons=new AButton[4];
        loadButtons();
        setPane("0");
        
        loadLabels();
        labels.setVisible(true);
        this.add(labels);
        setVisible(true);
        
    }
    public void loadDescriptions(){              //Loads the two-line descriptions for each tier level
        descriptions=new String [6];
        try{
            BufferedReader br=new BufferedReader(new FileReader("src/GameSource/Assets/skillDescriptions.txt"));
            for (int i=0; i<6; i++){
                descriptions[i]=br.readLine();
            }
        }catch(IOException e){System.out.println("No descriptions!");}
    }
    public void loadTabs(){     //Load all three possible tabs; some are set invisible
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
        for (int i=0; i<=job; i++){         //Set only the accessible tabs to
            tabs[i].setVisible(true);       //visible
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
    public void loadButtons(){          //Load the four buttons used to upgrade
        for (int i=0; i<4;i++){
            AButton b=new AButton("skill_"+(i),AMessage.SKILL_UP,""+(i),12,12);
            b.setLocation(134,114+39*i);
            b.setImage(AImageFactory.getImage("stat_up"));
            b.setFGImage(AImageFactory.getImage("stat_up_fg"));
            b.setVisible(false);
            add(b);
            buttons[i]=b;
        }
    }
   public void loadLocs(){              //Load the location of each skill label
        for (int i=0; i<4;i++){
            labelLocs.add(new APoint(11,94+38*i));
        }
    }
    public void increase(int a){
        //Invoked when an upgrade button is clicked. This methods performs the change.
        CharacterHandler.addSkillLevel(labels.get(a).getName(),1);
        CharacterHandler.addStat("skillPoints", -1);
    }
    public void loadLabels(){
        //Load the 4 labels by creating the layers using TextImageFactory and data
        //from SkillData.
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
    
    public void setPane(String paneName){       //Change the active pane
        int newPane=Integer.parseInt(paneName);
        if (newPane!=activeTab){
            activeTab=newPane;
            activeTabButton.displayBG();
            activeTabButton=tabs[newPane];
            activeTabButton.displayFG();
            loadLabels();
            updateButtons();
            try{
                SoundManager.getChannel("UI").stopAll();
                SoundManager.getChannel("UI").addTrack("tabswap");
            }catch(NullPointerException e){}
        }
    }
    public void updateButtons(){
            for (int i=0; i<4; i++){
                int skill=0;
                try{                //skillLevel may be null at initilization
                    skill=CharacterHandler.getSkillLevel(labels.get(i).getName());
                }catch (NullPointerException e){}
                buttons[i].setVisible(false);
                if (skillPoints>0 && skill<10)      //10 is the max skill level
                    buttons[i].setVisible(true);
            }
    }
    @Override
    public void update(){
        super.update();
        if (CharacterHandler.getStat("skillPoints")!=skillPoints){
            skillPoints=CharacterHandler.getStat("skillPoints");
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
        g.drawImage(AssetManager.getImage("tier"+(activeTab+1)),x+15,y+58,null);
        
        g.setColor(new Color(255,255,255));     //Draw the description
        g.setFont(new Font("Arial",Font.PLAIN,10));
        g.drawString(descriptions[2*activeTab],x+48,y+68);
        g.drawString(descriptions[2*activeTab+1],x+48,y+83);
        
        g.setFont(new Font("Arial",Font.PLAIN,11)); //Draw the # of skillPoints
        g.setColor(new Color(0,0,0));
        g.drawString(""+CharacterHandler.getStat("skillPoints"),x+139,y+269);
        
        for (int i=0; i<4;i++){        //Draw disabled buttons before enabled buttons
            Image tmp=AImageFactory.getImage("skill_up_disable");
            g.drawImage(tmp,x+134,y+114+39*i,null);
        }
        for (AComponent a: subComponents){
            if (a.visible()){
                a.draw(g);
            }
        }
        g.drawImage(AImageFactory.getImage("skills_main_fg"),x,y,null);
    }
}
