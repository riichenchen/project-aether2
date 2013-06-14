/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameSource.User.Inventory;

/**
 *
 * @author Liu
 */
public class EtcItem extends InventoryItem{

    public EtcItem(String itemId, float x, float z) {
        super(itemId, x, z);
    }

    @Override
    public void use() {
        //does nothing. Etc's can't be used
    }
    
}
