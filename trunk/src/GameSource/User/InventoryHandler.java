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
    
    public static void clearAll(){
        allItems = new HashMap<>();
        itemQuantities = new HashMap<>();
    }
    
    public static void addItem(InventoryItem item){
        String itemKey = item.getKey();
        if (allItems.containsKey(itemKey)){
            itemQuantities.put(itemKey,itemQuantities.get(itemKey)+1);
        } else {
            allItems.put(itemKey, item);
            itemQuantities.put(itemKey,1);
        }
    }
    //This should ONLY be called when loading in a player because
    //at this point we guarantee this will be the first appearance
    //of the item in the inventory
    public static void addItem(InventoryItem item,int quantity){
        String itemKey = item.getKey();
        allItems.put(itemKey,item);
        itemQuantities.put(itemKey, quantity);
    }
    public static void removeItem(InventoryItem item){
        String itemKey = item.getKey();
        if (allItems.containsKey(itemKey)){
            int val = itemQuantities.get(itemKey);
            if (val == 1){
                allItems.remove(itemKey);
                itemQuantities.remove(itemKey);
            } else {
                itemQuantities.put(itemKey, itemQuantities.get(itemKey)-1);
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
    
    public static int getItemQuantity(String key){
        return itemQuantities.get(key);
    }
    
    public static boolean hasItem(String id, int quantity){
        if (!allItems.containsKey(id) || itemQuantities.get(id) < quantity){
            return false;
        }
        return true;
    }
}
