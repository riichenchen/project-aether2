package GameSource.Game;

import Database.DatabaseHandler;
import Database.LoginReply;
import Database.PlayerData;
import GameSource.Assets.AssetManager;
import GameSource.game.GameMap;
import Networking.Messages.Message;
import Networking.Server.ClientManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerWorldHandler {
    private DatabaseHandler db;
    private ClientManager clientManager;
    private GameMap[] allMaps;

    public ServerWorldHandler(DatabaseHandler db) {
        this.db = db;     
        this.allMaps = AssetManager.getAllMaps();
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
            
            return new PlayerData(accountid,characterType,loc,mapType);
        
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
        
        //Log them out
        db.makeUpdate("update accounts set loggedin = false where idaccounts = "+accountId);
    }
}