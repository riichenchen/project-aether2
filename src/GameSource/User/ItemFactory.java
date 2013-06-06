/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User;

import GameSource.Assets.AssetManager;
import GameSource.User.Inventory.EquipItem;
import GameSource.User.Inventory.InventoryItem;
import GameSource.User.Inventory.ItemData;
import GameSource.User.Inventory.UseItem;
import GameSource.game.GameMap;
import Spatial.Spatial;

/**
 *
 * @author Shiyang
 */
public class ItemFactory {
    public static InventoryItem getItem(String key){
        //Note: location for x and z are irrelevant because this method only gets an instance of said item
        ItemData dat = AssetManager.getItemData(key);
        if (dat.itemType.equals("use")){
            UseItem newItem = new UseItem(dat.itemName,0,0);
            for (String stat: dat.stats.keySet().toArray(new String[0]))
                newItem.addStat(stat, dat.stats.get(stat));
            newItem.setImage(dat.image);
            return newItem;
        } else if (dat.itemType.equals("equip")){
            EquipItem newItem = new EquipItem(dat.itemName,dat.equipItemType,0,0);
            for (String stat: dat.stats.keySet().toArray(new String[0]))
                newItem.addStat(stat, dat.stats.get(stat));
            newItem.setImage(dat.image);
            return newItem;
        }
        return null;
    }
    
    public static void spawnItem(String key,Spatial spat,GameMap map){
        InventoryItem myItem = getItem(key);
        myItem.setLocation(spat.getX(),spat.getY(),spat.getZ()+1);
        map.addSpatial(myItem);
    }
    
    public static void main(String[] args){
        AssetManager.init();
        InventoryItem myItem = ItemFactory.getItem("trollbaithelm");
        System.out.println(myItem);
    }
}
