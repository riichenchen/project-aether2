/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import GameSource.User.Inventory.InventoryItem;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 */
public class InventoryHandler {
    private static HashMap<String,InventoryItem> allItems = new HashMap<>();
    private static HashMap<String,Integer> itemQuantities = new HashMap<>();
    
    public static void addItem(InventoryItem item){
        String itemId = item.getItemId();
        if (allItems.containsKey(itemId)){
            itemQuantities.put(itemId,itemQuantities.get(itemId)+1);
        } else {
            allItems.put(itemId, item);
            itemQuantities.put(itemId,1);
        }
    }
    
    public static void removeItem(InventoryItem item){
        String itemId = item.getItemId();
        if (allItems.containsKey(itemId)){
            int val = itemQuantities.get(itemId);
            if (val == 1){
                allItems.remove(itemId);
                itemQuantities.remove(itemId);
            } else {
                itemQuantities.put(itemId, itemQuantities.get(itemId)-1);
            }
        } else {
            System.out.println("WARNING: TRYING TO REMOVE NON-EXISTANT INVENTORY ITEM D:");
        }
    }
    
    public static String[] getItemIds(){
        return allItems.keySet().toArray(new String[0]);
    }
    
    public static InventoryItem getItem(String key){
        return allItems.get(key);
    }
    
}
