/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import GameSource.Assets.Portals.Portal;
import GameSource.User.Inventory.InventoryItem;
import Sound.SoundManager;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Shiyang
 */
public class CharacterHandler {
    private static HashMap<String,Integer> stats;
    private static InventoryHandler invenHandle;
    private static EquipHandler equipHandle;
    private static LinkedList<InventoryItem> collideItems;
    private static Portal currentPortal = null;
    private static String charName;
    
    public static void init(){
        //Note these are all placeholders to put a key in
        stats = new HashMap<>();
        collideItems = new LinkedList<>();
        
        stats.put("hp",0);
        stats.put("mp",0);
        stats.put("maxhp",0);
        stats.put("maxmp",0);
        stats.put("attack",0);
        stats.put("defense",0);
        stats.put("exp",0);
        stats.put("money", 0);
        stats.put("level",0);
    }
    
    public static void addStat(String stat,int val){
        stats.put(stat, stats.get(stat)+val);
    }
    
    public static int getStat(String stat){
        return stats.get(stat);
    }
    
    public static void addCollideItem(InventoryItem i){
        collideItems.add(i);
    }
    
    public static void clearCollideItems(){
        collideItems.clear();
    }
    public static void pickUpItem(){
        if (collideItems.peek() != null){
            SoundManager.getChannel("UI").addTrack("PickUpSound");
            InventoryItem myItem = collideItems.poll();
            InventoryHandler.addItem(myItem);
            myItem.getMap().removeSpatial(myItem);
        }
    }
    public static void setCurrentPortal(Portal port){
        currentPortal = port;
    }
    public static Portal getCurrentPortal(){
        return currentPortal;
    }
    
    public static void addAllStats(String[] keys,int[] newStats){
        for (int i = 0; i < newStats.length; i++){
            addStat(keys[i],newStats[i]);
        }
    }
    public static void setName(String name){
        charName = name;
    }
    public static String getName(){
        return charName;
    }
//    TODO:
//    public static int calculateHurtDamage(AbstractMob mob){
//        
//    }
//    
//    public static int calculateDamage(AbstractMob mob){
//        
//    }
    
}
