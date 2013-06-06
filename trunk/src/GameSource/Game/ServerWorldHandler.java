package GameSource.Game;

import Database.DatabaseHandler;
import Database.LoginReply;
import Database.PlayerData;
import Database.SaveItemData;
import GameSource.Assets.AssetManager;
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
            
            return new PlayerData(accountid,characterType,loc,mapType,getItemData(accountid));
        
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
        String template = "update characters set %s = %s where accountid = "+accountId;
        db.makeUpdate(String.format(template,"x",""+x));
        db.makeUpdate(String.format(template,"y",""+y));
        db.makeUpdate(String.format(template,"z",""+z));
        db.makeUpdate(String.format(template,"mapid","'"+mapId+"'"));
        //TODO LATER: ADD IN MOAR CHAR INFO
        
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
        //Log them out
        db.makeUpdate("update accounts set loggedin = false where idaccounts = "+accountId);
    }
    
    public SaveItemData[] getItemData(int accountId){
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
}