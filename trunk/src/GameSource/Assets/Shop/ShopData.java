/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Shop;

import GameSource.User.Inventory.InventoryItem;
import GameSource.User.ItemFactory;
import java.util.LinkedList;

/**
 * The data class for shop. Loaded in through the asset manager. Keeps a 
 * linked list of all items in the shop.
 * @author Shiyang
 */
public class ShopData {
    private LinkedList<ShopItemData> shopItems;
    private String npc;
    
    public ShopData(String npc){
        this.shopItems = new LinkedList<>();
        this.npc = npc;
    }
    
    public void addItem(String key,int value){
        shopItems.add(new ShopItemData(ItemFactory.getItem(key),value));
    }
    public LinkedList<ShopItemData> getShopItems(){
        return shopItems;
    }
    public String getNpc(){
        return npc;
    }
}
