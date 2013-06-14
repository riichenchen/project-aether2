/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User.Inventory;

import GameSource.User.CharacterHandler;
import GameSource.User.InventoryHandler;
import java.util.HashMap;

/**
 *
 * @author Shiyang
 * The standard use item.
 * As expected, it applies its stats and then
 * removes itself from the user's inventory
 */
public class UseItem extends InventoryItem {
    private HashMap<String,Integer> stats;
    
    public UseItem(String itemid,int x,int z) {
        super(itemid,x,z);
        this.stats = new HashMap<>();
    }
    
    public void addStat(String stat,int value){
        stats.put(stat, value);
    }
    
    @Override
    public void use() {
        for (String s: stats.keySet().toArray(new String[0])){
            CharacterHandler.addStat(s, stats.get(s));
        }
        InventoryHandler.removeItem(this);
    }
    
}
