/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import GameSource.Game.GamePoint;
import GameSource.Quest.QuestData;
import GameSource.Quest.QuestManager;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
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
    private int hp,maxhp,mp,maxmp,money,level,exp,attack,defense,statPoints,skillPoints;
    private String charName;
    private SaveSkillData[] allSkills;
    private QuestData[] allQuestData;
    private String[] equipData;
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
        this.equipData = EquipHandler.exportItems();
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
        this.statPoints = CharacterHandler.getStat("statPoints");
        this.skillPoints = CharacterHandler.getStat("skillPoints");
        this.allSkills = CharacterHandler.getSaveSkillData();
        this.allQuestData = QuestManager.exportQuestData();
    }
    
    public PlayerData(int accountId,int CharacterType,GamePoint location,String mapType,SaveItemData[] loadData,
                      int[] entity_data,String charName,SaveSkillData[] allSkills,QuestData[] allQuestData,String[] equipData){
        this.characterType = CharacterType;
        this.location = location;
        this.mapType = mapType;
        this.accountId = accountId;
        String[] allItemIds = InventoryHandler.getItemIds();
        this.items = loadData;
        this.maxhp = entity_data[0];
        this.hp = entity_data[1];
        this.maxmp = entity_data[2];
        this.mp = entity_data[3];
        this.money = entity_data[4];
        this.level = entity_data[5];
        this.exp = entity_data[6];
        this.attack = entity_data[7];
        this.defense = entity_data[8];
        this.statPoints = entity_data[9];
        this.skillPoints = entity_data[10];
        this.charName = charName;
        this.allSkills = allSkills;
        this.allQuestData = allQuestData;
        this.equipData = equipData;
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
        return new int[]{maxhp,hp,maxmp,mp,money,level,exp,attack,defense,statPoints,skillPoints};
    }
    public String getCharName(){
        return charName;
    }
    public SaveSkillData[] getSkillData(){
        return allSkills;
    }
    public QuestData[] getQuestData(){
        return allQuestData;
    }
    public String[] getEquipData(){
        return equipData;
    }
}
