/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.GUI;

import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.Inventory.UseItem;
import GameSource.User.InventoryHandler;
import java.awt.Graphics;
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
    private String [] skillNames;
    private ArrayList<APoint> buttonLocs;
    private AContainer buttons;
    private ArrayList<APoint> labelLocs;
    private AContainer labels;
    
    
    public ASkills(){
        super("skills");
        setImage(AImageFactory.getImage("skills_main"));
        setSize(174,299);
        setMoveBar(0,0,174,23);
        buttonLocs=new ArrayList<>();
        labelLocs=new ArrayList<>();
        loadLocs();
        buttons=new AContainer(0,0,4, buttonLocs);
        labels=new AContainer (0,0,4,labelLocs);
        
        tabs=new AButton [3];

        for (int i=0; i<3; i++){
            tabs[i]=new AButton("skill_"+i,AMessage.SKILLS,"skill_"+i);
            tabs[i].setSize(30,18);
            tabs[i].setImage(AImageFactory.getImage(tabs[i].getName()));
            tabs[i].setFGImage(AImageFactory.getImage(tabs[i].getName()+"_fg"));
            tabs[i].setVisible(true);
            tabs[i].setLocation(7+30*i,30);
            this.add(tabs[i]);
        }
        
        this.add(buttons);
        this.add(labels);
        setVisible(true);
        activeTabButton=tabs[0];
 //       setPane("equip");
 //       buttons.addScrollBar(153,65,183);
        
    }
    
    public void loadLocs(){
        for (int i=0; i<4;i++){
            buttonLocs.add(new APoint(134,114+40*i));
            labelLocs.add(new APoint(11,94+40*i));
        }
    }
    
    public void loadLabels(){
        //skillNames= getSkills();
  //      labels.clear();
//        int buttonNo=0;
//        for (int i=0;i<skillNames.length;i++){
//            String key=skillNames[i];
            //get SkillData somehow
            //if type matches the current pane
//                AButton b=new AButton (key,AMessage.SKILLS_CLICK,key,33,33);
//                b.setImage(AImageFactory.getImage("skill_up"));
//                b.setFGImage(TextImageFactory.createDes(name, description);
//                b.setParent(this);
//                buttons.add(b);
   //             buttonNo++;
           // }
       // }
        //buttons.updateActiveContent();
    }
    
    public void setPane(String paneName){
//        if (paneName.equals(activeTab)==false){
// //           System.out.println("Pane Switch to "+paneName+"Items: "+items.length);
//            activeTab=paneName;
//            for (int i=0; i<3; i++){
//                if (nameDirect[i].equals(activeTab)){
//                    activeTabButton.displayBG();
//                    activeTabButton=tabs[i];
//                    activeTabButton.displayFG();
//                }
//            }
//            loadButtons();
//        }
    }
    
    @Override
    public void update(){
        super.update();
        buttons.update();
    }
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.drawImage(AImageFactory.getImage("skills_main_fg"),x,y,null);
    }
}
