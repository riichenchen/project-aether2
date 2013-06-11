/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;

/**
 * This class is transfered over between the client and server
 * to load and save item data.
 * An object of this class represents 1 inventoryitem
 * @author Shiyang
 */
public class SaveItemData implements Serializable{
    private String itemKey;
    private int quantity;
    public SaveItemData(String itemType, int quantity){
        this.itemKey = itemType;
        this.quantity = quantity;
    }

    public String getItemKey() {
        return itemKey;
    }

    public int getQuantity() {
        return quantity;
    }
    
}
