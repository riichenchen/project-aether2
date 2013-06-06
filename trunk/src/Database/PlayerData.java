/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import GameSource.Game.GamePoint;
import GameSource.User.InventoryHandler;
import java.io.Serializable;

/**
 *
 * @author Shiyang
 */
public class PlayerData implements Serializable{
    private int characterType;
    private GamePoint location;
    private String mapType;
    private int accountId;
    private SaveItemData[] items;
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
        String[] allItemIds = InventoryHandler.getItemIds();
        this.items = new SaveItemData[allItemIds.length];
        for (int i = 0; i < allItemIds.length; i++){
            //save and load using the item's key and not name as the name can have spacing
            items[i] = new SaveItemData(InventoryHandler.getItem(allItemIds[i]).getKey(),InventoryHandler.getItemQuantity(allItemIds[i]));
        }
    }
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType,SaveItemData[] loadData){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
        String[] allItemIds = InventoryHandler.getItemIds();
        this.items = loadData;
    }
    
    public int getCharacterType() {
        return characterType;
    }

    public GamePoint getLocation() {
        return location;
    }

    public String getMapId() {
        return mapType;
    }
    
    public int getAccountId() {
        return accountId;
    }
    public SaveItemData[] getItems(){
        return items;
    }
}
