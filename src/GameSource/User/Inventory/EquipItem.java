/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User.Inventory;

import GameSource.User.EquipHandler;
import GameSource.User.InventoryHandler;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 * The EquipItem class extends inventory item
 * and overrides the use method by equipping this item
 * it also provides some standard add and remove stat methods
 */
public class EquipItem extends InventoryItem{
    private HashMap<String,Integer> stats;
    private String equipType;
    
    public EquipItem(String itemKey,String equipType,int x, int z) {
        super(itemKey,x,z);
        this.equipType = equipType;
        this.stats = new HashMap<>();
    }

    @Override
    public void use() {
        InventoryItem item = EquipHandler.equip(this);
        InventoryHandler.removeItem(this);
        if (item != null)
            InventoryHandler.addItem(item);
    }
    
    public void addStat(String stat, int value){
        stats.put(stat, value);
    }
    
    public String getEquipType(){
        return equipType;
    }
    
    public String[] getStats(){
        return stats.keySet().toArray(new String[0]);
    }
    public int getStat(String key){
        return stats.get(key);
    }
}
