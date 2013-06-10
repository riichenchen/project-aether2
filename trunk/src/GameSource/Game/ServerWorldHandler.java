package GameSource.Game;

import Database.DatabaseHandler;
import Database.LoginReply;
import Database.PlayerData;
import Database.SaveItemData;
import Database.SaveSkillData;
import GameSource.Assets.AssetManager;
import GameSource.Quest.QuestData;
import GameSource.Quest.QuestRequirement;
import GameSource.User.CharacterHandler;
import GameSource.User.EquipHandler;
import GameSource.game.GameMap;
import Networking.Messages.Message;
import Networking.Server.ClientManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerWorldHandler {
    private DatabaseHandler db;
    private ClientManager clientManager;
    private GameMap[] allMaps;

    public ServerWorldHandler(DatabaseHandler db) {
        this.db = db;     
        this.allMaps = AssetManager.getAllMaps();
        EquipHandler.init();
        CharacterHandler.init();
    }
    
    public void bindManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }
    
    public void broadcastMessage(Message m){
        if (clientManager == null){
            System.out.println("SEVERE: NO CLIENTMANAGER!");
            System.exit(0);
        }
        clientManager.broadcast(m);
    }
    
    public void sendToOne(int clientId,Message msg){
        clientManager.sendToOne(clientId,msg);
    }
    
    public PlayerData getPlayerData(int accountid){
        ResultSet r = db.makeQuerry("Select * from characters where accountid = "+accountid);
        try{
            r.next();
            //Note: There should only be one character per account at this point.
            //Anymore, and itl only take the first character!
            //Future: Add in multiple character support
            
            int characterType = r.getInt("characterType");
            GamePoint loc = new GamePoint(r.getFloat("x"),r.getFloat("y"),r.getFloat("z"));
            String mapType = r.getString("mapid");
            int[] entity_data = new int[]{r.getInt("maxhp"),r.getInt("hp"),r.getInt("maxmp"),r.getInt("mp"),
                                          r.getInt("money"),r.getInt("level"),r.getInt("exp"),r.getInt("attack"),r.getInt("defense"),
                                          r.getInt("statPoints"),r.getInt("skillPoints")};
            return new PlayerData(accountid,characterType,loc,mapType,getItemData(accountid),entity_data,r.getString("name"),
                                  getSkillLevels(accountid),getQuestData(accountid),getEquipData(accountid));
        
        } catch (SQLException e){
            System.out.println("Error fetching player data!");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    
    public synchronized LoginReply RequestLogin(String username, String password){
        ResultSet r = db.makeQuerry("Select * from accounts where username = '"+username+"' and password = '"+password+"'");
        try {
            if (r.next()){
                if (!r.getBoolean("loggedin")){
                    db.makeUpdate("update accounts set loggedin = true where username = '"+username+"'");
                    return new LoginReply(r.getInt("idaccounts"),true,"");
                } else{
                    return new LoginReply(-1,false,"This user is already logged in!");
                }
            } else {
                return new LoginReply(-1,false,"Either your user or pass is wrong!");
            }
        } catch (SQLException e){
            System.out.println("ERROR LOGGING IN! "+e.getMessage());
            return new LoginReply(-1,false,"Something wrong occured with the server!");
        }
    }
    public synchronized void savePlayerData(PlayerData playerData){
        int accountId = playerData.getAccountId();
        GamePoint loc = playerData.getLocation();
        float x = loc.getX();
        float y = loc.getY();
        float z = loc.getZ();
        String mapId = playerData.getMapId();
        int[] entityData = playerData.getEntity_data();
        String template = "update characters set %s = %s where accountid = "+accountId;
        db.makeUpdate(String.format(template,"x",""+x));
        db.makeUpdate(String.format(template,"y",""+y));
        db.makeUpdate(String.format(template,"z",""+z));
        db.makeUpdate(String.format(template,"mapid","'"+mapId+"'"));
        db.makeUpdate(String.format(template,"maxhp",""+entityData[0]));
        db.makeUpdate(String.format(template,"hp",""+entityData[1]));
        db.makeUpdate(String.format(template,"maxmp",""+entityData[2]));
        db.makeUpdate(String.format(template,"mp",""+entityData[3]));
        db.makeUpdate(String.format(template,"money",""+entityData[4]));
        db.makeUpdate(String.format(template,"level",""+entityData[5]));
        db.makeUpdate(String.format(template,"exp",""+entityData[6]));
        db.makeUpdate(String.format(template,"attack",""+entityData[7]));
        db.makeUpdate(String.format(template,"defense",""+entityData[8]));
        db.makeUpdate(String.format(template,"statPoints",""+entityData[9]));
        db.makeUpdate(String.format(template,"skillPoints",""+entityData[10]));
        
        String invenTemplate = "update inventory set %s = %s where accountId = "+accountId+" and itemId = '%s'";
        
        ResultSet r = db.makeQuerry("select * from inventory where accountId = "+accountId);
        try {
            HashMap<String,SaveItemData> addedItems = new HashMap<>();
            for (SaveItemData data: playerData.getItems()){
                addedItems.put(data.getItemKey(),data);
            }
            while (r.next()){
                String itemType = r.getString("itemId");
                addedItems.remove(itemType);
                boolean exists = false;
                for (SaveItemData data: playerData.getItems()){
                    if (data.getItemKey().equals(itemType)){
                        exists = true;
                        db.makeUpdate(String.format(invenTemplate,"quantity",""+data.getQuantity(),data.getItemKey()));
                        break;
                    }
                }
                if (!exists){//our item doesn't exist anymore so remove the row completely
                    db.makeUpdate("DELETE from inventory where accountId = "+accountId+" and itemId = '"+itemType+"'");
                }
            }
            //Everything left from addedItems are new items
            for (SaveItemData data: addedItems.values().toArray(new SaveItemData[0])){
                db.makeUpdate(String.format("INSERT INTO inventory (accountId,itemId,quantity) VALUES(%d,'%s',%d)",accountId,data.getItemKey(),data.getQuantity()));
            }
        } catch (SQLException e) {
            System.out.println("Severe: Failed to save inventoryData!");
            e.printStackTrace();
        }
        
        //Save skill levels
        String skillTemplate = "update skillset set skillLevel = %s where accountId = "+accountId+" and skillId = '%s'";
        for (SaveSkillData data: playerData.getSkillData()){
            db.makeUpdate(String.format(skillTemplate,""+data.getLevel(),data.getSkillName()));
        }
        
        //Save quest information
        String questTemplate = "update questdata set number = %s where accountId = "+accountId+" and questId = '%s'";
        String statusTemplate = "update quests set status = %s where accountId = "+accountId+" and questId = '%s'";
        
        for (QuestData quest: playerData.getQuestData()){
            db.makeUpdate(String.format(statusTemplate,""+quest.getStatus(),quest.getQuestId()));
            for (QuestRequirement req: quest.getRequirements()){
                db.makeUpdate(String.format(questTemplate,""+req.getNumber(),req.getQuestId()));
            }
        }
        
        //Save equipment information
        db.makeUpdate("delete from equip where accountId = "+accountId);
        String equipTemplate = "insert into equip(accountId,itemId) values(%d,'%s')";
        for (String equipment: playerData.getEquipData()){
            db.makeUpdate(String.format(equipTemplate,accountId,equipment));
        }
        //Log them out
        db.makeUpdate("update accounts set loggedin = false where idaccounts = "+accountId);
    }
    
    private SaveItemData[] getItemData(int accountId){
        ResultSet r = db.makeQuerry("select * from inventory where accountId = "+accountId);
        LinkedList<SaveItemData> alldata = new LinkedList<>();
        try {
            while (r.next()){
                String itemType = r.getString("itemId");
                int itemQuantity = r.getInt("quantity");
                alldata.add(new SaveItemData(itemType,itemQuantity));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account inventory information!");
            e.printStackTrace();
            System.exit(0);
        }
        return alldata.toArray(new SaveItemData[0]);
    }
    
    private SaveSkillData[] getSkillLevels(int accountId){
        ResultSet r = db.makeQuerry("select * from skillSet where accountId = "+accountId);
        LinkedList<SaveSkillData> allSkillData = new LinkedList<>();
        try {
            while (r.next()){
                allSkillData.add(new SaveSkillData(r.getString("skillId"),r.getInt("skillLevel")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching skill levels!");
            e.printStackTrace();
            System.exit(0);
        }
        return allSkillData.toArray(new SaveSkillData[0]);
    }
    
    private QuestData[] getQuestData(int accountId){
        ResultSet allQuests = db.makeQuerry("select * from quests where accountId = "+accountId);
        LinkedList<QuestData> allQuestData = new LinkedList<>();
        try {
            while (allQuests.next()){
                QuestData newQuest = new QuestData(allQuests.getString("questId"),allQuests.getInt("status"));
                System.out.println(allQuests.getInt("status"));
                ResultSet questData = db.makeQuerry("select * from questdata where accountid = "+accountId+" and questId = '"+allQuests.getString("questId") +"'");
                while (questData.next()){
                    newQuest.addRequirement(new QuestRequirement(questData.getString("requiredMob"),questData.getString("questId"),questData.getInt("number"),questData.getInt("requiredNumber")));
                }
                allQuestData.add(newQuest);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching quest data!");
            e.printStackTrace();
            System.exit(0);
        }
        return allQuestData.toArray(new QuestData[0]);
    }
    private String[] getEquipData(int accountId){
        ResultSet allItems = db.makeQuerry("select * from equip where accountId = "+accountId);
        String[] equips = new String[4];
        try {
            for (int i = 0; i < 4; i++){//should always have a max of 4 items equiped at a time :3
                allItems.next();
                equips[i] = allItems.getString("itemId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerWorldHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equips;
    }
}