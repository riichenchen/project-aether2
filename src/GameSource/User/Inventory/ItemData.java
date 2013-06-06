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
 */
public class ItemData {
    //Used by itemFactory to generate an instance of this item :DD
    public String itemName;
    public String itemType;
    public String equipItemType = null;//Should be null unless this is an equipItem
    public Image image;
    public HashMap<String,Integer> stats;
    
    public ItemData(String itemName,String itemType){
        this.itemName = itemName;
        stats = new HashMap<>();
        this.itemType = itemType;
    }
    public void setEquipType(String equipType){
        this.equipItemType = equipType;
    }
    public void addStat(String stat,int value){
        stats.put(stat, value);
    }
    public void setImage(String key){
        this.image = AssetManager.getImage(key);
    }
}
