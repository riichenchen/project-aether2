/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User.Inventory;

import GameSource.Assets.AssetManager;
import java.awt.Image;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 * The item data class is a data container that contains information
 * about a specific item
 */
public class ItemData {
    //Used by itemFactory to generate an instance of this item :DD
    public String itemName;
    public String itemType;
    public String equipItemType = null;//Should be null unless this is an equipItem
    public String itemDescription = "";
    public Image image;
    public int sellPrice;
    
    public HashMap<String,Integer> stats;
    
    public ItemData(String itemName,String itemType){
        this.itemName = itemName;
        stats = new HashMap<>();
        this.itemType = itemType;
    }
    //standard set/get/add/get methods
    //Notes: Yes, it's public
    public void setEquipType(String equipType){
        this.equipItemType = equipType;
    }
    public void addStat(String stat,int value){
        stats.put(stat, value);
    }
    public void setImage(String key){
        this.image = AssetManager.getImage(key);
    }
    public void setItemDescription(String descrip){
        this.itemDescription = descrip;
    }
    public void setSellPrice(int price){
        this.sellPrice = price;
    }
}
