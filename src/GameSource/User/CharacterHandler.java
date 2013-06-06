/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import GameSource.User.Inventory.InventoryItem;
import Sound.SoundManager;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Shiyang
 */
public class CharacterHandler {
    public static HashMap<String,Integer> stats;
    public static InventoryHandler invenHandle;
    public static EquipHandler equipHandle;
    public static LinkedList<InventoryItem> collideItems;
    
    public static void init(){
        //Note these are all placeholders to put a key in
        stats = new HashMap<>();
        collideItems = new LinkedList<>();
        
        stats.put("hp",0);
        stats.put("mp",0);
        stats.put("attack",0);
        stats.put("defense",0);
        stats.put("exp",0);
    }
    
    public static void addStat(String stat,int val){
        stats.put(stat, stats.get(stat)+val);
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
//    TODO:
//    public static int calculateHurtDamage(AbstractMob mob){
//        
//    }
//    
//    public static int calculateDamage(AbstractMob mob){
//        
//    }
    
}
