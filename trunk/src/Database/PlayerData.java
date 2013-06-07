/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import GameSource.Game.GamePoint;
import GameSource.User.CharacterHandler;
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
    private int hp,maxhp,mp,maxmp,money,level,exp,attack,defense;
    private String charName;
    
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
        this.hp = CharacterHandler.getStat("hp");
        this.maxhp = CharacterHandler.getStat("maxhp");
        this.mp = CharacterHandler.getStat("mp");
        this.maxmp = CharacterHandler.getStat("maxmp");
        this.money = CharacterHandler.getStat("money");
        this.level = CharacterHandler.getStat("level");
        this.exp = CharacterHandler.getStat("exp");
        this.attack = CharacterHandler.getStat("attack");
        this.defense = CharacterHandler.getStat("defense");
    }
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType,SaveItemData[] loadData,int[] entity_data,String charName){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
        String[] allItemIds = InventoryHandler.getItemIds();
        this.items = loadData;
        this.hp = entity_data[0];
        this.maxhp = entity_data[1];
        this.mp = entity_data[2];
        this.maxmp = entity_data[3];
        this.money = entity_data[4];
        this.level = entity_data[5];
        this.exp = entity_data[6];
        this.attack = entity_data[7];
        this.defense = entity_data[8];
        this.charName = charName;
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
    public int[] getEntity_data(){
        return new int[]{hp,maxhp,mp,maxmp,money,level,exp,attack,defense};
    }
    public String getCharName(){
        return charName;
    }
}
