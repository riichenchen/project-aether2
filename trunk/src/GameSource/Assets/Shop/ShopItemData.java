/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.Assets.Shop;

import GameSource.User.Inventory.InventoryItem;

/**
 * The single item data for shop. Tracks how much the item costs
 * and the item itself
 * @author Shiyang
 */
public class ShopItemData {
    public InventoryItem item;
    public int value;
    public ShopItemData(InventoryItem item, int value){
        this.item = item;
        this.value = value;
    }
}
