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
 */
public class EquipHandler {
    private static HashMap<String, EquipItem> allEquipped = new HashMap<>();
    public static void init(){
        allEquipped.put("head", null);
        allEquipped.put("body", null);
        allEquipped.put("feet", null);
        allEquipped.put("weapon",null);
    }
    
    private static void reverseEffects(EquipItem item){
        for (String s: item.getStats())
            CharacterHandler.addStat(s, -item.getStat(s));
    }
    
    private static void addEffects(EquipItem item){
        for (String s: item.getStats())
            CharacterHandler.addStat(s, item.getStat(s));
    }
    
    public static InventoryItem getItem(String key){
        return allEquipped.get(key);
    }
    
    public static InventoryItem equip(EquipItem item){
        String type = item.getEquipType();
        EquipItem swap = allEquipped.get(type);
        if (swap != null){
            reverseEffects(swap);
        }
        allEquipped.put(type, item);
        addEffects(item);
        return swap;
    }
}
