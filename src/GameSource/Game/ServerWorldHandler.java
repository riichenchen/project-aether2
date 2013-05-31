package GameSource.Game;

import Database.DatabaseHandler;
import Database.PlayerData;
import GameSource.Assets.AssetManager;
import GameSource.game.GameMap;
import Networking.Messages.Message;
import Networking.Messages.PlayerJoinMessage;
import Networking.Server.ClientManager;
import PhysicsSync.PhysicSyncMessage;
import PhysicsSync.PhysicsSyncManager;
import Spatial.Spatial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerWorldHandler {
    private DatabaseHandler db;
//    private GameMap myGameMap;
    private ClientManager clientManager;
//    private PhysicsSpace myPhysicsSpace;
    private PhysicsSyncManager psync;
    private GameMap[] allMaps;
//    private ConcurrentLinkedQueue<WorldMessage> allMessages;
    public ServerWorldHandler(DatabaseHandler db) {
        AssetManager.init();
        this.db = db;     
        this.psync = new PhysicsSyncManager(this);
//        this.allMessages = new ConcurrentLinkedQueue<>();
        this.allMaps = AssetManager.getAllMaps();
    }
    
    public void bindManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }
    
    public synchronized Spatial getSpatial(int id,String mapid){
        return AssetManager.getMap(mapid).getSpatial(id);
    }
    
    public void addPSyncMessage(PhysicSyncMessage msg){
        psync.addMessage(msg);
    }
    
    public void broadcastMessage(Message m){
        if (clientManager == null){
            System.out.println("SEVERE: NO CLIENTMANAGER!");
            System.exit(0);
        }
        clientManager.broadcast(m);
    }
    public synchronized void sendPhysicsMessage(PhysicSyncMessage m){
        if (clientManager == null){
            System.out.println("SEVERE: NO CLIENTMANAGER!");
            System.exit(0);
        }
        for (int cid: AssetManager.getMap(m.getMapId()).getClients()){
            clientManager.sendToOne(cid,m);
        }
    }
    
    public synchronized void sendToMap(String mapid,Message m){
        for (int cid: AssetManager.getMap(mapid).getClients()){
            clientManager.sendToOne(cid,m);
        }
    }
    
    public void sendToOne(int clientId,Message msg){
        clientManager.sendToOne(clientId,msg);
    }
    
    public synchronized void update() {
        if (clientManager == null){
            System.out.println("ERROR! NO MANAGER!");
            System.exit(0);
        }
        for (GameMap map: allMaps){
            map.update();
        }
        psync.elapseTime();
        psync.update();
        //resolveMessages();
    }
//    public void sendSpatData(){
//        for ()
//    }

    public static void addMonster() {

    }

    public static void dropItem() {

    }
    public PlayerData getPlayerData(int accountid){
        ResultSet r = db.makeQuerry("Select * from characters where accountid = "+accountid);
        try{
            r.next();
            //Note: There should only be one character per account at this point.
            //Anymore, and itl only take the first character!
            //Future: Add in multiple character support
            
            int characterType = r.getInt("characterType");
            //register our character into the world, the world returns a
            //spatial id for the client to register
            GamePoint loc = new GamePoint(r.getFloat("x"),r.getFloat("y"),r.getFloat("z"));
            String mapType = r.getString("mapid");
            
            
            return new PlayerData(characterType,loc,mapType);
        
        } catch (SQLException e){
            System.out.println("Error fetching player data!");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    
    public synchronized PlayerJoinMessage getPlayerJoinMessage(PlayerData pData,int clientid){
        String mapType = pData.getMapId();
        int characterType = pData.getCharacterType();
        GamePoint loc = pData.getLocation();
        GameMap theMap = AssetManager.getMap(mapType);
        int registeredId = theMap.addPlayer(characterType,loc);
        theMap.addClientId(clientid);
        return new PlayerJoinMessage(characterType,loc,mapType,registeredId,clientid);
    }
    
    public ConcurrentHashMap<Integer,Spatial> getNonPermanents(String mapId){
        return AssetManager.getMap(mapId).getNonPermanents();
    }
    
    public int RequestLogin(String username, String password){
        //System.out.println("okay to querry");
        ResultSet r = db.makeQuerry("Select * from accounts where username = '"+username+"' and password = '"+password+"'");
        try {
            if (r.next()){
                return r.getInt("idaccounts");
            } else {
                return -1;
            }
        } catch (SQLException e){
            System.out.println("ERROR LOGGING IN! "+e.getMessage());
            return -1;
        }
    }
}