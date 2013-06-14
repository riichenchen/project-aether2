package GameSource.GUI;

import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.Inventory.UseItem;
import GameSource.User.Inventory.EtcItem;
import GameSource.User.InventoryHandler;
import Sound.SoundManager;
import java.util.ArrayList;

/*AInventory.java           @Chen~
 * A specialty window to display the items available from InventoryHandler, nicely
 * sorted by type. It allows the user to view item descriptions, drag and drog items
 * into the game, and doubleclick on items to equip or use them.
 */

public class AInventory extends AWindow{
    public static final String [] nameDirect={"equip","use","other"};
    private String activeTab;
    private AButton activeTabButton;
    
    private AButton [] tabs;
    private String [] items;
    private ArrayList<APoint> buttonLocs; 
    private AContainer buttons;
    
    
    public AInventory(){
        super("invent");
        setImage(AImageFactory.getImage("invent"));
        setSize(172,393);
        setMoveBar(0,0,172,22);
		
        buttonLocs=new ArrayList<>();
        loadButtonLocs();
        buttons=new AContainer(0,0,24, buttonLocs);
        
        
        this.add(new AImage("invent_bg2",6,23));
        this.add(new AImage("invent_bg3",7,45));
        tabs=new AButton [3];                       //Load the three tabs
        for (int i=0; i<3; i++){
            tabs[i]=new AButton(nameDirect[i],AMessage.INVENTORY,nameDirect[i]);
            tabs[i].setSize(31,20);
            tabs[i].setImage(AImageFactory.getImage(tabs[i].getName()));
            tabs[i].setFGImage(AImageFactory.getImage(nameDirect[i]+"_hover"));
            tabs[i].setVisible(true);
            tabs[i].setLocation(8+31*i,27);
            this.add(tabs[i]);
        }
       
        this.add(buttons);
        buttons.addScrollBar(153,65,183);
        setVisible(true);
        activeTabButton=tabs[0];
        setPane("equip");
    }
    
    public void loadButtonLocs(){
        for(int ty=0; ty<6; ty++){
            for (int tx=0; tx<4; tx++){
                buttonLocs.add(new APoint (10+36*tx,51+36*ty));
            }
        }
    }
    
    public void loadButtons(){
        //This method grabs an array of available items from the inventory, then loops
        //through, keeps the ones that match the current pane, creates an appropriate
        //button, then adds the button to the Container.
        items = InventoryHandler.getItemIds();
        buttons.clear();
        for (int i=0;i<items.length;i++){
            String key=items[i];
            InventoryItem c=InventoryHandler.getItem(key);
            if ((c instanceof EquipItem && activeTab.equals("equip"))||
                (c instanceof UseItem && activeTab.equals("use"))||
                (c instanceof EtcItem && activeTab.equals("other"))){
                AButton b=new AButton (key,AMessage.INVENTORY_CLICK,key,33,33);
                b.setImage(c.getImage());
                b.setFGImage(TextImageFactory.merge(c.getImage(),AImageFactory.getImage("item_fg")));
                b.setHover(TextImageFactory.createDes(c));
                b.setLabel(String.format("%d",InventoryHandler.getItemQuantity(key)));
                b.setParent(this);
                buttons.add(b);
            }
        }
        buttons.updateActiveContent();
    }
    
    public void setPane(String paneName){
        if (paneName.equals(activeTab)==false){
            activeTab=paneName;
            for (int i=0; i<3; i++){
                if (nameDirect[i].equals(activeTab)){
                    activeTabButton.displayBG();
                    activeTabButton=tabs[i];
                    activeTabButton.displayFG();
                }
            }
            loadButtons();
            try{        //Catch NullPointerException from beginning when sounds aren't yet loaded
                SoundManager.getChannel("UI").stopAll();
                SoundManager.getChannel("UI").addTrack("tabswap");
            }catch (NullPointerException e){}
        }
    }
    
    @Override
    public void update(){
        super.update();
        buttons.update();
    }
}