/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 * The Equipment Handler class is a static class that manages all equipment
 * going in and out of the player. The first four adds will return warnings 
 * as these four will add equipment when there is none initially loaded onto
 * the character.
 */
public class EquipHandler {
    private static HashMap<String, EquipItem> allEquipped = new HashMap<>();
    
    public static void init(){
        //initialize some keys
        allEquipped.put("head", null);
        allEquipped.put("body", null);
        allEquipped.put("feet", null);
        allEquipped.put("weapon",null);
    }
    
    //Reverses all effects from this item
    private static void reverseEffects(EquipItem item){
        for (String s: item.getStats())
            CharacterHandler.addStat(s, -item.getStat(s));
    }
    //executes all effects from this item
    private static void addEffects(EquipItem item){
        for (String s: item.getStats())
            CharacterHandler.addStat(s, item.getStat(s));
    }
    
    //returns the item found in the slotkey provided
    public static InventoryItem getItem(String key){
        return allEquipped.get(key);
    }
    
    //Equips the item provided.
    //will send the current equipped item in the slot, back to the inventory 
    //and reverse its effects if it exists
    public static InventoryItem equip(EquipItem item){
        String type = item.getEquipType();
        EquipItem swap = allEquipped.get(type);
        if (swap != null){
            reverseEffects(swap);
            InventoryHandler.addItem(swap);
        }
        allEquipped.put(type, item);
        addEffects(item);
        InventoryHandler.removeItem(item);
        return swap;
    }
    
    //Exports all equipped items in a neat array of string
    public static String[] exportItems(){
        String[] export = new String[4];
        InventoryItem[] equipItems = allEquipped.values().toArray(new InventoryItem[0]);
        for (int i = 0; i < 4; i++){
            reverseEffects((EquipItem)equipItems[i]);
            export[i] = equipItems[i].getKey();
        }
        return export;
    }
    //Takes in an array of 4 strings and equips all equipment corresponding to these keys
    public static void equipAll(String[] equipData){
        for (String each: equipData){
            equip((EquipItem)ItemFactory.getItem(each));
        }
    }
}
